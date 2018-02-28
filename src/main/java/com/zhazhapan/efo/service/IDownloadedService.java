package com.zhazhapan.efo.service;

import com.zhazhapan.efo.model.DownloadRecord;

import java.util.List;

/**
 * @author pantao
 * @since 2018/2/1
 */
public interface IDownloadedService {

    /**
     * 添加下载记录
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     */
    void insertDownload(int userId, long fileId);

    /**
     * 通过文件编号删除下载记录
     *
     * @param fileId 文件编号
     */
    void removeByFileId(long fileId);

    /**
     * 获取所有下载记录
     *
     * @param user 用户名或邮箱
     * @param category 分类名称
     * @param file 文件名
     * @param offset 偏移
     *
     * @return {@link DownloadRecord}
     */
    List<DownloadRecord> getAll(String user, String file, String category, int offset);
}
