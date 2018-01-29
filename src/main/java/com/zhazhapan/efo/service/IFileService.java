package com.zhazhapan.efo.service;

/**
 * @author pantao
 * @date 2018/1/29
 */
public interface IFileService {

    /**
     * 文件上传
     *
     * @return {@link Boolean}
     */
    boolean upload();

    /**
     * 下载文件
     */
    void download();
}
