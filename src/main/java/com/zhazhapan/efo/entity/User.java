package com.zhazhapan.efo.entity;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.Formatter;

import javax.persistence.*;
import java.util.Date;

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

    /**
     * 权限级别：0（禁止登录），1（正常，普通用户），2（正常，管理员），3（正常，超级管理员）
     */
    @Column
    private int permission;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    public User(String username, String realName, String email, String password, int permission) {
        this.username = username;
        this.realName = realName;
        this.email = email;
        this.password = password;
        this.permission = permission;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("username", username);
        jsonObject.put("realName", realName);
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("permission", permission);
        jsonObject.put("createTime", createTime);
        jsonObject.put("lastLoginTime", lastLoginTime);
        return Formatter.formatJson(jsonObject.toString());
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
