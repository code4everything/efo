package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.service.IDownloadedService;
import com.zhazhapan.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018/2/9
 */
@RestController
@RequestMapping(value = "/downloaded")
public class DownloadedController {

    private final IDownloadedService downloadService;

    @Autowired
    public DownloadedController(IDownloadedService downloadService) {
        this.downloadService = downloadService;
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public String getAll(String user, String file, String category, int offset) {
        return Formatter.listToJson(downloadService.getAll(user, file, category, offset));
    }
}
