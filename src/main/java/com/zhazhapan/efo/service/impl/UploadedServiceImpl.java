package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.dao.UploadedDAO;
import com.zhazhapan.efo.model.DownloadRecord;
import com.zhazhapan.efo.model.UploadedRecord;
import com.zhazhapan.efo.service.IUploadedService;
import com.zhazhapan.efo.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pantao
 * @since 2018/2/28
 */
@Service
public class UploadedServiceImpl implements IUploadedService {

    private final UploadedDAO uploadedDAO;

    @Autowired
    public UploadedServiceImpl(UploadedDAO uploadedDAO) {this.uploadedDAO = uploadedDAO;}

    @Override
    public List<UploadedRecord> getAll(String user, String file, String category, int offset) {
        int userId = ServiceUtils.getUserId(user);
        long fileId = ServiceUtils.getFileId(file);
        int categoryId = ServiceUtils.getCategoryId(category);
        return uploadedDAO.getUploadedBy(userId, fileId, file, categoryId, offset);
    }
}
