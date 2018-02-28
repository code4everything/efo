package com.zhazhapan.efo.entity;

import com.zhazhapan.efo.util.BeanUtils;

import java.sql.Timestamp;

/**
 * 用户表
 *
 * @author pantao
 * @since 2018/1/11
 */
public class User {

    private int id;

    private String username;

    private String realName;

    private String email;

    private String password;

    private int isUploadable;

    private int isDeletable;

    private int isUpdatable;

    private int isDownloadable;

    private int isVisible;

    /**
     * 权限级别：0（禁止登录），1（正常，普通用户），2（正常，管理员），3（正常，超级管理员）
     */
    private int permission;

    private Timestamp createTime;

    private Timestamp lastLoginTime;

    private String avatar;

    public User(String username, String realName, String email, String password) {
        this.username = username;
        this.realName = realName;
        this.email = email;
        this.password = password;
    }

    public User(int id, String username, String realName, String email, String password, int permission, Timestamp
            createTime, Timestamp lastLoginTime, int isDownloadable, int isUploadable, int isVisible, int
            isDeletable, int isUpdatable, String avatar) {
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
        this.avatar = avatar;
    }

    public void setAuth(int isDownloadable, int isUploadable, int isDeletable, int isUpdatable, int isVisible) {
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isDownloadable = isDownloadable;
        this.isVisible = isVisible;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
