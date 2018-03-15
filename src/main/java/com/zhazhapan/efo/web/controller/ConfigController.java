package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.efo.service.IConfigService;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.NetUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author pantao
 * @since 2018/1/22
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    private static Logger logger = Logger.getLogger(ConfigController.class);
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
            //打包成jar之后无法修改config.json文件
            try {
                FileExecutor.saveFile(NetUtils.urlToString(EfoApplication.class.getResource(DefaultValues
                        .SETTING_PATH)), EfoApplication.settings.toString());
            } catch (IOException e) {
                logger.error(e.getMessage());
                return "{\"message\":\"internal error, cannot save\"}";
            }
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
