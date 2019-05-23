package org.code4everything.efo.stand.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.efo.base.constant.EfoError;
import org.code4everything.efo.base.model.vo.file.FileInfoVO;
import org.code4everything.efo.base.model.vo.file.FileUploadVO;
import org.code4everything.efo.stand.web.file.FileService;
import org.code4everything.efo.stand.web.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pantao
 * @since 2019/5/22
 **/
@RestController
@RequestMapping("/")
@Api(tags = "文件接口")
public class FileController extends BaseController {

    private final FileService fileService;

    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Response<FileInfoVO> upload(@RequestBody MultipartFile file, @RequestBody @ApiParam FileUploadVO uploadVO) {
        uploadVO.setUserId(getUser(userService).getId());
        EfoError uploadError = EfoError.UPLOAD_ERROR;
        return parseResult(uploadError.getMsg(), uploadError.getCode(), fileService.upload(file, uploadVO));
    }
}
