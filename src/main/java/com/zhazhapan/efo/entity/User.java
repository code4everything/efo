package com.zhazhapan.efo.entity;

import com.zhazhapan.efo.util.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用户表
 *
 * @author pantao
 * @date 2018/1/11
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username;

    @Column(name = "real_name")
    private String realName;

    @Column
    private String email;

    @Column
    private String password;

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
    /**
     * 权限级别：0（禁止登录），1（正常，普通用户），2（正常，管理员），3（正常，超级管理员）
     */
    @Column
    private int permission;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "last_login_time")
    private Timestamp lastLoginTime;

    public User(String username, String realName, String email, String password) {
        this.username = username;
        this.realName = realName;
        this.email = email;
        this.password = password;
    }

    public User(int id, String username, String realName, String email, String password, int permission, Timestamp createTime, Timestamp lastLoginTime, int isDownloadable, int isUploadable, int isVisible, int isDeletable, int isUpdatable) {
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.email = email;
        this.password = password;
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isDownloadable = isDownloadable;
        this.isVisible = isVisible;
        this.permission = permission;
        this.createTime = createTime;
        this.lastLoginTime = lastLoginTime;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
