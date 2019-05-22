package org.code4everything.efo.stand.file.service.impl;

import org.code4everything.boot.web.http.DustFile;
import org.code4everything.efo.stand.dao.domain.FileDO;
import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.file.service.FileService;

import javax.annotation.Nullable;

/**
 * @author pantao
 * @since 2019-04-17
 */
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileDO getBy(DustFile dustFile) {
        return null;
    }

    @Override
    public FileDO save(DustFile dustFile, @Nullable FileDO file) {
        return null;
    }

    @Override
    public String getLocalPathByAccessUrl(String accessUrl) {
        return null;
    }
}
