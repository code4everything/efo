package org.code4everything.efo.stand.web.file.impl;

import org.code4everything.efo.base.model.vo.FileInfoVO;
import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.web.file.BaseFileService;
import org.code4everything.efo.stand.web.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author pantao
 * @since 2019/5/22
 **/
@Service
public class FileServiceImpl implements FileService {

    private final BaseFileService[] services;

    @Autowired
    public FileServiceImpl(FileRepository repository) {
        services = new BaseFileService[]{new LocalFileServiceImpl(repository), new QiniuFileServiceImpl(repository)};
    }

    @Override
    public FileInfoVO upload(MultipartFile file, Map<String, Object> params) {
        return null;
    }
}
