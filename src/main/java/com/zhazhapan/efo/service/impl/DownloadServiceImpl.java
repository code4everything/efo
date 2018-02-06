package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.dao.DownloadDAO;
import com.zhazhapan.efo.service.IDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @date 2018/2/1
 */
@Service
public class DownloadServiceImpl implements IDownloadService {

    private final DownloadDAO downloadDAO;

    @Autowired
    public DownloadServiceImpl(DownloadDAO downloadDAO) {this.downloadDAO = downloadDAO;}

    @Override
    public void insertDownload(int userId, long fileId) {
        downloadDAO.insertDownload(userId, fileId);
    }

    @Override
    public void removeByFileId(long fileId) {
        downloadDAO.removeByFileId(fileId);
    }
}
