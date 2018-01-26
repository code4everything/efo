package com.zhazhapan.efo.service;

import com.zhazhapan.efo.entity.User;

import javax.servlet.http.HttpServletResponse;

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
     * @param token 自动登录
     * @param response 响应
     *
     * @return {@link User}
     */
    User login(String loginName, String password, String token, HttpServletResponse response);

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

    /**
     * 通过编号获取用户
     *
     * @param id 编号
     *
     * @return {@link User}
     */
    User getUserById(int id);

    /**
     * 更新用户登录时间
     *
     * @param user {@link User}
     */
    void updateUserLoginTime(User user);

    /**
     * 通知值删除Token
     *
     * @param user {@link User}
     */
    void removeTokenByValue(User user);

    /**
     * 更新密码
     *
     * @param password 密码
     * @param id 用户编号
     *
     * @return 是否更新成功
     */
    boolean updatePassword(String password, int id);

    /**
     * 检查密码是否合法
     *
     * @param password 密码
     *
     * @return {@link Boolean}
     */
    boolean checkPassword(String password);
}
