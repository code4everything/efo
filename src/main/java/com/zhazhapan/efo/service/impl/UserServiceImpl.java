package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.dao.UserDAO;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IUserService;
import com.zhazhapan.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @date 2018/1/22
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public User login(String loginName, String password) {
        boolean allowLogin = EfoApplication.settings.getBooleanUseEval(ConfigConsts.ALLOW_LOGIN_OF_SETTINGS);
        if (allowLogin && Checker.isNotEmpty(loginName) && Checker.isNotEmpty(password)) {
            User user = userDAO.login(loginName, password);
            if (Checker.isNotNull(user)) {
                userDAO.updateUserLoginTime(user.getId());
                return user;
            }
        }
        return null;
    }
}
