package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.modules.constant.Values;
import com.zhazhapan.util.FileExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author pantao
 * @date 2018/1/22
 */
@RestController
public class PageController {

    @RequestMapping("/login")
    public String login() {
        return getHtml("login");
    }

    @RequestMapping("/index")
    public String index() {
        return getHtml("index");
    }

    public String getHtml(String page) {
        try {
            return FileExecutor.readFile(EfoApplication.CLASS_PATH + "static" + Values.SEPARATOR + page + ".html");
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
