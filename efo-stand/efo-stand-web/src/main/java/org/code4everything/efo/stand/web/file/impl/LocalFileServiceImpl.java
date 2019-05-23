package org.code4everything.efo.stand.web.file.impl;

import org.code4everything.boot.config.BootConfig;
import org.code4everything.boot.service.BootFileService;
import org.code4everything.boot.web.http.DustFile;
import org.code4everything.boot.web.http.HttpUtils;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.efo.base.model.vo.file.FileUploadVO;
import org.code4everything.efo.stand.dao.domain.FileDO;
import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.web.file.BaseFileService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Objects;

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
        } else if (response.getCode() == BootConfig.DEFAULT_ERROR_CODE) {
            // 文件大小超出限制
        }
        // 文件写入失败
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

        FileUploadVO uploadVO = local.get();
        file.setCategoryId(uploadVO.getCategoryId());
        file.setDescription(uploadVO.getDescription());
        file.setTags(uploadVO.getTags());
        local.remove();

        file.setMode(MODE);
        return fileRepository.save(file);
    }

    @Override
    public String getLocalPathByAccessUrl(String accessUrl) {
        return null;
    }
}
