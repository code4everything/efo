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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "/user", description = "用户相关操作")
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

    @ApiOperation(value = "更新用户权限（注：不是文件权限）")
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

    @ApiOperation("重置用户密码（管理员接口）")
    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/reset/{id}/{password}", method = RequestMethod.PUT)
    public String resetPassword(@PathVariable("id") int id, @PathVariable("password") String password) {
        return ControllerUtils.getResponse(userService.resetPassword(id, password));
    }

    @ApiOperation(value = "更新用户的默认文件权限")
    @ApiImplicitParam(name = "auth", value = "权限", example = "1,1,1,1", required = true)
    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{id}/auth", method = RequestMethod.PUT)
    public String updateFileAuth(@PathVariable("id") int id, String auth) {
        return ControllerUtils.getResponse(userService.updateFileAuth(id, auth));
    }

    @ApiOperation(value = "获取所有用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "指定用户（默认所有用户）"), @ApiImplicitParam(name = "offset",
            value = "偏移量", required = true)})
    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getUser(String user, int offset) {
        User u = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        return Formatter.listToJson(userService.listUser(u.getPermission(), user, offset));
    }

    @ApiOperation(value = "更新我的基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "avatar", value = "头像（可空）"), @ApiImplicitParam(name = "realName",
            value = "真实姓名（可空）"), @ApiImplicitParam(name = "email", value = "邮箱（可空）"), @ApiImplicitParam(name =
            "code", value = "验证码（可空）")})
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

    @ApiOperation(value = "更新我的密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "oldPassword", value = "原密码", required = true), @ApiImplicitParam
            (name = "newPassword", value = "新密码", required = true)})
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

    @ApiOperation(value = "获取我的基本信息")
    @AuthInterceptor(InterceptorLevel.USER)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getInfo() {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        JSONObject object = JSON.parseObject(user.toString());
        object.remove(ValueConsts.ID_STRING);
        object.remove(ValueConsts.PASSWORD_STRING);
        return object.toString();
    }

    @ApiOperation(value = "登录（用户名密码和token必须有一个输入）")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名"), @ApiImplicitParam(name
            = "password", value = "密码"), @ApiImplicitParam(name = "auto", value = "是否自动登录", dataType = "Boolean"),
            @ApiImplicitParam(name = "token", value = "用于自动登录")})
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

    @ApiOperation(value = "用户注册（当不需要验证邮箱时，邮箱和验证码可空）")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", required = true), @ApiImplicitParam(name
            = "email", value = "邮箱"), @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "code", value = "验证码")})
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

    @ApiOperation(value = "重置我的密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "邮箱", required = true), @ApiImplicitParam(name =
            "code", value = "验证码", required = true), @ApiImplicitParam(name = "password", value = "密码", required =
            true)})
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

    @ApiOperation(value = "检测用户名是否已经注册")
    @ApiImplicitParam(name = "username", value = "用户名", required = true)
    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/username/exists", method = RequestMethod.GET)
    public String usernameExists(String username) {
        jsonObject.put("exists", userService.usernameExists(username));
        return jsonObject.toString();
    }

    @ApiOperation(value = "检测邮箱是否已经注册")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true)
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
