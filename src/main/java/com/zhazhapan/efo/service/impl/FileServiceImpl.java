package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.config.SettingConfig;
import com.zhazhapan.efo.dao.CategoryDAO;
import com.zhazhapan.efo.dao.FileDAO;
import com.zhazhapan.efo.entity.Category;
import com.zhazhapan.efo.entity.File;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IFileService;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
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
    public void download() {

    }

    @Override
    public boolean upload(int categoryId, String tag, String description, MultipartFile multipartFile, User user) {
        String name = multipartFile.getOriginalFilename();
        String suffix = FileExecutor.getFileSuffix(name);
        String localUrl = SettingConfig.getUploadStoragePath() + ValueConsts.SEPARATOR + name;
        Category category = categoryDAO.getCategoryById(categoryId);
        long maxSize = Formatter.sizeToLong(EfoApplication.settings.getStringUseEval(ConfigConsts.FILE_MAX_SIZE_OF_SETTING));
        long size = multipartFile.getSize();
        boolean canUpload = !multipartFile.isEmpty() && size <= maxSize && Pattern.compile(EfoApplication.settings.getStringUseEval(ConfigConsts.FILE_SUFFIX_MATCH_OF_SETTING)).matcher(suffix).matches() && (Checker.isNotExists(localUrl) || EfoApplication.settings.getBooleanUseEval(ConfigConsts.FILE_COVER_OF_SETTING));
        logger.info("is empty [" + multipartFile.isEmpty() + "], file size [" + size + "], max file size [" + maxSize + "]");
        if (canUpload) {
            Date date = new Date();
            String visitUrl = EfoApplication.settings.getStringUseEval(ConfigConsts.CUSTOM_LINK_RULE_OF_SETTING).replace("{year}", Utils.getYear(date)).replace("{month}", Utils.getMonth(date)).replace("{day}", Utils.getDay(date)).replace("{author}", Checker.isNull(user) ? "" : user.getUsername()).replace("{fileName}", name).replace("{categoryName}", Checker.checkNull(Checker.isNull(category) ? "" : category.getName()));
            if (localUrlExists(localUrl)) {
                fileDAO.deleteByLocalUrl(localUrl);
            }
            if (visitUrlExists(visitUrl)) {
                fileDAO.deleteByVisitUrl(visitUrl);
            }
            try {
                byte[] bytes = multipartFile.getBytes();
                FileExecutor.createNewFile(localUrl);
                Path path = Paths.get(localUrl);
                Files.write(path, bytes);
                logger.info("local url of upload file: " + localUrl);
                File file = new File(name, suffix, localUrl, visitUrl, WebUtils.scriptFilter(description), WebUtils.scriptFilter(tag), Checker.isNull(user) ? 0 : user.getId(), categoryId);
                int[] auth = SettingConfig.getAuth(ConfigConsts.FILE_DEFAULT_AUTH_OF_SETTING);
                file.setAuth(auth[0], auth[1], auth[2], auth[3], auth[4]);
                boolean isSuccess = fileDAO.insertFile(file);
                if (!isSuccess) {
                    FileExecutor.deleteFile(localUrl);
                }
                return isSuccess;
            } catch (Exception e) {
                FileExecutor.deleteFile(localUrl);
                logger.error("save file error: " + e.getMessage());
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
