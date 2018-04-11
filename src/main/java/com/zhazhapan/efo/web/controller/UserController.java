package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.config.TokenConfig;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.efo.service.IUserService;
import com.zhazhapan.efo.util.ControllerUtils;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.Formatter;
import com.zhazhapan.util.encryption.JavaEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author pantao
 * @since 2018/1/22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    private final HttpServletRequest request;

    private final JSONObject jsonObject;

    @Autowired
    public UserController(IUserService userService, HttpServletRequest request, JSONObject jsonObject) {
        this.userService = userService;
        this.request = request;
        this.jsonObject = jsonObject;
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{id}/{permission}", method = RequestMethod.PUT)
    public String updatePermission(@PathVariable("id") int id, @PathVariable("permission") int permission) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        if (user.getPermission() < ValueConsts.THREE_INT && permission > 1) {
            jsonObject.put("message", "权限不够，设置失败");
        } else if (userService.updatePermission(id, permission)) {
            jsonObject.put("message", "更新成功");
        } else {
            jsonObject.put("message", "更新失败，请稍后重新尝试");
        }
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/reset/{id}/{password}", method = RequestMethod.PUT)
    public String resetPassword(@PathVariable("id") int id, @PathVariable("password") String password) {
        return ControllerUtils.getResponse(userService.resetPassword(id, password));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{id}/auth", method = RequestMethod.PUT)
    public String updateFileAuth(@PathVariable("id") int id, String auth) {
        return ControllerUtils.getResponse(userService.updateFileAuth(id, auth));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getUser(String user, int offset) {
        User u = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        return Formatter.listToJson(userService.getUser(u.getPermission(), user, offset));
    }

    @AuthInterceptor(InterceptorLevel.USER)
    @RequestMapping(value = "/info", method = RequestMethod.PUT)
    public String updateBasicInfo(String avatar, String realName, String email, String code) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        jsonObject.put("message", "保存成功");
        boolean emilVerify = EfoApplication.settings.getBooleanUseEval(ConfigConsts.EMAIL_VERIFY_OF_SETTINGS);
        if (Checker.isNotEmpty(email) && !email.equals(user.getEmail())) {
            if (!emilVerify || isCodeValidate(code)) {
                if (userService.emailExists(email)) {
                    jsonObject.put("message", "邮箱更新失败，该邮箱已经存在");
                } else {
                    user.setEmail(email);
                }
            } else {
                jsonObject.put("message", "邮箱更新失败，验证码校验失败");
            }
        }
        if (userService.updateBasicInfoById(user.getId(), avatar, realName, user.getEmail())) {
            user.setAvatar(avatar);
            user.setRealName(realName);
            jsonObject.put("status", "success");
        } else {
            jsonObject.put("message", "服务器发生错误，请稍后重新尝试");
        }
        jsonObject.put("email", user.getEmail());
        return jsonObject.toString();
    }

    @AuthInterceptor(InterceptorLevel.USER)
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public String updatePassword(String oldPassword, String newPassword) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        jsonObject.put("status", "error");
        try {
            if (user.getPassword().equals(JavaEncrypt.sha256(oldPassword))) {
                if (userService.updatePasswordById(newPassword, user.getId())) {
                    jsonObject.put("status", "success");
                    TokenConfig.removeTokenByValue(user.getId());
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

    @AuthInterceptor(InterceptorLevel.USER)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getInfo() {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        JSONObject object = JSON.parseObject(user.toString());
        object.remove(ValueConsts.ID_STRING);
        object.remove(ValueConsts.PASSWORD_STRING);
        return object.toString();
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/login", method = RequestMethod.PUT)
    public String login(String username, String password, boolean auto, String token) {
        //使用密码登录
        User user = userService.login(username, password, ValueConsts.NULL_STRING, ValueConsts.NULL_RESPONSE);
        if (Checker.isNull(user) || user.getPermission() < 1) {
            jsonObject.put("status", "failed");
        } else {
            request.getSession().setAttribute(ValueConsts.USER_STRING, user);
            jsonObject.put("status", "success");
            if (auto) {
                jsonObject.put("token", TokenConfig.generateToken(token, user.getId()));
            } else {
                jsonObject.put("token", "");
                TokenConfig.removeTokenByValue(user.getId());
            }
        }
        return jsonObject.toString();
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String username, String email, String password, String code) {
        boolean emilVerify = EfoApplication.settings.getBooleanUseEval(ConfigConsts.EMAIL_VERIFY_OF_SETTINGS);
        jsonObject.put("status", "error");
        if (!emilVerify || isCodeValidate(code)) {
            if (userService.usernameExists(username)) {
                jsonObject.put("message", "用户名已经存在");
            } else if (userService.emailExists(email)) {
                jsonObject.put("message", "该邮箱已经被注册啦");
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

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/password/reset", method = RequestMethod.PUT)
    public String resetPassword(String email, String code, String password) {
        jsonObject.put("status", "error");
        if (isCodeValidate(code)) {
            if (userService.resetPasswordByEmail(email, password)) {
                jsonObject.put("status", "success");
            } else {
                jsonObject.put("message", "格式不合法");
            }
        } else {
            jsonObject.put("message", "验证码校验失败");
        }
        return jsonObject.toString();
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/username/exists", method = RequestMethod.GET)
    public String usernameExists(String username) {
        jsonObject.put("exists", userService.usernameExists(username));
        return jsonObject.toString();
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/email/exists", method = RequestMethod.GET)
    public String emailExists(String email) {
        jsonObject.put("exists", userService.emailExists(email));
        return jsonObject.toString();
    }

    private boolean isCodeValidate(String code) {
        return Checker.checkNull(code).equals(String.valueOf(request.getSession().getAttribute(DefaultValues
                .CODE_STRING)));
    }
}
