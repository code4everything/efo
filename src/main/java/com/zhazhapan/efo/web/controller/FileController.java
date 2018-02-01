package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.impl.FileServiceImpl;
import com.zhazhapan.efo.util.ControllerUtils;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;

/**
 * @author pantao
 * @date 2018/1/29
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    JSONObject jsonObject;

    @AuthInterceptor
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(int categoryId, String tag, String description, @RequestParam("file") MultipartFile
            multipartFile) {
        User user = (User) request.getSession().getAttribute("user");
        return ControllerUtils.getResponse(fileService.upload(categoryId, tag, description, multipartFile, user));
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll() {
        User user = (User) request.getSession().getAttribute("user");
        boolean canGet = EfoApplication.settings.getBooleanUseEval(ConfigConsts.ANONYMOUS_VISIBLE_OF_SETTING) ||
                (Checker.isNotNull(user) && user.getIsVisible() == 1);
        if (canGet) {
            return Formatter.listToJson(fileService.getAll());
        } else {
            jsonObject.put("error", "you have no authorization to get resources");
            return jsonObject.toString();
        }
    }

    /**
     * 资源下载
     *
     * @param response {@link HttpServletResponse}
     */
    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public void getResource(HttpServletResponse response) {
        File file = new File(fileService.getResource(request.getServletPath(), request));
        try {
            OutputStream os = response.getOutputStream();
            os.write(FileExecutor.readFileToByteArray(file));
            os.flush();
        } catch (Exception e) {
            response.setStatus(404);
            logger.error(e.getMessage());
        }
    }
}
