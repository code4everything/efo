package org.code4everything.efo.stand.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.bean.Response;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.efo.base.model.vo.RegisterVO;
import org.code4everything.efo.base.model.vo.UserInfoVO;
import org.code4everything.efo.stand.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
