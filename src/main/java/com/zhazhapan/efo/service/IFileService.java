package com.zhazhapan.efo.service;

import com.zhazhapan.efo.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pantao
 * @date 2018/1/29
 */
public interface IFileService {

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
     * @param user {@link User}
     *
     * @return 是否上传成功
     */
    boolean upload(int categoryId, String tag, String description, MultipartFile multipartFile, User user);

    /**
     * 本地路径是否存在
     *
     * @param localUrl 本地路径
     *
     * @return {@link Boolean}
     */
    boolean localUrlExists(String localUrl);

    /**
     * 访问路径是否存在
     *
     * @param visitUrl 访问路径
     *
     * @return {@link Boolean}
     */
    boolean visitUrlExists(String visitUrl);
}
