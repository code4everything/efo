package com.zhazhapan.efo.entity;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 文件表
 *
 * @author pantao
 * @date 2018/1/11
 */
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String suffix;

    /**
     * 文件名的base64编码
     */
    private String base64;

    private String localUrl;

    private String visitUrl;

    private long size;

    private Date createTime = new Date();

    private String description;

    private int checkTimes = 0;

    private int downloadTimes = 0;

    private String tag;

    private int userId;

    private int categoryId;

    public File(String name, String suffix, String base64, String localUrl, String visitUrl, String description, String tag, int userId, int categoryId) {
        this.name = name;
        this.base64 = base64;
        this.suffix = suffix;
        this.categoryId = categoryId;
        this.description = description;
        this.localUrl = localUrl;
        this.visitUrl = visitUrl;
        this.tag = tag;
        this.userId = userId;
        this.size = new java.io.File(localUrl).length();
    }

    public int getId() {
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

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("suffix", suffix);
        jsonObject.put("base64", base64);
        jsonObject.put("localUrl", localUrl);
        jsonObject.put("visitUrl", visitUrl);
        jsonObject.put("size", size);
        jsonObject.put("description", description);
        jsonObject.put("createTime", createTime);
        jsonObject.put("checkTimes", checkTimes);
        jsonObject.put("downloadTimes", downloadTimes);
        jsonObject.put("tag", tag);
        jsonObject.put("userId", userId);
        jsonObject.put("categoryId", categoryId);
        return jsonObject.toString();
    }
}
