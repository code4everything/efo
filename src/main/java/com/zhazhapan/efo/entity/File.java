package com.zhazhapan.efo.entity;

import com.zhazhapan.efo.util.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 文件表
 *
 * @author pantao
 * @date 2018/1/11
 */
@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String suffix;

    @Column(name = "local_url")
    private String localUrl;

    @Column(name = "visit_url")
    private String visitUrl;

    @Column
    private long size;

    @Column(name = "create_time")
    private Timestamp createTime;

    private String description;

    @Column(name = "check_times")
    private int checkTimes;

    @Column(name = "download_times")
    private int downloadTimes;

    @Column
    private String tag;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "category_id")
    private int categoryId;

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
    private Timestamp lastModifyTime;
    
    public File(String name, String suffix, String localUrl, String visitUrl, String description, String tag, int userId, int categoryId) {
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

    public File(int id, String name, String suffix, String localUrl, String visitUrl, long size, Timestamp createTime, String description, int checkTimes, int downloadTimes, String tag, int userId, int categoryId, int isDownloadable, int isUploadable, int isVisible, int isDeletable, int isUpdatable, Timestamp lastModifyTime) {
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
