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

    @Autowired
    DownloadDAO downloadDAO;

    @Override
    public void insertDownload(int userId, long fileId) {
        downloadDAO.insertDownload(userId, fileId);
    }
}
