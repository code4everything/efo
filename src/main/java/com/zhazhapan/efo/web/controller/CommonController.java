package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.enums.InterceptorLevel;
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
@RequestMapping("/common")
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private JSONObject jsonObject;

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/code/send", method = RequestMethod.GET)
    public String sendVerifyCode(String email) {
        int code = commonService.sendVerifyCode(email);
        if (code > 0) {
            request.getSession().setAttribute(DefaultValues.CODE_STRING, code);
            logger.info("verify code: " + code);
            jsonObject.put("status", "success");
        } else {
            jsonObject.put("status", "error");
        }
        return jsonObject.toString();
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/code/verify", method = RequestMethod.GET)
    public String verifyCode(String code) {
        if (Checker.checkNull(code).equals(String.valueOf(request.getSession().getAttribute(DefaultValues.CODE_STRING)))) {
            jsonObject.put("status", "success");
        } else {
            jsonObject.put("status", "error");
        }
        return jsonObject.toString();
    }
}
