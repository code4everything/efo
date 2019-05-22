package org.code4everything.efo.stand.web.controller;

import io.swagger.annotations.Api;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.efo.stand.file.service.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2019/5/22
 **/
@RestController
@RequestMapping("/")
@Api(tags = "文件接口")
public class FileController extends BaseController {

    private final FileService fileService;

    public FileController(FileService fileService) {this.fileService = fileService;}
}
