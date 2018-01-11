package com.zhazhapan.efo.entity;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.Formatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 下载记录表
 *
 * @author pantao
 * @date 2018/1/11
 */
@Entity
public class Download {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date createTime = new Date();

    private int userId;

    private int fileId;

    public Download(int userId, int fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("userId", userId);
        jsonObject.put("fileId", fileId);
        jsonObject.put("createTime", createTime);
        return Formatter.formatJson(jsonObject.toString());
    }
}
