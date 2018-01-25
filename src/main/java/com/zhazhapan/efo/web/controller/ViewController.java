package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.enums.InterceptorLevel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author pantao
 * @date 2018/1/25
 */
@Controller
public class ViewController {

    @AuthInterceptor
    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @RequestMapping("/signin")
    public String signin() {
        return "/signin";
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping("/admin")
    public String admin() {
        return "/admin";
    }
}
