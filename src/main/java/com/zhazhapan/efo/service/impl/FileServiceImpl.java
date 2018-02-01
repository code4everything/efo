package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.config.SettingConfig;
import com.zhazhapan.efo.dao.CategoryDAO;
import com.zhazhapan.efo.dao.FileDAO;
import com.zhazhapan.efo.entity.Category;
import com.zhazhapan.efo.entity.File;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.model.AuthRecord;
import com.zhazhapan.efo.model.FileRecord;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IFileService;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author pantao
 * @date 2018/1/29
 */
@Service
public class FileServiceImpl implements IFileService {

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    FileDAO fileDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    DownloadServiceImpl downloadService;

    @Override
    public boolean removeById(long id) {
        authService.removeByFileId(id);
        return fileDAO.removeById(id);
    }

    @Override
    public boolean removeByVisitUrl(String visitUrl) {
        long fileId = fileDAO.getIdByVisitUrl(visitUrl);
        return removeById(fileId);
    }

    @Override
    public boolean removeByLocalUrl(String localUrl) {
        long fileId = fileDAO.getIdByLocalUrl(localUrl);
        return removeById(fileId);
    }

    @Override
    public String getResource(String visitUrl, HttpServletRequest request) {
        logger.info("visit url: " + visitUrl);
        boolean downloadable = EfoApplication.settings.getBooleanUseEval(ConfigConsts
                .ANONYMOUS_DOWNLOADABLE_OF_SETTING);
        User user = (User) request.getSession().getAttribute("user");
        File file = fileDAO.getFileByVisitUrl(visitUrl);
        AuthRecord authRecord = null;
        if (Checker.isNotNull(file)) {
            authRecord = authService.getByFileId(file.getId());
        }
        boolean canDownload = downloadable || (((Checker.isNotNull(user) && user.getIsDownloadable() == 1) ||
                (Checker.isNotNull(authRecord) && authRecord.getIsDownloadable() == 1)) && Checker.isNotNull(file) &&
                file.getIsDownloadable() == 1);
        if (canDownload && Checker.isNotNull(file)) {
            fileDAO.updateDownloadTimesById(file.getId());
            if (Checker.isNotNull(user)) {
                downloadService.insertDownload(user.getId(), file.getId());
            }
            return file.getLocalUrl();
        }
        return "";
    }

    @Override
    public String getLocalUrlByVisitUrl(String visitUrl) {
        return fileDAO.getLocalUrlByVisitUrl(Checker.checkNull(visitUrl));
    }

    @Override
    public void download() {

    }

    @Override
    public List<FileRecord> getAll() {
        return fileDAO.getAll();
    }

    @Override
    public boolean upload(int categoryId, String tag, String description, MultipartFile multipartFile, User user) {
        if (user.getIsUploadable() == 1) {
            String name = multipartFile.getOriginalFilename();
            String suffix = FileExecutor.getFileSuffix(name);
            String localUrl = SettingConfig.getUploadStoragePath() + ValueConsts.SEPARATOR + name;
            Category category = categoryDAO.getCategoryById(categoryId);
            long maxSize = Formatter.sizeToLong(EfoApplication.settings.getStringUseEval(ConfigConsts
                    .FILE_MAX_SIZE_OF_SETTING));
            long size = multipartFile.getSize();
            boolean fileExists = localUrlExists(localUrl);
            //是否可以上传
            boolean canUpload = !multipartFile.isEmpty() && size <= maxSize && Pattern.compile(EfoApplication
                    .settings.getStringUseEval(ConfigConsts.FILE_SUFFIX_MATCH_OF_SETTING)).matcher(suffix).matches()
                    && (Checker.isNotExists(localUrl) || !fileExists || EfoApplication.settings.getBooleanUseEval
                    (ConfigConsts.FILE_COVER_OF_SETTING));
            logger.info("is empty [" + multipartFile.isEmpty() + "], file size [" + size + "], max file size [" +
                    maxSize + "]");
            if (canUpload) {
                Date date = new Date();
                String visitUrl = EfoApplication.settings.getStringUseEval(ConfigConsts.CUSTOM_LINK_RULE_OF_SETTING)
                        .replace("{year}", Utils.getYear(date)).replace("{month}", Utils.getMonth(date)).replace
                                ("{day}", Utils.getDay(date)).replace("{author}", user.getUsername()).replace
                                ("{fileName}", name).replace("{categoryName}", Checker.checkNull(Checker.isNull
                                (category) ? "uncategorized" : category.getName()));
                visitUrl = "/file" + (visitUrl.startsWith("/") ? "" : "/") + visitUrl;
                if (fileExists) {
                    removeByLocalUrl(localUrl);
                }
                if (visitUrlExists(visitUrl)) {
                    removeByVisitUrl(visitUrl);
                }
                try {
                    byte[] bytes = multipartFile.getBytes();
                    FileExecutor.createNewFile(localUrl);
                    Path path = Paths.get(localUrl);
                    Files.write(path, bytes);
                    logger.info("local url of upload file: " + localUrl);
                    File file = new File(name, suffix, localUrl, visitUrl, WebUtils.scriptFilter(description),
                            WebUtils.scriptFilter(tag), user.getId(), categoryId);
                    int[] auth = SettingConfig.getAuth(ConfigConsts.FILE_DEFAULT_AUTH_OF_SETTING);
                    file.setAuth(auth[0], auth[1], auth[2], auth[3], auth[4]);
                    boolean isSuccess = fileDAO.insertFile(file);
                    if (isSuccess) {
                        long fileId = fileDAO.getIdByLocalUrl(localUrl);
                        if (fileId > 0) {
                            authService.insertDefaultAuth(user.getId(), fileId);
                        }
                    } else {
                        FileExecutor.deleteFile(localUrl);
                    }
                    return isSuccess;
                } catch (Exception e) {
                    FileExecutor.deleteFile(localUrl);
                    logger.error("save file error: " + e.getMessage());
                }
            }
        }
        return false;
    }

    @Override
    public boolean localUrlExists(String localUrl) {
        return fileDAO.checkLocalUrl(localUrl) > 0;
    }

    @Override
    public boolean visitUrlExists(String visitUrl) {
        return fileDAO.checkVisitUrl(visitUrl) > 0;
    }
}
