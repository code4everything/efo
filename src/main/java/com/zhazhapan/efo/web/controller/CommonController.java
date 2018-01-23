package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.efo.service.impl.CommonServiceImpl;
import com.zhazhapan.util.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @date 2018/1/23
 */
@RestController
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/code/send", method = RequestMethod.GET)
    public String sendVerifyCode(String email) {
        JSONObject object = new JSONObject();
        int code = commonService.sendVerifyCode(email);
        if (code > 0) {
            request.getSession().setAttribute(DefaultValues.CODE_STRING, code);
            logger.info("verify code: " + code);
            object.put("status", "success");
        } else {
            object.put("status", "error");
        }
        return object.toString();
    }

    @RequestMapping(value = "/code/verify", method = RequestMethod.GET)
    public String verifyCode(String code) {
        JSONObject object = new JSONObject();
        if (Checker.checkNull(code).equals(String.valueOf(request.getSession().getAttribute(DefaultValues.CODE_STRING)))) {
            object.put("status", "success");
        } else {
            object.put("status", "error");
        }
        return object.toString();
    }
}
