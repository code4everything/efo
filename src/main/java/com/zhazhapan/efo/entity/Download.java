package com.zhazhapan.efo.entity;

import com.zhazhapan.efo.util.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private long id;

    @Column
    private Timestamp createTime;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "file_id")
    private int fileId;

    public Download(int userId, int fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public Download(int id, Timestamp createTime, int userId, int fileId) {
        this.id = id;
        this.createTime = createTime;
        this.userId = userId;
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return BeanUtils.toPrettyJson(this);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
