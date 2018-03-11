package com.zhazhapan.efo.service.impl;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.efo.service.IFileManagerService;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.Formatter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author pantao
 * @since 2018/1/29
 */
@Service
public class FileManagerServiceImpl implements IFileManagerService {

    private static Logger logger = Logger.getLogger(FileManagerServiceImpl.class);

    @Override
    public void multiDownload(HttpServletResponse response, String[] items, String destFile) throws IOException {
        File zip = ZipUtil.zip(new File(ValueConsts.USER_DESKTOP + File.separator + destFile), ValueConsts.FALSE,
                FileExecutor.getFiles(items));
        if (zip.exists()) {
            response.getOutputStream().write(FileExecutor.readFileToByteArray(zip));
            FileExecutor.deleteFile(zip);
        }
    }

    @Override
    public JSONObject upload(String destination, MultipartFile... files) {
        if (Checker.isNotNull(files)) {
            if (Checker.isWindows() && destination.length() < ValueConsts.TWO_INT) {
                destination = "C:";
            }
            for (MultipartFile file : files) {
                if (Checker.isNull(file) || file.isEmpty()) {
                    break;
                } else {
                    try {
                        FileExecutor.writeByteArrayToFile(new File(destination + File.separator + file
                                .getOriginalFilename()), file.getBytes());
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        return getBasicResponse(ValueConsts.FALSE);
                    }
                }
            }
        }
        return getBasicResponse(ValueConsts.TRUE);
    }

    @Override
    public JSONObject extract(JSONObject object) {
        String destination = object.getString("destination") + File.separator + object.getString("folderName");
        String zipFile = object.getString("item");
        return getBasicResponse(ZipUtil.unzip(zipFile, destination).exists());
    }

    @Override
    public JSONObject compress(JSONObject object) {
        JSONArray array = object.getJSONArray("items");
        File[] files = new File[array.size()];
        int i = 0;
        for (Object file : array) {
            files[i++] = new File(file.toString());
        }
        String dest = object.getString("destination");
        String name = object.getString("compressedFilename");
        File zip = ZipUtil.zip(new File(dest + File.separator + name), ValueConsts.FALSE, files);
        return getBasicResponse(zip.exists());
    }

    @Override
    public JSONObject setPermission(JSONObject object) {
        if (Checker.isLinux()) {
            JSONArray array = object.getJSONArray("items");
            int code = object.getInteger("permsCode");
            for (Object file : array) {
                try {
                    Runtime.getRuntime().exec("chmod -R " + code + " " + file.toString());
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    return getBasicResponse(ValueConsts.FALSE);
                }
            }
        }
        return getBasicResponse(ValueConsts.TRUE);
    }

    @Override
    public JSONObject createFolder(JSONObject object) {
        String folder = object.getString("newPath");
        return getBasicResponse(FileExecutor.createFolder(folder));
    }

    @Override
    public String getContent(JSONObject object) {
        String fileName = object.getString("item");
        try {
            return FileExecutor.readFile(fileName);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    @Override
    public JSONObject edit(JSONObject object) {
        String file = object.getString("item");
        String content = object.getString("content");
        try {
            FileExecutor.saveFile(file, content);
            return getBasicResponse(ValueConsts.TRUE);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return getBasicResponse(ValueConsts.FALSE);
        }
    }

    @Override
    public JSONObject remove(JSONObject object) {
        JSONArray array = object.getJSONArray("items");
        array.forEach(file -> FileExecutor.deleteFile(file.toString()));
        return getBasicResponse(ValueConsts.TRUE);
    }

    @Override
    public JSONObject copy(JSONObject object) {
        JSONArray array = object.getJSONArray("items");
        String dest = object.getString("newPath");
        File[] files = new File[array.size()];
        int i = 0;
        for (Object file : array) {
            files[i++] = new File(file.toString());
        }
        try {
            FileExecutor.copyFiles(files, dest);
            return getBasicResponse(ValueConsts.TRUE);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return getBasicResponse(ValueConsts.FALSE);
        }
    }

    @Override
    public JSONObject move(JSONObject object) {
        JSONArray array = object.getJSONArray("items");
        String dest = object.getString("newPath");
        for (Object file : array) {
            try {
                FileExecutor.moveToDirectory(new File(file.toString()), new File(dest), ValueConsts.TRUE);
            } catch (IOException e) {
                logger.error(e.getMessage());
                return getBasicResponse(ValueConsts.FALSE);
            }
        }
        return getBasicResponse(ValueConsts.TRUE);
    }

    @Override
    public JSONObject rename(JSONObject object) {
        String fileName = object.getString("item");
        String newFileName = object.getString("newItemPath");
        FileExecutor.renameTo(fileName, newFileName);
        return getBasicResponse(ValueConsts.TRUE);
    }

    @Override
    public JSONArray list(JSONObject object) {
        String path = object.getString("path");
        JSONArray array = new JSONArray();
        File[] files = null;
        if (Checker.isWindows()) {
            if (Checker.isNotEmpty(path) && path.startsWith(ValueConsts.SPLASH_STRING)) {
                path = path.substring(1);
            }
            if (Checker.isEmpty(path)) {
                FileSystemView fsv = FileSystemView.getFileSystemView();
                File[] fs = File.listRoots();
                for (File file : fs) {
                    if (file.getTotalSpace() > 0) {
                        String displayName = fsv.getSystemDisplayName(file);
                        int len = displayName.length();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", displayName.substring(len - 3, len - 1));
                        jsonObject.put("rights", "----------");
                        jsonObject.put("size", file.getTotalSpace() - file.getFreeSpace());
                        jsonObject.put("date", Formatter.datetimeToString(new Date(file.lastModified())));
                        jsonObject.put("type", file.isDirectory() ? "dir" : "file");
                        array.add(jsonObject);
                    }
                }
            } else if (path.startsWith(DefaultValues.COLON, 1)) {
                files = FileExecutor.listFile(path.endsWith(DefaultValues.COLON) ? path + File.separator : path);
            } else {
                logger.error("path error");
            }
        } else {
            files = FileExecutor.listFile(Checker.isEmpty(path) ? "/" : (path.startsWith("/") ? path : "/" + path));
        }
        if (Checker.isNotNull(files)) {
            for (File file : files) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", file.getName());
                jsonObject.put("rights", "----------");
                jsonObject.put("size", file.length());
                jsonObject.put("date", Formatter.datetimeToString(new Date(file.lastModified())));
                jsonObject.put("type", file.isDirectory() ? "dir" : "file");
                array.add(jsonObject);
            }
        }
        return array;
    }

    private JSONObject getBasicResponse(boolean isSuccess) {
        JSONObject jsonObject = new JSONObject();
        if (isSuccess) {
            jsonObject.put("success", true);
            jsonObject.put("error", null);
        } else {
            jsonObject.put("success", null);
            jsonObject.put("error", "服务器异常");
        }
        return jsonObject;
    }
}
