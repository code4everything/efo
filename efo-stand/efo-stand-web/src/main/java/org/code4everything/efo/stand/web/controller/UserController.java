package org.code4everything.efo.stand.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.code4everything.boot.bean.Response;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.efo.base.model.vo.RegisterVO;
import org.code4everything.efo.base.model.vo.UserInfoVO;
import org.code4everything.efo.stand.dao.domain.User;
import org.code4everything.efo.stand.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pantao
 * @since 2019-04-11
 */
@RestController
@Api(tags = "用户接口")
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Response<UserInfoVO> register(@Valid @RequestBody RegisterVO registerVO) {
        return successResult(userService.register(registerVO));
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "loginName", value = "登录名：邮箱或用户名", required = true),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true)})
    public Response<UserInfoVO> login(@RequestParam String loginName, @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password, true);
        subject.login(token);
        return successResult("登录成功", ((User) subject.getPrincipal()).copyInto(new UserInfoVO()));
    }

    @DeleteMapping("/logout")
    @ApiOperation("用户登出")
    public Response<String> logout() {
        SecurityUtils.getSubject().logout();
        return successResult("登出成功");
    }
}
