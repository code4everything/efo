package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.efo.service.impl.UserServiceImpl;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.DateUtils;
import com.zhazhapan.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * @author pantao
 * @date 2018/1/22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    HttpServletRequest request;

    @AuthInterceptor
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getInfo() {
        User user = (User) request.getSession().getAttribute("user");
        JSONObject object = JSON.parseObject(user.toString());
        object.remove("id");
        object.remove("password");
        return object.toString();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, boolean auto, String token) {
        User user;
        int userId = 0;
        try {
            userId = EfoApplication.tokens.get(token);
        } catch (NullPointerException ignored) {
            // do nothing
        }
        if (Checker.isNullOrEmpty(token) || userId < 1) {
            user = userService.login(username, password);
        } else {
            user = userService.getUserById(userId);
        }
        JSONObject object = new JSONObject();
        if (Checker.isNull(user)) {
            object.put("status", "failed");
        } else {
            userService.updateUserLoginTime(user);
            user.setLastLoginTime(DateUtils.getCurrentTimestamp());
            request.getSession().setAttribute("user", user);
            object.put("status", "success");
            if (auto) {
                token = RandomUtils.getRandomStringOnlyLetter(64);
                EfoApplication.tokens.put(token, user.getId());
                object.put("token", token);
            }
        }
        return object.toString();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String username, String email, String password, String code) {
        JSONObject object = new JSONObject();
        boolean emilVerify = EfoApplication.settings.getBooleanUseEval(ConfigConsts.EMAIL_VERIFY_OF_SETTINGS);
        object.put("status", "error");
        if (!emilVerify || Checker.checkNull(code).equals(String.valueOf(request.getSession().getAttribute(DefaultValues.CODE_STRING)))) {
            if (userService.usernameExists(username)) {
                object.put("message", "用户名已经存在");
            } else if (userService.register(username, email, password)) {
                object.put("status", "success");
            } else {
                object.put("message", "数据格式不合法");
            }
        } else {
            object.put("message", "验证码校验失败");
        }
        return object.toString();
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public String resetPassword(String email, String code, String password) {
        JSONObject object = new JSONObject();
        object.put("status", "error");
        if (Checker.checkNull(code).equals(String.valueOf(request.getSession().getAttribute(DefaultValues.CODE_STRING)))) {
            if (userService.resetPassword(email, password)) {
                object.put("status", "success");
            } else {
                object.put("message", "格式不合法");
            }
        } else {
            object.put("message", "验证码校验失败");
        }
        return object.toString();
    }

    @RequestMapping(value = "/username/exists", method = RequestMethod.GET)
    public String usernameExists(String username) {
        JSONObject object = new JSONObject();
        object.put("exists", userService.usernameExists(username));
        return object.toString();
    }
}
