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

    private final IAdminService adminService;

    private final HttpServletRequest request;

    private final JSONObject jsonObject;

    @Autowired
    public AdminController(IAdminService adminService, HttpServletRequest request, JSONObject jsonObject) {
        this.adminService = adminService;
        this.request = request;
        this.jsonObject = jsonObject;
    }
}
