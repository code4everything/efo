package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.config.TokenConfigurer;
import com.zhazhapan.efo.dao.UserDAO;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IUserService;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.zhazhapan.efo.EfoApplication.*;

/**
 * @author pantao
 * @date 2018/1/22
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public User login(String loginName, String password, String token, HttpServletResponse response) {
        boolean allowLogin = settings.getBooleanUseEval(ConfigConsts.ALLOW_LOGIN_OF_SETTINGS);
        User user = null;
        if (allowLogin) {
            if (Checker.isNotEmpty(token) && EfoApplication.tokens.containsKey(token)) {
                user = EfoApplication.tokens.get(token);
                if (Checker.isNotNull(response)) {
                    Cookie cookie = new Cookie("token", TokenConfigurer.generateToken(token, user));
                    cookie.setMaxAge(30 * 24 * 60 * 60);
                    response.addCookie(cookie);
                }
            }
            if (Checker.isNull(user) && Checker.isNotEmpty(loginName) && Checker.isNotEmpty(password)) {
                user = userDAO.login(loginName, password);
                removeTokenByValue(user);
            }
            updateUserLoginTime(user);
        }
        return user;
    }

    @Override
    public boolean register(String username, String email, String password) {
        boolean allowRegister = settings.getBooleanUseEval(ConfigConsts.ALLOW_REGISTER_OF_SETTINGS);
        if (allowRegister) {
            boolean isValid = Checker.isEmail(email) && checkPassword(password) && usernamePattern.matcher(username).matches();
            if (isValid) {
                User user = new User(username, "", email, password);
                int[] auth = new int[5];
                for (int i = 0; i < ConfigConsts.USER_AUTH_OF_SETTINGS.length; i++) {
                    auth[i] = settings.getBooleanUseEval(ConfigConsts.USER_AUTH_OF_SETTINGS[i]) ? 1 : 0;
                }
                user.setAuth(auth[0], auth[1], auth[2], auth[3], auth[4]);
                return userDAO.insertUser(user);
            }
        }
        return false;
    }

    @Override
    public boolean resetPassword(String email, String password) {

        return Checker.isEmail(email) && checkPassword(password) && userDAO.updatePasswordByEmail(password, email);
    }

    @Override
    public boolean checkPassword(String password) {
        int min = settings.getIntegerUseEval(ConfigConsts.PASSWORD_MIN_LENGTH_OF_SETTINGS);
        int max = settings.getIntegerUseEval(ConfigConsts.PASSWORD_MAX_LENGTH_OF_SETTINGS);
        return Checker.isLimited(password, min, max);
    }

    @Override
    public boolean usernameExists(String username) {
        return userDAO.checkUsername(username) > 0;
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void updateUserLoginTime(User user) {
        if (Checker.isNotNull(user)) {
            user.setLastLoginTime(DateUtils.getCurrentTimestamp());
            userDAO.updateUserLoginTime(user.getId());
        }
    }

    @Override
    public void removeTokenByValue(User user) {
        if (Checker.isNotNull(user)) {
            String removeKey = "";
            for (String key : tokens.keySet()) {
                if (tokens.get(key).getId() == user.getId()) {
                    removeKey = key;
                    break;
                }
            }
            if (Checker.isNotEmpty(removeKey)) {
                tokens.remove(removeKey);
                TokenConfigurer.saveToken();
            }
        }
    }

    @Override
    public boolean updatePassword(String password, int id) {
        return checkPassword(password) && userDAO.updatePasswordById(id, password);
    }
}
