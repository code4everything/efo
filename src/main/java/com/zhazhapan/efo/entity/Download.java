package com.zhazhapan.efo.entity;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.Formatter;

import javax.persistence.*;
import java.util.Date;

/**
 * 下载记录表
 *
 * @author pantao
 * @date 2018/1/11
 */
@Entity
@Table(name = "download")
public class Download {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Date createTime;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "file_id")
    private int fileId;

    public Download(int userId, int fileId) {
        this.userId = userId;
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
}
