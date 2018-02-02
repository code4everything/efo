package com.zhazhapan.efo.service;

import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.model.FileRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author pantao
 * @date 2018/1/29
 */
public interface IFileService {

    /**
     * 获取用户的下载资源
     *
     * @param userId 用户编号
     *
     * @return {@link List}
     */
    List<FileRecord> getUserDownloaded(int userId);

    /**
     * 获取用户的上传资源
     *
     * @param userId 用户编号
     *
     * @return {@link List}
     */
    List<FileRecord> getUserUploaded(int userId);

    /**
     * 通过编号删除
     *
     * @param id 编号
     *
     * @return 是否删除成功
     */
    boolean removeById(long id);

    /**
     * 通过访问路径删除
     *
     * @param visitUrl 访问路径
     *
     * @return 是否删除成功
     */
    boolean removeByVisitUrl(String visitUrl);

    /**
     * 通过本地路径删除
     *
     * @param localUrl 访问路径
     *
     * @return 是否删除成功
     */
    boolean removeByLocalUrl(String localUrl);

    /**
     * 获取资源
     *
     * @param visitUrl 访问路径
     * @param request {@link HttpServletRequest}
     *
     * @return {@link File}
     */
    String getResource(String visitUrl, HttpServletRequest request);

    /**
     * 通过访问路径获取本地文件路径
     *
     * @param visitUrl 访问路径
     *
     * @return {@link String}
     */
    String getLocalUrlByVisitUrl(String visitUrl);

    /**
     * 获取所有文件
     *
     * @return {@link List}
     */
    List<FileRecord> getAll();

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
