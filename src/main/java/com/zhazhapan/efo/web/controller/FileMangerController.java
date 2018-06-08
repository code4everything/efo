package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.service.IFileManagerService;
import com.zhazhapan.efo.util.ControllerUtils;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <a href="https://github.com/joni2back/angular-filemanager/blob/master/API.md">see api doc</a>
 *
 * @author pantao
 * @since 2018/1/29
 */
@ApiIgnore
@RestController
@RequestMapping("/filemanager")
@AuthInterceptor(InterceptorLevel.SYSTEM)
public class FileMangerController {

    private final IFileManagerService fileManagerService;

    private final JSONObject jsonObject;

    @Autowired
    public FileMangerController(IFileManagerService fileManagerService, JSONObject jsonObject) {
        this.fileManagerService = fileManagerService;
        this.jsonObject = jsonObject;
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/multidownload", method = RequestMethod.GET)
    public void multiDownload(HttpServletResponse response, String[] items, String toFilename) throws IOException {
        ControllerUtils.setResponseFileName(response, toFilename);
        fileManagerService.multiDownload(response, items, toFilename);
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, String path) throws IOException {
        ControllerUtils.loadResource(response, path, ValueConsts.TRUE);
    }

    /**
     * 暂时没有找到更好的解决方案
     *
     * @param destination 目的
     *
     * @return 响应结果
     */
    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(String destination, MultipartHttpServletRequest request) {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        MultipartFile[] files = ArrayUtils.mapToArray(fileMap, MultipartFile.class);
        jsonObject.put("result", fileManagerService.upload(destination, files));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/extract", method = RequestMethod.POST)
    public String extract(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.extract(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/compress", method = RequestMethod.POST)
    public String compress(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.compress(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public String setPermission(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.setPermission(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/folder", method = RequestMethod.POST)
    public String createFolder(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.createFolder(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/content", method = RequestMethod.POST)
    public String getContent(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.getContent(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.edit(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.remove(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    public String copy(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.copy(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    public String move(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.move(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public String rename(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.rename(json));
        return jsonObject.toJSONString();
    }

    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@RequestBody JSONObject json) {
        jsonObject.put("result", fileManagerService.list(json));
        return jsonObject.toJSONString();
    }
}
