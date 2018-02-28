package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.enums.InterceptorLevel;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author pantao
 * @since 2018/1/22
 */
@Controller
public class CustomErrorController implements ErrorController {

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping("/exception")
    public String handleError() {
        return "error";
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound() {
        return "/404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}