package com.zhazhapan.efo.entity;

import com.zhazhapan.efo.util.BeanUtils;

import java.sql.Timestamp;

/**
 * 文件表
 *
 * @author pantao
 * @since 2018/1/11
 */
public class File {

    private long id;

    private String name;

    private String suffix;

    private String localUrl;

    private String visitUrl;

    private long size;

    private Timestamp createTime;

    private String description;

    private int checkTimes;

    private int downloadTimes;

    private String tag;

    private int userId;

    private int categoryId;

    private int isUploadable;

    private int isDeletable;

    private int isUpdatable;

    private int isDownloadable;

    private int isVisible;

    private Timestamp lastModifyTime;

    public File(String name, String suffix, String localUrl, String visitUrl, String description, String tag, int
            userId, int categoryId) {
        this.name = name;
        this.suffix = suffix;
        this.categoryId = categoryId;
        this.description = description;
        this.localUrl = localUrl;
        this.visitUrl = visitUrl;
        this.tag = tag;
        this.userId = userId;
        this.size = new java.io.File(localUrl).length();
    }

    public File(long id, String name, String suffix, String localUrl, String visitUrl, long size, Timestamp
            createTime, String description, int checkTimes, int downloadTimes, String tag, int userId, int
            categoryId, int isDownloadable, int isUploadable, int isVisible, int isDeletable, int isUpdatable,
                Timestamp lastModifyTime) {
        this.id = id;
        this.name = name;
        this.suffix = suffix;
        this.localUrl = localUrl;
        this.visitUrl = visitUrl;
        this.size = size;
        this.createTime = createTime;
        this.description = description;
        this.checkTimes = checkTimes;
        this.downloadTimes = downloadTimes;
        this.tag = tag;
        this.userId = userId;
        this.categoryId = categoryId;
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isDownloadable = isDownloadable;
        this.isVisible = isVisible;
        this.lastModifyTime = lastModifyTime;
    }

    public void setAuth(int isDownloadable, int isUploadable, int isDeletable, int isUpdatable, int isVisible) {
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isDownloadable = isDownloadable;
        this.isVisible = isVisible;
    }

    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Override
    public String toString() {
        return BeanUtils.toPrettyJson(this);
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

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
