package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.service.IFileService;
import com.zhazhapan.efo.util.BeanUtils;
import com.zhazhapan.efo.util.ControllerUtils;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author pantao
 * @since 2018/1/29
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private final IFileService fileService;

    private final HttpServletRequest request;

    private final JSONObject jsonObject;

    @Autowired
    public FileController(IFileService fileService, HttpServletRequest request, JSONObject jsonObject) {
        this.fileService = fileService;
        this.request = request;
        this.jsonObject = jsonObject;
    }

    @AuthInterceptor(InterceptorLevel.USER)
    @RequestMapping(value = "/user/downloaded", method = RequestMethod.GET)
    public String getUserDownloaded(int offset, String search) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        return Formatter.listToJson(fileService.getUserDownloaded(user.getId(), offset, search));
    }

    @AuthInterceptor(InterceptorLevel.USER)
    @RequestMapping(value = "/user/uploaded", method = RequestMethod.GET)
    public String getUserUploaded(int offset, String search) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        return Formatter.listToJson(fileService.getUserUploaded(user.getId(), offset, search));
    }

    @AuthInterceptor(InterceptorLevel.USER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String upload(int categoryId, String tag, String description, String prefix, @RequestParam("file")
            MultipartFile multipartFile) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        return ControllerUtils.getResponse(fileService.upload(categoryId, tag, description, prefix, multipartFile,
                user));
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll(int offset, int categoryId, String orderBy, String search) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        boolean canGet = EfoApplication.settings.getBooleanUseEval(ConfigConsts.ANONYMOUS_VISIBLE_OF_SETTING) ||
                (Checker.isNotNull(user) && user.getIsVisible() == 1);
        if (canGet) {
            return Formatter.listToJson(fileService.getAll(offset, categoryId, orderBy, search));
        } else {
            jsonObject.put("error", "权限被限制，无法获取资源，请联系管理员");
            return jsonObject.toString();
        }
    }

    @AuthInterceptor(InterceptorLevel.USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String removeFile(@PathVariable("id") long id) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        jsonObject.put("status", "error");
        if (Checker.isNull(user)) {
            jsonObject.put("message", "请先登录");
        } else if (id < 1) {
            jsonObject.put("message", "格式不合法");
        } else if (fileService.removeFile(user, id)) {
            jsonObject.put("status", "success");
        } else {
            jsonObject.put("message", "删除失败，权限不够，请联系管理员");
        }
        return jsonObject.toString();
    }

    @AuthInterceptor(InterceptorLevel.USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateFileInfo(@PathVariable("id") long id, String name, String category, String tag, String
            description) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        jsonObject.put("status", "error");
        if (fileService.updateFileInfo(id, user, name, category, tag, description)) {
            jsonObject.put("status", "success");
        } else {
            jsonObject.put("message", "格式不正确或权限不够，更新失败，请联系管理员");
        }
        return jsonObject.toString();
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/basic/all", method = RequestMethod.GET)
    public String getBasicAll(String user, String file, String category, int offset) {
        return Formatter.listToJson(fileService.getBasicAll(user, file, category, offset));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/server", method = RequestMethod.GET)
    public String getServerFilesByPath(String path) {
        File[] files = FileExecutor.listFile(Checker.isEmpty(path) ? (Checker.isWindows() ? "C:\\" : "/") : path);
        JSONArray array = new JSONArray();
        if (Checker.isNotNull(files)) {
            for (File file : files) {
                array.add(BeanUtils.beanToJson(file));
            }
        }
        return array.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/server/share", method = RequestMethod.POST)
    public String shareFile(String prefix, String files) {
        User user = (User) request.getSession().getAttribute(ValueConsts.USER_STRING);
        return ControllerUtils.getResponse(fileService.shareFiles(Checker.checkNull(prefix), files, user));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{id}/url", method = RequestMethod.PUT)
    public String uploadFileUrl(@PathVariable("id") int id, String oldLocalUrl, String localUrl, String visitUrl) {
        boolean[] b = fileService.updateUrl(id, oldLocalUrl, localUrl, visitUrl);
        String responseJson = "{status:{localUrl:" + b[0] + ",visitUrl:" + b[1] + "}}";
        return Formatter.formatJson(responseJson);
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/batch/{ids}", method = RequestMethod.DELETE)
    public String deleteFiles(@PathVariable("ids") String ids) {
        return ControllerUtils.getResponse(fileService.deleteFiles(ids));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{id}/auth", method = RequestMethod.GET)
    public String getAuth(@PathVariable("id") long id) {
        return BeanUtils.toPrettyJson(fileService.getAuth(id));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{id}/auth", method = RequestMethod.PUT)
    public String updateAuth(@PathVariable("id") long id, String auth) {
        return ControllerUtils.getResponse(fileService.updateAuth(id, auth));
    }

    /**
     * 资源下载
     *
     * @param response {@link HttpServletResponse}
     */
    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public void getResource(HttpServletResponse response) throws IOException {
        ControllerUtils.loadResource(response, fileService.getResource(request.getServletPath(), request),
                ValueConsts.FALSE);
    }
}
