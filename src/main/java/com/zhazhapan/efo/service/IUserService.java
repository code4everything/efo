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
}
