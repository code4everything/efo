package org.code4everything.efo.stand.web.file.impl;

import org.code4everything.boot.service.BootFileService;
import org.code4everything.boot.web.http.DustFile;
import org.code4everything.boot.web.http.HttpUtils;
import org.code4everything.efo.base.model.vo.FileInfoVO;
import org.code4everything.efo.stand.dao.domain.FileDO;
import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.web.file.BaseFileService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @author pantao
 * @since 2019-04-17
 */
class LocalFileServiceImpl implements BootFileService<FileDO>, BaseFileService {

    private final FileRepository fileRepository;

    LocalFileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileInfoVO upload(MultipartFile file, Map<String, Object> params) {
        HttpUtils.upload(this, file, "", false, params, false);
        return null;
    }

    @Override
    public FileDO getBy(DustFile dustFile) {
        return null;
    }

    @Override
    public FileDO save(DustFile dustFile, @Nullable FileDO file) {
        if (Objects.isNull(file)) {
            // 没有发生文件覆盖
            file = new FileDO();
            file.setCreateTime(LocalDateTime.now());
        } else {
            // 发生了文件覆盖
            file.setSize(dustFile.getSize());
        }
        return fileRepository.save(file);
    }

    @Override
    public String getLocalPathByAccessUrl(String accessUrl) {
        return null;
    }
}
