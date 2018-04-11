package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.config.SettingConfig;
import com.zhazhapan.efo.dao.DownloadedDAO;
import com.zhazhapan.efo.dao.FileDAO;
import com.zhazhapan.efo.entity.Category;
import com.zhazhapan.efo.entity.File;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.model.AuthRecord;
import com.zhazhapan.efo.model.BaseAuthRecord;
import com.zhazhapan.efo.model.FileBasicRecord;
import com.zhazhapan.efo.model.FileRecord;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.efo.service.IAuthService;
import com.zhazhapan.efo.service.ICategoryService;
import com.zhazhapan.efo.service.IFileService;
import com.zhazhapan.efo.util.BeanUtils;
import com.zhazhapan.efo.util.ServiceUtils;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author pantao
 * @since 2018/1/29
 */
@Service
public class FileServiceImpl implements IFileService {

    private final static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private static final String FILE_SUFFIX = "{fileSuffix}";

    private static final String RANDOM_ID = "{randomId}";

    private static final String YEAR = "{year}";

    private static final String MONTH = "{month}";

    private static final String DAY = "{day}";

    private static final String AUTHOR = "{author}";

    private static final String FILE_NAME = "{fileName}";

    private static final String CATEGORY_NAME = "{categoryName}";

    private final FileDAO fileDAO;

    private final ICategoryService categoryService;

    private final IAuthService authService;

    private final DownloadedDAO downloadDAO;

    @Autowired
    public FileServiceImpl(FileDAO fileDAO, ICategoryService categoryService, IAuthService authService, DownloadedDAO
            downloadDAO) {
        this.fileDAO = fileDAO;
        this.categoryService = categoryService;
        this.authService = authService;
        this.downloadDAO = downloadDAO;
    }

    @Override
    public boolean updateAuth(long id, String auth) {
        int[] au = BeanUtils.getAuth(auth);
        return fileDAO.updateAuthById(id, au[0], au[1], au[2], au[3], au[4]);
    }

    @Override
    public BaseAuthRecord getAuth(long id) {
        return fileDAO.getAuth(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor =
            Exception.class)
    public boolean deleteFiles(String ids) {
        if (Checker.isNotEmpty(ids)) {
            String[] id = ids.split(ValueConsts.COMMA_SIGN);
            for (String s : id) {
                long fileId = Formatter.stringToLong(s);
                String localUrl = fileDAO.getLocalUrlById(fileId);
                FileExecutor.deleteFile(localUrl);
                removeById(fileId);
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor =
            Exception.class)
    public boolean[] updateUrl(int id, String oldLocalUrl, String localUrl, String visitUrl) {
        boolean[] b = new boolean[]{false, false};
        if (Checker.isNotEmpty(localUrl) && Checker.isNotExists(localUrl) && !localUrlExists(localUrl)) {
            FileExecutor.renameTo(oldLocalUrl, localUrl);
            fileDAO.updateLocalUrlById(id, localUrl);
            b[0] = true;
        }
        if (Checker.isNotEmpty(visitUrl) && !visitUrlExists(visitUrl)) {
            fileDAO.updateVisitUrlById(id, visitUrl);
            b[1] = true;
        }
        return b;
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
                String newVisitUrl = visitUrl.substring(0, visitUrl.lastIndexOf(ValueConsts.SPLASH_STRING) + 1) + name;
                file.setName(name);
                file.setSuffix(suffix);
                file.setLocalUrl(newLocalUrl);
                file.setVisitUrl(newVisitUrl);
                file.setCategoryId(categoryService.getIdByName(category));
                file.setTag(tag);
                file.setDescription(description);
                boolean isValid = (localUrl.endsWith(ValueConsts.SEPARATOR + name) || (!Checker.isExists(newLocalUrl)
                        && !localUrlExists(newLocalUrl) && !visitUrlExists(newVisitUrl)));
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
    public List<FileRecord> getUserDownloaded(int userId, int offset, String search) {
        return fileDAO.getUserDownloaded(userId, offset, search);
    }

    @Override
    public List<FileRecord> getUserUploaded(int userId, int offset, String search) {
        return fileDAO.getUserUploaded(userId, offset, search);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor =
            Exception.class)
    public boolean removeById(long id) {
        downloadDAO.removeByFileId(id);
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
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
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
                downloadDAO.insertDownload(user.getId(), file.getId());
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
    public List<FileRecord> getAll(int offset, int categoryId, String orderBy, String search) {
        return fileDAO.getAll(offset, categoryId, orderBy, search);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor =
            Exception.class)
    public boolean upload(int categoryId, String tag, String description, String prefix, MultipartFile multipartFile,
                          User user) {
        if (user.getIsUploadable() == 1) {
            String name = multipartFile.getOriginalFilename();
            String suffix = FileExecutor.getFileSuffix(name);
            String localUrl = SettingConfig.getUploadStoragePath() + ValueConsts.SEPARATOR + name;
            Category category = categoryService.getById(categoryId);
            long maxSize = Formatter.sizeToLong(EfoApplication.settings.getStringUseEval(ConfigConsts
                    .FILE_MAX_SIZE_OF_SETTING));
            long size = multipartFile.getSize();
            boolean fileExists = localUrlExists(localUrl);
            //检测标签是否合法
            if (EfoApplication.settings.getBooleanUseEval(ConfigConsts.TAG_REQUIRE_OF_SETTING)) {
                String[] tags = Checker.checkNull(tag).split(ValueConsts.SPACE);
                if (tags.length <= EfoApplication.settings.getIntegerUseEval(ConfigConsts.TAG_SIZE_OF_SETTING)) {
                    int maxLength = EfoApplication.settings.getIntegerUseEval(ConfigConsts.TAG_LENGTH_OF_SETTING);
                    for (String t : tags) {
                        if (t.length() > maxLength) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            //是否可以上传
            boolean canUpload = !multipartFile.isEmpty() && size <= maxSize && Pattern.compile(EfoApplication
                    .settings.getStringUseEval(ConfigConsts.FILE_SUFFIX_MATCH_OF_SETTING)).matcher(suffix).matches()
                    && (Checker.isNotExists(localUrl) || !fileExists || EfoApplication.settings.getBooleanUseEval
                    (ConfigConsts.FILE_COVER_OF_SETTING));
            logger.info("is empty [" + multipartFile.isEmpty() + "], file size [" + size + "], max file size [" +
                    maxSize + "]");
            if (canUpload) {
                String visitUrl = getRegularVisitUrl(Checker.isNotEmpty(prefix) && user.getPermission() > 1 ? prefix
                        : EfoApplication.settings.getStringUseEval(ConfigConsts.CUSTOM_LINK_RULE_OF_SETTING), user,
                        name, suffix, category);
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

    private String getRegularVisitUrl(String customUrl, User user, String fileName, String suffix, Category category) {
        Date date = new Date();
        suffix = suffix.startsWith(".") ? "" : "." + suffix;
        try {
            customUrl = URLDecoder.decode(customUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        if (!customUrl.contains(FILE_NAME) && !customUrl.contains(RANDOM_ID)) {
            customUrl += (customUrl.endsWith("/") ? "" : "/") + fileName;
        }
        customUrl = customUrl.replace(YEAR, DateUtils.getYear(date)).replace(MONTH, DateUtils.getMonth(date)).replace
                (DAY, DateUtils.getDay(date)).replace(AUTHOR, user.getUsername()).replace(FILE_NAME, fileName)
                .replace(CATEGORY_NAME, Checker.checkNull(Checker.isNull(category) ? "uncategorized" : category
                        .getName())).replace(RANDOM_ID, String.valueOf(RandomUtils.getRandomInteger(ValueConsts
                        .NINE_INT))).replace(FILE_SUFFIX, suffix);
        return "/file" + (customUrl.startsWith("/") ? "" : "/") + customUrl;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor =
            Exception.class)
    public boolean shareFiles(String prefix, String files, User user) {
        if (Checker.isNotEmpty(files)) {
            String[] paths = files.split(ValueConsts.COMMA_SIGN);
            for (String path : paths) {
                java.io.File f = new java.io.File(path);
                String name = f.getName();
                String suffix = FileExecutor.getFileSuffix(name);
                String visitUrl = getRegularVisitUrl(prefix, user, name, suffix, null);
                if (f.exists() && f.isFile() && !localUrlExists(path) && !visitUrlExists(visitUrl)) {
                    File file = new File(name, suffix, path, visitUrl, ValueConsts.EMPTY_STRING, ValueConsts
                            .EMPTY_STRING, user.getId(), categoryService.getIdByName(DefaultValues.UNCATEGORIZED));
                    file.setAuth(ValueConsts.ONE_INT, ValueConsts.ZERO_INT, ValueConsts.ZERO_INT, ValueConsts
                            .ZERO_INT, ValueConsts.ONE_INT);
                    fileDAO.insertFile(file);
                }
            }
        }
        return true;
    }

    @Override
    public boolean localUrlExists(String localUrl) {
        return fileDAO.checkLocalUrl(localUrl) > 0;
    }

    @Override
    public boolean visitUrlExists(String visitUrl) {
        return fileDAO.checkVisitUrl(visitUrl) > 0;
    }

    @Override
    public long getFileId(String localUrl) {
        try {
            return fileDAO.getIdByLocalUrl(localUrl);
        } catch (Exception e) {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FileBasicRecord> getBasicAll(String user, String file, String category, int offset) {
        return (List<FileBasicRecord>) ServiceUtils.invokeFileFilter(fileDAO, "getBasicBy", user, file, category,
                offset);
    }
}
