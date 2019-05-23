package org.code4everything.efo.stand.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.efo.base.model.vo.user.RegisterVO;
import org.code4everything.efo.base.model.vo.user.UserInfoVO;
import org.code4everything.efo.base.util.Checker;
import org.code4everything.efo.stand.web.service.UserService;
import org.code4everything.efo.stand.web.shiro.ShiroUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author pantao
 * @since 2019-04-11
 */
@RestController
@RequestMapping("/")
@Api(tags = "用户接口")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @PatchMapping("/user/avatar/update")
    @ApiOperation("更新头像")
    public Response<String> updateAvatar(@RequestBody MultipartFile avatar) {
        return successResult();
    }

    @PatchMapping("/user/username/{username}/update")
    @ApiOperation("更新用户名")
    public Response<String> updateUsername(@PathVariable String username) {
        userService.updateUsername(username);
        return successResult("用户名更新成功", username);
    }

    @PatchMapping("/user/email/{email}/update")
    @ApiOperation("更新邮箱")
    @ApiImplicitParam(name = "code", value = "验证码")
    public Response<String> updateEmail(@PathVariable String email, @RequestParam String code) {
        Checker.checkCode(email, code);
        userService.updateEmail(email, code);
        return successResult("邮箱更新成功", email);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Response<UserInfoVO> register(@Valid @RequestBody RegisterVO registerVO) {
        // 校验参数
        Checker.checkPassword(registerVO.getPassword());
        Checker.checkUsername(registerVO.getUsername());
        Checker.checkCode(registerVO.getEmail(), registerVO.getCode());
        return successResult(userService.register(registerVO));
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "login_name", value = "登录名：邮箱或用户名", required = true),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true)})
    public Response<UserInfoVO> login(@RequestParam("login_name") String loginName, @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password, true);
        subject.login(token);
        return successResult("登录成功", ShiroUtils.getUser().copyInto(new UserInfoVO()));
    }

    @DeleteMapping("/logout")
    @ApiOperation("用户登出")
    public Response<String> logout() {
        SecurityUtils.getSubject().logout();
        return successResult("登出成功");
    }
}
