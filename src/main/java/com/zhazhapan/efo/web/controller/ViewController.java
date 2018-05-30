package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.enums.InterceptorLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author pantao
 * @since 2018/1/25
 */
@Controller
@Api(description = "视图页面映射")
public class ViewController {

    @ApiOperation(value = "远程文件管理页面")
    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/filemanager", method = RequestMethod.GET)
    public String fileManager() {
        return "/filemanager";
    }

    @ApiOperation(value = "上传页面")
    @AuthInterceptor
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "/upload";
    }

    @ApiOperation(value = "首页")
    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/index";
    }

    @ApiOperation(value = "登录、注册、忘记密码页面")
    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin() {
        return "/signin";
    }

    @ApiOperation(value = "管理员页面")
    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "/admin";
    }

    @ApiIgnore
    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "<b>test</b>";
    }
}
