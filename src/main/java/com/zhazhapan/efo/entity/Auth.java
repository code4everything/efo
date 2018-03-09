package com.zhazhapan.efo.entity;

import com.zhazhapan.efo.util.BeanUtils;

import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/1/18
 */
public class Auth {

    private long id;

    private int isUploadable;

    private int isDeletable;

    private int isUpdatable;

    private int isDownloadable;

    private int isVisible;

    private int userId;

    private long fileId;

    private Timestamp createTime;

    public Auth(int userId, long fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public Auth(long id, int isUploadable, int isDeletable, int isUpdatable, int userId, long fileId, int isVisible,
                int isDownloadable, Timestamp createTime) {
        this.id = id;
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isDownloadable = isDownloadable;
        this.isVisible = isVisible;
        this.userId = userId;
        this.fileId = fileId;
        this.createTime = createTime;
    }

    public void setAuth(int isDownloadable, int isUploadable, int isDeletable, int isUpdatable, int isVisible) {
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isDownloadable = isDownloadable;
        this.isVisible = isVisible;
    }

    public void setAuth(int[] auth) {
        setAuth(auth[0], auth[1], auth[2], auth[3], auth[4]);
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
