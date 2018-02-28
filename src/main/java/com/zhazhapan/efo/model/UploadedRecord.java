package com.zhazhapan.efo.model;

import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/2/28
 */
public class UploadedRecord {

    private long id;

    private int userId;

    private String username;

    private String email;

    private String fileName;

    private String categoryName;

    private String localUrl;

    private String visitUrl;

    private Timestamp createTime;

    public UploadedRecord(long id, int userId, String username, String email, String fileName, String categoryName,
                          String localUrl, String visitUrl, Timestamp createTime) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fileName = fileName;
        this.categoryName = categoryName;
        this.localUrl = localUrl;
        this.visitUrl = visitUrl;
        this.createTime = createTime;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
