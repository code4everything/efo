package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.service.IConfigService;
import com.zhazhapan.modules.constant.ValueConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/1/22
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    private final IConfigService configService;

    private final HttpServletRequest request;

    @Autowired
    public ConfigController(IConfigService configService, HttpServletRequest request) {
        this.configService = configService;
        this.request = request;
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String updateConfig(String config) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        if (user.getPermission() > ValueConsts.TWO_INT) {
            EfoApplication.settings.setJsonObject(config);
            return "{\"message\":\"saved successfully\"}";
        } else {
            return "{\"message\":\"permission denied\"}";
        }
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll() {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        if (user.getPermission() > ValueConsts.TWO_INT) {
            return EfoApplication.settings.toString();
        } else {
            return "{\"message\":\"permission denied\"}";
        }
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/global", method = RequestMethod.GET)
    public String getGlobalConfig() {
        return configService.getGlobalConfig();
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUserConfig() {
        return configService.getUserConfig();
    }
}
