package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @date 2018/1/23
 */
@RestController
public class CommonController {

    @Autowired
    CommonServiceImpl commonService;

    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    public String sendVerifyCode(String email) {
        JSONObject object = new JSONObject();
        if (commonService.sendVerifyCode(email)) {
            object.put("status", "success");
        } else {
            object.put("status", "error");
        }
        return object.toString();
    }
}
