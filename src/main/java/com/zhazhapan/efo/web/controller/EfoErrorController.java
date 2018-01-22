package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @date 2018/1/22
 */
@RestController
public class EfoErrorController implements ErrorController {

    @RequestMapping("/exception")
    public String handleError() {
        JSONObject object = new JSONObject();
        object.put("error", "internal error, please try again later");
        return object.toString();
    }

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound() {
        JSONObject object = new JSONObject();
        object.put("404", "page not found");
        return object.toString();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}