package org.code4everything.efo.stand.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.bean.Response;
import org.code4everything.boot.message.VerifyCodeUtils;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.efo.base.util.CommonUtils;
import org.code4everything.efo.stand.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

/**
 * @author pantao
 * @since 2019-04-11
 */
@RestController
@Api(tags = "公共接口")
public class CommonController extends BaseController {

    private final UserService userService;

    @Autowired
    public CommonController(UserService userService) {this.userService = userService;}

    @PostMapping("/code/{email}/send")
    @ApiOperation("发送验证码")
    public Response sendCode(@PathVariable String email) throws MessagingException {
        return CommonUtils.sendCode(this, email);
    }

    @GetMapping("/code/{email}/validate")
    @ApiOperation("校验验证码")
    @ApiImplicitParam(name = "code", value = "验证码")
    public Response<Boolean> validateCode(@PathVariable String email, @RequestParam String code) {
        return parseBoolean("验证码正确", "验证码错误", VerifyCodeUtils.validate(email, code));
    }

    @GetMapping("/email/{email}/check")
    @ApiOperation("检测邮箱是否已经存在")
    public Response<Boolean> checkEmail(@PathVariable String email) {
        userService.checkEmail(email);
        return successResult("邮箱不存在，可注册");
    }

    @GetMapping("/username/{username}/check")
    @ApiOperation("检测用户名是否已经存在")
    public Response<Boolean> checkUsername(@PathVariable String username) {
        userService.checkUsername(username);
        return successResult("用户名不存在，可注册");
    }
}
