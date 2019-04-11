package org.code4everything.efo.stand.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.code4everything.boot.bean.Response;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.efo.base.util.CommonUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * @author pantao
 * @since 2019-04-11
 */
@RestController
@Api("公共接口")
public class CommonController extends BaseController {

    @PostMapping("/code/send/{email}")
    @ApiOperation("发送验证码")
    public Response sendCode(@PathVariable String email) throws MessagingException {
        return CommonUtils.sendCode(this, email);
    }
}
