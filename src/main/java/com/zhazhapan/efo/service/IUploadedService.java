package com.zhazhapan.efo.service;

import com.zhazhapan.efo.model.UploadedRecord;

import java.util.List;

/**
 * @author pantao
 * @since 2018/2/28
 */
public interface IUploadedService {

    /**
     * 获取所有上传记录
     *
     * @param user 用户名或邮箱
     * @param category 分类名称
     * @param file 文件名
     * @param offset 偏移
     *
     * @return {@link List}
     */
    List<UploadedRecord> getAll(String user, String file, String category, int offset);
}
