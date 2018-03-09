package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.service.IAuthService;
import com.zhazhapan.efo.util.ControllerUtils;
import com.zhazhapan.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018/3/8
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {this.authService = authService;}

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String add(String files, String users, String auths) {
        System.out.println("files: " + files + " users: " + users + " auths: " + auths);
        return ControllerUtils.getResponse(authService.addAuth(files, users, auths));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAuth(String user, String file, int offset) {
        return Formatter.listToJson(authService.getAuth(user, file, offset));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateAuth(@PathVariable("id") long id, String auth) {
        return ControllerUtils.getResponse(authService.updateAuth(id, auth));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/batch/{ids}", method = RequestMethod.DELETE)
    public String batchDelete(@PathVariable("ids") String ids) {
        return ControllerUtils.getResponse(authService.batchDelete(ids));
    }
}
