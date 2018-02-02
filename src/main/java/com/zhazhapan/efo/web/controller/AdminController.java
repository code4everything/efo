package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @date 2018/1/29
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminService adminService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    JSONObject jsonObject;
}
