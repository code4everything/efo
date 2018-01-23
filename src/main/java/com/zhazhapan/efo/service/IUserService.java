package com.zhazhapan.efo.service;

import com.zhazhapan.efo.entity.User;

/**
 * @author pantao
 * @date 2018/1/22
 */
public interface IUserService {

    /**
     * 登录
     *
     * @param loginName 登录名
     * @param password 密码
     *
     * @return {@link User}
     */
    User login(String loginName, String password);

    /**
     * 注册
     *
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     *
     * @return 是否插入成功
     */
    boolean register(String username, String email, String password);

    /**
     * 重置密码
     *
     * @param email 邮箱
     * @param password 密码
     *
     * @return {@link Boolean}
     */
    boolean resetPassword(String email, String password);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     *
     * @return {@link Boolean}
     */
    boolean usernameExists(String username);
}
