package com.zhazhapan.efo.service;

import com.zhazhapan.efo.entity.User;

/**
 * @author pantao
 * @date 2018/1/22
 */
public interface IUserService {

    User login(String loginName, String password);
}
