package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.enums.InterceptorLevel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pantao
 * @since 2018/1/25
 */
@Controller
public class ViewController {

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/filemanager", method = RequestMethod.GET)
    public String fileManager() {
        return "/filemanager";
    }

    @AuthInterceptor
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "/upload";
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/index";
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin() {
        return "/signin";
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "/admin";
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "<b>test</b>";
    }
}
