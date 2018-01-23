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

    @Override
    public boolean register(String username, String email, String password) {
        boolean allowRegister = EfoApplication.settings.getBooleanUseEval(ConfigConsts.ALLOW_REGISTER_OF_SETTINGS);
        if (allowRegister) {
            int min = EfoApplication.settings.getIntegerUseEval(ConfigConsts.PASSWORD_MIN_LENGTH_OF_SETTINGS);
            int max = EfoApplication.settings.getIntegerUseEval(ConfigConsts.PASSWORD_MAX_LENGTH_OF_SETTINGS);
            boolean isValid = Checker.isEmail(email) && Checker.isLimited(password, min, max) && EfoApplication.usernamePattern.matcher(username).matches();
            if (isValid) {
                User user = new User(username, "", email, password);
                int[] auth = new int[5];
                for (int i = 0; i < ConfigConsts.USER_AUTH_OF_SETTINGS.length; i++) {
                    auth[i] = EfoApplication.settings.getBooleanUseEval(ConfigConsts.USER_AUTH_OF_SETTINGS[i]) ? 1 : 0;
                }
                user.setAuth(auth[0], auth[1], auth[2], auth[3], auth[4]);
                return userDAO.insertUser(user);
            }
        }
        return false;
    }

    @Override
    public boolean resetPassword(String email, String password) {
        int min = EfoApplication.settings.getIntegerUseEval(ConfigConsts.PASSWORD_MIN_LENGTH_OF_SETTINGS);
        int max = EfoApplication.settings.getIntegerUseEval(ConfigConsts.PASSWORD_MAX_LENGTH_OF_SETTINGS);
        if (Checker.isEmail(email) && Checker.isLimited(password, min, max)) {
            return userDAO.updatePasswordByEmail(password, email);
        }
        return false;
    }

    @Override
    public boolean usernameExists(String username) {
        return userDAO.checkUsername(username) > 0;
    }
}
