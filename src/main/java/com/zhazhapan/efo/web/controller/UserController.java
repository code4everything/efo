package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.service.impl.UserServiceImpl;
import com.zhazhapan.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @date 2018/1/22
 */
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password) {
        User user = userService.login(username, password);
        JSONObject object = new JSONObject();
        if (Checker.isNull(user)) {
            object.put("status", "failed");
        } else {
            request.getSession().setAttribute("user", user);
            object.put("status", "success");
        }
        return object.toString();
    }
}
