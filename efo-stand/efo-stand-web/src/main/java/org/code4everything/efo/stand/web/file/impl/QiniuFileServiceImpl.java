package org.code4everything.efo.stand.web.file.impl;

import org.code4everything.efo.base.model.vo.file.FileUploadVO;
import org.code4everything.efo.stand.dao.domain.FileDO;
import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.web.file.BaseFileService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pantao
 * @since 2019/5/22
 **/
public class QiniuFileServiceImpl implements BaseFileService {

    private static final Character MODE = '2';

    private final FileRepository fileRepository;

    QiniuFileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileDO upload(String storagePath, MultipartFile file, FileUploadVO uploadVO) {
        return null;
    }
}
