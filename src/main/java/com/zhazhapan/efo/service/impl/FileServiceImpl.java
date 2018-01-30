package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.config.SettingConfig;
import com.zhazhapan.efo.dao.CategoryDAO;
import com.zhazhapan.efo.dao.FileDAO;
import com.zhazhapan.efo.entity.Category;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IFileService;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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

    @Override
    public boolean upload(HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        int categoryId = com.zhazhapan.efo.util.Utils.stringToInt(request.getParameter("categoryId"));
        String tag = WebUtils.scriptFilter(request.getParameter("tag"));
        String description = WebUtils.scriptFilter(request.getParameter("description"));
        User user = (User) request.getSession().getAttribute("user");
        Category category = categoryDAO.getCategoryById(categoryId);
        Date date = new Date();
        boolean result = true;
        long maxSize = Formatter.sizeToLong(EfoApplication.settings.getStringUseEval(ConfigConsts.FILE_MAX_SIZE_OF_SETTING));
        logger.info("is multipart: " + isMultipart);
        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fileItems = null;
            try {
                fileItems = upload.parseRequest(request);
            } catch (FileUploadException e) {
                logger.error("parse request error: " + e.getMessage());
            }
            if (Checker.isNotNull(fileItems)) {
                logger.info("file item length: " + fileItems.size());
                for (FileItem item : fileItems) {
                    logger.info("is form field: " + item.isFormField());
                    if (!item.isFormField()) {
                        String name = item.getName();
                        long size = item.getSize();
                        if (Checker.isNotNull(name) && size <= maxSize && Pattern.compile(EfoApplication.settings.getStringUseEval(ConfigConsts.USERNAME_PATTERN_OF_SETTINGS)).matcher(name).matches()) {
                            File tempFile = new File(item.getName());
                            if (tempFile.exists()) {
                                String localUrl = SettingConfig.getUploadStoragePath() + ValueConsts.SEPARATOR + name;
                                File file = new File(localUrl);
                                try {
                                    FileExecutor.createNewFile(file);
                                    item.write(file);
                                    String suffix = FileExecutor.getFileSuffix(name);
                                    String visitUrl = EfoApplication.settings.getStringUseEval(ConfigConsts.CUSTOM_LINK_RULE_OF_SETTING).replace("{year}", Utils.getYear(date)).replace("{month}", Utils.getMonth(date)).replace("{day}", Utils.getDay(date)).replace("{author}", Checker.isNull(user) ? "" : user.getUsername()).replace("{fileName}", name).replace("{categoryName}", Checker.checkNull(Checker.isNull(category) ? "" : category.getName()));
                                    fileDAO.insertFile(new com.zhazhapan.efo.entity.File(name, suffix, localUrl, visitUrl, description, tag, Checker.isNull(user) ? 0 : user.getId(), categoryId));
                                } catch (Exception e) {
                                    logger.error("save file error: " + e.getMessage());
                                    result = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void download() {

    }

    @Override
    public boolean upload(int categoryId, String tag, String description, MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            byte[] bytes;
            try {
                bytes = multipartFile.getBytes();
                Path path = Paths.get("");
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
