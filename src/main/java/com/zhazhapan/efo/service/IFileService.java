package com.zhazhapan.efo.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @date 2018/1/29
 */
public interface IFileService {

    /**
     * 文件上传
     *
     * @param request {@link HttpServletRequest}
     *
     * @return 是否上传成功
     */
    boolean upload(HttpServletRequest request);

    /**
     * 下载文件
     */
    void download();

    /**
     * 上传文件
     *
     * @param categoryId 分类ID
     * @param tag 标签
     * @param description 描述
     * @param multipartFile 文件
     *
     * @return 是否上传成功
     */
    boolean upload(int categoryId, String tag, String description, MultipartFile multipartFile);
}
