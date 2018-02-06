package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.config.SettingConfig;
import com.zhazhapan.efo.dao.FileDAO;
import com.zhazhapan.efo.entity.Category;
import com.zhazhapan.efo.entity.File;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.model.AuthRecord;
import com.zhazhapan.efo.model.FileRecord;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IAuthService;
import com.zhazhapan.efo.service.ICategoryService;
import com.zhazhapan.efo.service.IDownloadService;
import com.zhazhapan.efo.service.IFileService;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    private final static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileDAO fileDAO;

    private final ICategoryService categoryService;

    private final IAuthService authService;

    private final IDownloadService downloadService;

    @Autowired
    public FileServiceImpl(FileDAO fileDAO, ICategoryService categoryService, IAuthService authService,
                           IDownloadService downloadService) {
        this.fileDAO = fileDAO;
        this.categoryService = categoryService;
        this.authService = authService;
        this.downloadService = downloadService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor =
            Exception.class)
    public boolean updateFileInfo(long id, User user, String name, String category, String tag, String description) {
        File file = fileDAO.getById(id);
        if (Checker.isNotNull(file) && file.getIsUpdatable() == 1) {
            AuthRecord authRecord = authService.getByFileIdAndUserId(id, user.getId());
            String suffix = FileExecutor.getFileSuffix(name);
            boolean canUpdate = (Checker.isNull(authRecord) ? user.getIsUpdatable() == 1 : authRecord.getIsUpdatable
                    () == 1) && Checker.isNotEmpty(name) && Pattern.compile(EfoApplication.settings.getStringUseEval
                    (ConfigConsts.FILE_SUFFIX_MATCH_OF_SETTING)).matcher(suffix).matches();
            if (canUpdate) {
                String localUrl = file.getLocalUrl();
                java.io.File newFile = new java.io.File(localUrl);
                String visitUrl = file.getVisitUrl();
                String newLocalUrl = localUrl.substring(0, localUrl.lastIndexOf(ValueConsts.SEPARATOR) + 1) + name;
                String newVisitUrl = visitUrl.substring(0, visitUrl.lastIndexOf("/") + 1) + name;
                file.setName(name);
                file.setSuffix(suffix);
                file.setLocalUrl(newLocalUrl);
                file.setVisitUrl(newVisitUrl);
                file.setCategoryId(categoryService.getIdByName(category));
                file.setTag(tag);
                file.setDescription(description);
                boolean isValid = (localUrl.endsWith("/" + name) || (!Checker.isExists(newLocalUrl) &&
                        !localUrlExists(newLocalUrl) && !visitUrlExists(newVisitUrl)));
                if (isValid && fileDAO.updateFileInfo(file)) {
                    return newFile.renameTo(new java.io.File(newLocalUrl));
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeFile(User user, long fileId) {
        File file = fileDAO.getById(fileId);
        boolean isDeleted = false;
        if (Checker.isNotNull(file) && file.getIsDeletable() == 1) {
            AuthRecord authRecord = authService.getByFileIdAndUserId(fileId, user.getId());
            String localUrl = fileDAO.getLocalUrlById(fileId);
            isDeleted = (Checker.isNull(authRecord) ? user.getIsDeletable() == 1 : authRecord.getIsDeletable() == 1)
                    && removeById(fileId);
            if (isDeleted) {
                FileExecutor.deleteFile(localUrl);
            }
        }
        return isDeleted;
    }

    @Override
    public List<FileRecord> getUserDownloaded(int userId, int offset) {
        return fileDAO.getUserDownloaded(userId, offset);
    }

    @Override
    public List<FileRecord> getUserUploaded(int userId, int offset) {
        return fileDAO.getUserUploaded(userId, offset);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor =
            Exception.class)
    public boolean removeById(long id) {
        downloadService.removeByFileId(id);
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
            authRecord = authService.getByFileIdAndUserId(file.getId(), Checker.isNull(user) ? 0 : user.getId());
        }
        boolean canDownload = Checker.isNotNull(file) && file.getIsDownloadable() == 1 && (downloadable || (Checker
                .isNull(authRecord) ? (Checker.isNotNull(user) && user.getIsDownloadable() == 1) : authRecord
                .getIsDownloadable() == 1));
        if (canDownload) {
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
    public List<FileRecord> getAll(int offset) {
        return fileDAO.getAll(offset);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor =
            Exception.class)
    public boolean upload(int categoryId, String tag, String description, MultipartFile multipartFile, User user) {
        if (user.getIsUploadable() == 1) {
            String name = multipartFile.getOriginalFilename();
            String suffix = FileExecutor.getFileSuffix(name);
            String localUrl = SettingConfig.getUploadStoragePath() + ValueConsts.SEPARATOR + name;
            Category category = categoryService.getById(categoryId);
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
