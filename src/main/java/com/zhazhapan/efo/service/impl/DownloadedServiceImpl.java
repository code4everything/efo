package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.dao.DownloadedDAO;
import com.zhazhapan.efo.model.DownloadRecord;
import com.zhazhapan.efo.service.IDownloadedService;
import com.zhazhapan.efo.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pantao
 * @since 2018/2/1
 */
@Service
public class DownloadedServiceImpl implements IDownloadedService {

    private final DownloadedDAO downloadDAO;

    @Autowired
    public DownloadedServiceImpl(DownloadedDAO downloadDAO) {
        this.downloadDAO = downloadDAO;
    }

    @Override
    public void insertDownload(int userId, long fileId) {
        downloadDAO.insertDownload(userId, fileId);
    }

    @Override
    public void removeByFileId(long fileId) {
        downloadDAO.removeByFileId(fileId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DownloadRecord> getAll(String user, String file, String category, int offset) {
        return (List<DownloadRecord>) ServiceUtils.invokeFileFilter(downloadDAO, "getDownloadedBy", user, file,
                category, offset);
    }
}
