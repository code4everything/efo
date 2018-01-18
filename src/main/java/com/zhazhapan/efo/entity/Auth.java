package com.zhazhapan.efo.entity;

import com.zhazhapan.efo.util.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author pantao
 * @date 2018/1/18
 */
@Entity
@Table(name = "auth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int isUploadable;

    @Column
    private int isDeletable;

    @Column
    private int isUpdatable;

    @Column
    private int isDownloadable;

    @Column
    private int isVisible;

    @Column
    private int userId;

    @Column
    private long fileId;

    @Column
    private Timestamp createTime;

    public Auth(int userId, long fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public Auth(int id, int isUploadable, int isDeletable, int isUpdatable, int userId, long fileId, int isVisible, int isDownloadable, Timestamp createTime) {
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

    @Override
    public String toString() {
        return BeanUtils.toPrettyJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
