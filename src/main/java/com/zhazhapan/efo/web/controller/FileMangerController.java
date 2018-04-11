package com.zhazhapan.efo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.service.IFileManagerService;
import com.zhazhapan.efo.util.ControllerUtils;
import com.zhazhapan.modules.constant.ValueConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pantao
 * @since 2018/1/29
 */
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
    public void upload(HttpServletResponse response, String path) throws IOException {
        ControllerUtils.loadResource(response, path, ValueConsts.TRUE);
    }

    /**
     * 暂时没有找到更好的解决方案
     *
     * @param destination 目的
     * @param file0 文件1
     * @param file1 文件2
     * @param file2 文件3
     * @param file3 文件4
     * @param file4 文件5
     * @param file5 文件6
     * @param file6 文件7
     * @param file7 文件8
     * @param file8 文件9
     * @param file9 文件10
     * @param file10 文件11
     *
     * @return 响应结果
     */
    @AuthInterceptor(InterceptorLevel.SYSTEM)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(String destination, @RequestParam("file-0") MultipartFile file0, @Nullable @RequestParam
            ("file-1") MultipartFile file1, @Nullable @RequestParam("file-2") MultipartFile file2, @Nullable
    @RequestParam("file-3") MultipartFile file3, @Nullable @RequestParam("file-4") MultipartFile file4, @Nullable
    @RequestParam("file-5") MultipartFile file5, @Nullable @RequestParam("file-6") MultipartFile file6, @Nullable
    @RequestParam("file-7") MultipartFile file7, @Nullable @RequestParam("file-8") MultipartFile file8, @Nullable
    @RequestParam("file-9") MultipartFile file9, @Nullable @RequestParam("file-10") MultipartFile file10) {
        jsonObject.put("result", fileManagerService.upload(destination, file0, file1, file2, file3, file4, file5,
                file6, file7, file8, file9, file10));
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
