package org.code4everything.efo.stand.web.file.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.code4everything.boot.base.DateUtils;
import org.code4everything.boot.service.BootFileService;
import org.code4everything.boot.web.http.DustFile;
import org.code4everything.boot.web.http.HttpUtils;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.boot.web.mvc.exception.ExceptionFactory;
import org.code4everything.efo.base.constant.EfoError;
import org.code4everything.efo.base.model.vo.file.FileUploadVO;
import org.code4everything.efo.stand.dao.domain.FileDO;
import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.web.file.BaseFileService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author pantao
 * @since 2019-04-17
 */
class LocalFileServiceImpl implements BootFileService<FileDO>, BaseFileService {

    /**
     * 存储模式
     */
    private static final Character MODE = '1';

    private final FileRepository fileRepository;

    private final ThreadLocal<FileUploadVO> local = new ThreadLocal<>();

    LocalFileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileDO upload(String storagePath, MultipartFile file, FileUploadVO uploadVO) {
        local.set(uploadVO);
        Response<FileDO> response = HttpUtils.upload(this, file, storagePath, false, true);
        if (response.isOk()) {
            return response.getData();
        }
        throw ExceptionFactory.exception(EfoError.UPLOAD_ERROR);
    }

    @Override
    public FileDO getBy(DustFile dustFile) {
        // 拼接映射路径
        StringJoiner urlJoiner = new StringJoiner("/", "/", "/");
        urlJoiner.add(local.get().getUsername());
        urlJoiner.add(DateUtil.format(DateUtils.getEndOfToday(), "yyyy/MM/dd"));
        String accessUrl = urlJoiner.toString() + DigestUtil.md5Hex(dustFile.getFilename());
        dustFile.addParam("url", accessUrl);
        return fileRepository.getByAccessUrl(accessUrl);
    }

    @Override
    public FileDO save(DustFile dustFile, @Nullable FileDO file) {
        FileUploadVO uploadVO = local.get();
        if (Objects.isNull(file)) {
            // 没有发生文件覆盖
            file = new FileDO();
            file.setCreateTime(LocalDateTime.now());
            file.setUserId(uploadVO.getUserId());
            // default status
            file.setStatus('3');
        } else {
            // 发生了文件覆盖
            file.setSize(dustFile.getSize());
        }
        // 设置文件信息
        file.setSize(dustFile.getSize());
        file.setAccessUrl((String) dustFile.getParam("url"));
        file.setLocalPath(dustFile.getStoragePath() + dustFile.getFilename());

        // 设置文件附加信息
        file.setCategoryId(uploadVO.getCategoryId());
        file.setDescription(uploadVO.getDescription());
        file.setTag(uploadVO.getTags());
        local.remove();

        file.setMode(MODE);
        return fileRepository.save(file);
    }

    @Override
    public String getLocalPathByAccessUrl(String accessUrl) {
        return null;
    }
}
