package com.zhazhapan.efo.service;

/**
 * @author pantao
 * @date 2018/2/1
 */
public interface IDownloadService {

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
}
