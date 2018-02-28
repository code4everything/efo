package com.zhazhapan.efo.model;

import com.zhazhapan.efo.util.BeanUtils;

import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/1/19
 */
public class FileRecord {

    private long id;

    private int userId;

    private String username;

    private String avatar;

    private String fileName;

    private long size;

    private String categoryName;

    private String description;

    private String tag;

    private int checkTimes;

    private int downloadTimes;

    private String visitUrl;

    private int isUploadable;

    private int isDeletable;

    private int isUpdatable;

    private int isDownloadable;

    private int isVisible;

    private Timestamp createTime;

    private Timestamp downloadTime;

    public FileRecord(long id, int userId, String username, String avatar, String fileName, long size, Timestamp
            createTime, String categoryName, String description, String tag, int checkTimes, int downloadTimes,
                      String visitUrl, int isUploadable, int isDeletable, int isUpdatable, int isDownloadable, int
                              isVisible, Timestamp downloadTime) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.fileName = fileName;
        this.size = size;
        this.createTime = createTime;
        this.categoryName = categoryName;
        this.description = description;
        this.tag = tag;
        this.checkTimes = checkTimes;
        this.downloadTimes = downloadTimes;
        this.visitUrl = visitUrl;
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isDownloadable = isDownloadable;
        this.isVisible = isVisible;
        this.downloadTime = downloadTime;
    }

    public FileRecord(long id, int userId, String username, String avatar, String fileName, long size, Timestamp
            createTime, String categoryName, String description, String tag, int checkTimes, int downloadTimes,
                      String visitUrl, int isUploadable, int isDeletable, int isUpdatable, int isDownloadable, int
                              isVisible) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.fileName = fileName;
        this.size = size;
        this.createTime = createTime;
        this.categoryName = categoryName;
        this.description = description;
        this.tag = tag;
        this.checkTimes = checkTimes;
        this.downloadTimes = downloadTimes;
        this.visitUrl = visitUrl;
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isDownloadable = isDownloadable;
        this.isVisible = isVisible;
    }

    @Override
    public String toString() {
        return BeanUtils.toPrettyJson(this);
    }

    public Timestamp getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Timestamp downloadTime) {
        this.downloadTime = downloadTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getCreateTime() {

        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(int checkTimes) {
        this.checkTimes = checkTimes;
    }

    public int getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(int downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public int getIsUploadable() {
        return isUploadable;
    }

    public void setIsUploadable(int isUploadable) {
        this.isUploadable = isUploadable;
    }

    public int getIsDeletable() {
        return isDeletable;
    }

    public void setIsDeletable(int isDeletable) {
        this.isDeletable = isDeletable;
    }

    public int getIsUpdatable() {
        return isUpdatable;
    }

    public void setIsUpdatable(int isUpdatable) {
        this.isUpdatable = isUpdatable;
    }

    public int getIsDownloadable() {
        return isDownloadable;
    }

    public void setIsDownloadable(int isDownloadable) {
        this.isDownloadable = isDownloadable;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }
}
