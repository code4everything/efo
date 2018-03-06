package com.zhazhapan.efo.service;

import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.model.BaseAuthRecord;
import com.zhazhapan.efo.model.FileBasicRecord;
import com.zhazhapan.efo.model.FileRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author pantao
 * @since 2018/1/29
 */
public interface IFileService {

    /**
     * 更新文件权限
     *
     * @param id 文件编号
     * @param auth 权限集
     *
     * @return 是否更新成功
     */
    boolean updateAuth(long id, String auth);

    /**
     * 获取文件权限
     *
     * @param id 文件编号
     *
     * @return {@link BaseAuthRecord}
     */
    BaseAuthRecord getAuth(long id);

    /**
     * 批量删除文件
     *
     * @param ids 所有文件编号
     *
     * @return 是否删除成功
     */
    boolean deleteFiles(String ids);

    /**
     * 更新文件路径
     *
     * @param id 文件编号
     * @param oldLocalUrl 原本地路径
     * @param localUrl 本地路径
     * @param visitUrl 访问路径
     *
     * @return 是否更新成功
     */
    boolean[] updateUrl(int id, String oldLocalUrl, String localUrl, String visitUrl);

    /**
     * 更新文件信息
     *
     * @param id 文件编号
     * @param user 用户对象
     * @param name 文件名
     * @param category 文件分类
     * @param tag 标签
     * @param description 文件描述
     *
     * @return 是否更新成功
     */
    boolean updateFileInfo(long id, User user, String name, String category, String tag, String description);

    /**
     * 删除文件，验证权限
     *
     * @param user 用户对象
     * @param fileId 文件编号
     *
     * @return {@link Boolean}
     */
    boolean removeFile(User user, long fileId);

    /**
     * 获取用户的下载资源
     *
     * @param userId 用户编号
     * @param offset 偏移
     * @param search 搜索
     *
     * @return {@link List}
     */
    List<FileRecord> getUserDownloaded(int userId, int offset, String search);

    /**
     * 获取用户的上传资源
     *
     * @param userId 用户编号
     * @param offset 偏移
     * @param search 搜索
     *
     * @return {@link List}
     */
    List<FileRecord> getUserUploaded(int userId, int offset, String search);

    /**
     * 通过编号删除，不验证权限
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
     * @param offset 偏移
     * @param categoryId 分类编号
     * @param orderBy 排序方式
     * @param search 搜索
     *
     * @return {@link List}
     */
    List<FileRecord> getAll(int offset, int categoryId, String orderBy, String search);

    /**
     * 上传文件
     *
     * @param categoryId 分类ID
     * @param tag 标签
     * @param description 描述
     * @param prefix 自定义前缀
     * @param multipartFile 文件
     * @param user {@link User}
     *
     * @return 是否上传成功
     */
    boolean upload(int categoryId, String tag, String description, String prefix, MultipartFile multipartFile, User
            user);

    /**
     * 分享服务器本地文件
     *
     * @param prefix 链接前缀
     * @param files 文件
     * @param user 用户对象
     *
     * @return 是否添加成功
     */
    boolean shareFiles(String prefix, String files, User user);

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

    /**
     * 通过本地路径获取文件编号
     *
     * @param localUrl 本地路径
     *
     * @return 文件编号
     */
    long getFileId(String localUrl);

    /**
     * 获取所有文件基本信息
     *
     * @param user 用户名或邮箱
     * @param category 分类名称
     * @param file 文件名
     * @param offset 偏移
     *
     * @return {@link List}
     */
    List<FileBasicRecord> getBasicAll(String user, String file, String category, int offset);
}
