package org.code4everything.efo.stand.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.efo.base.model.vo.file.FileInfoVO;
import org.code4everything.efo.base.model.vo.file.FileUploadVO;
import org.code4everything.efo.stand.web.file.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.InputStream;

/**
 * @author pantao
 * @since 2019/5/22
 **/
@RestController
@RequestMapping("/")
@Api(tags = "文件接口", hidden = true)
public class FileController extends BaseController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Response<FileInfoVO> upload(@RequestBody MultipartFile file, @RequestBody @ApiParam FileUploadVO uploadVO) {
        return successResult(fileService.upload(file, uploadVO));
    }

    @ApiIgnore
    @GetMapping("/**")
    @ApiOperation("文件资源路径")
    public ResponseEntity<InputStream> response() {
        return null;
    }
}
