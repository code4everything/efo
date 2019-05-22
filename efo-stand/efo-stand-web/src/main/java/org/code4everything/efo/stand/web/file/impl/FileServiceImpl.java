package org.code4everything.efo.stand.web.file.impl;

import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.web.file.BaseFileService;
import org.code4everything.efo.stand.web.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
