package com.zhazhapan.efo.service.impl;

import com.zhazhapan.efo.dao.DownloadDAO;
import com.zhazhapan.efo.model.DownloadRecord;
import com.zhazhapan.efo.service.ICategoryService;
import com.zhazhapan.efo.service.IDownloadService;
import com.zhazhapan.efo.service.IFileService;
import com.zhazhapan.efo.service.IUserService;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pantao
 * @date 2018/2/1
 */
@Service
public class DownloadServiceImpl implements IDownloadService {

    private final DownloadDAO downloadDAO;

    private final IUserService userService;

    private final IFileService fileService;

    private final ICategoryService categoryService;

    @Autowired
    public DownloadServiceImpl(DownloadDAO downloadDAO, IUserService userService, IFileService fileService,
                               ICategoryService categoryService) {
        this.downloadDAO = downloadDAO;
        this.userService = userService;
        this.fileService = fileService;
        this.categoryService = categoryService;
    }

    @Override
    public void insertDownload(int userId, long fileId) {
        downloadDAO.insertDownload(userId, fileId);
    }

    @Override
    public void removeByFileId(long fileId) {
        downloadDAO.removeByFileId(fileId);
    }

    @Override
    public List<DownloadRecord> getAll(String user, String file, String category, int offset) {
        int userId = Checker.isEmpty(user) ? ValueConsts.ZERO_INT : userService.getUserId(user);
        long fileId = Checker.isEmpty(file) ? ValueConsts.ZERO_INT : fileService.getFileId(file);
        int categoryId = Checker.isEmpty(category) ? ValueConsts.ZERO_INT : categoryService.getIdByName(category);
        return downloadDAO.getDownloadBy(userId, fileId, file, categoryId, offset);
    }
}
