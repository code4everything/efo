package org.code4everything.efo.stand.web.file.impl;

import org.code4everything.efo.base.model.vo.FileInfoVO;
import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.web.file.BaseFileService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
    public FileInfoVO upload(MultipartFile file, Map<String, Object> params) {
        return null;
    }
}
