package com.zhazhapan.efo.model;

import com.zhazhapan.efo.util.BeanUtils;

import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/1/19
 */
public class AuthRecord {

    private long id;

    private int userId;

    private long fileId;

    private String username;

    private String fileName;

    private String localUrl;

    private int isDownloadable;

    private int isUploadable;

    private int isDeletable;

    private int isUpdatable;

    private int isVisible;

    private Timestamp createTime;

    public AuthRecord(long id, int userId, long fileId, String username, String fileName, String localUrl, int
            isDownloadable, int isUploadable, int isDeletable, int isUpdatable, int isVisible, Timestamp createTime) {
        this.id = id;
        this.userId = userId;
        this.fileId = fileId;
        this.username = username;
        this.fileName = fileName;
        this.localUrl = localUrl;
        this.isDownloadable = isDownloadable;
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isVisible = isVisible;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return BeanUtils.toPrettyJson(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
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

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public int getIsDownloadable() {
        return isDownloadable;
    }

    public void setIsDownloadable(int isDownloadable) {
        this.isDownloadable = isDownloadable;
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

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
