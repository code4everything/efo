package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.config.TokenConfigurer;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.efo.service.impl.UserServiceImpl;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.encryption.JavaEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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

    private JSONObject jsonObject = new JSONObject();

    @AuthInterceptor
    @RequestMapping(value = "/password/update", method = RequestMethod.POST)
    public String updatePassword(String oldPassword, String newPassword) {
        User user = (User) request.getSession().getAttribute("user");
        jsonObject.put("status", "error");
        try {
            if (user.getPassword().equals(JavaEncrypt.sha256(oldPassword))) {
                if (userService.updatePassword(newPassword, user.getId())) {
                    jsonObject.put("status", "success");
                    userService.removeTokenByValue(user);
                } else {
                    jsonObject.put("message", "新密码格式不正确");
                }
            } else {
                jsonObject.put("message", "原密码不正确");
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            jsonObject.put("message", "服务器内部错误，请稍后重新尝试");
        }
        return jsonObject.toString();
    }

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
        User user = userService.login(username, password, token, null);
        if (Checker.isNull(user)) {
            jsonObject.put("status", "failed");
        } else {
            request.getSession().setAttribute("user", user);
            jsonObject.put("status", "success");
            if (auto) {
                jsonObject.put("token", TokenConfigurer.generateToken(token, user));
            } else {
                jsonObject.put("token", "");
                userService.removeTokenByValue(user);
            }
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String username, String email, String password, String code) {
        boolean emilVerify = EfoApplication.settings.getBooleanUseEval(ConfigConsts.EMAIL_VERIFY_OF_SETTINGS);
        jsonObject.put("status", "error");
        if (!emilVerify || Checker.checkNull(code).equals(String.valueOf(request.getSession().getAttribute(DefaultValues.CODE_STRING)))) {
            if (userService.usernameExists(username)) {
                jsonObject.put("message", "用户名已经存在");
            } else if (userService.register(username, email, password)) {
                jsonObject.put("status", "success");
            } else {
                jsonObject.put("message", "数据格式不合法");
            }
        } else {
            jsonObject.put("message", "验证码校验失败");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public String resetPassword(String email, String code, String password) {
        jsonObject.put("status", "error");
        if (Checker.checkNull(code).equals(String.valueOf(request.getSession().getAttribute(DefaultValues.CODE_STRING)))) {
            if (userService.resetPassword(email, password)) {
                jsonObject.put("status", "success");
            } else {
                jsonObject.put("message", "格式不合法");
            }
        } else {
            jsonObject.put("message", "验证码校验失败");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/username/exists", method = RequestMethod.GET)
    public String usernameExists(String username) {
        jsonObject.put("exists", userService.usernameExists(username));
        return jsonObject.toString();
    }
}
