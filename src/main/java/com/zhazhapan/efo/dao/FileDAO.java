package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.dao.sqlprovider.FileSqlProvider;
import com.zhazhapan.efo.entity.File;
import com.zhazhapan.efo.model.BaseAuthRecord;
import com.zhazhapan.efo.model.FileBasicRecord;
import com.zhazhapan.efo.model.FileRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2018/1/19
 */
@Repository
public interface FileDAO {

    /**
     * 获取文件权限
     *
     * @param id 文件编号
     *
     * @return {@link BaseAuthRecord}
     */
    @Select("select is_downloadable,is_uploadable,is_deletable,is_updatable,is_visible from file where id=#{id}")
    BaseAuthRecord getAuth(long id);

    /**
     * 通过编号获取文件
     *
     * @param id 编号
     *
     * @return {@link File}
     */
    @Select("select * from file where id=#{id}")
    File getById(long id);

    /**
     * 通过ID获取本地路径
     *
     * @param fileId 文件编号
     *
     * @return {@link String}
     */
    @Select("select local_url from file where id=#{id}")
    String getLocalUrlById(long fileId);

    /**
     * 通过编号删除文件
     *
     * @param id 编号
     *
     * @return 是否删除成功
     */
    @Delete("delete from file where id=#{id}")
    boolean removeById(long id);

    /**
     * 通过本地路径获取文件编号
     *
     * @param visitUrl 本地路径
     *
     * @return 编号
     */
    @Select("select id from file where visit_url=#{visitUrl}")
    long getIdByVisitUrl(String visitUrl);

    /**
     * 通过本地路径获取文件编号
     *
     * @param localUrl 本地路径
     *
     * @return 编号
     */
    @Select("select id from file where local_url=#{localUrl}")
    long getIdByLocalUrl(String localUrl);

    /**
     * 通过访问路径获取本地文件路径
     *
     * @param visitUrl 访问路径
     *
     * @return {@link String}
     */
    @Select("select local_url from file where visit_url=#{visitUrl}")
    String getLocalUrlByVisitUrl(String visitUrl);

    /**
     * 通过访问路径删除
     *
     * @param visitUrl 访问路径
     *
     * @return 是否删除成功
     */
    @Delete("delete from file where visit_url=#{visitUrl}")
    boolean removeByVisitUrl(String visitUrl);

    /**
     * 通过本地路径删除
     *
     * @param localUrl 本地路径
     *
     * @return 是否删除成功
     */
    @Delete("delete from file where local_url=#{localUrl}")
    boolean removeByLocalUrl(String localUrl);

    /**
     * 检查本地路径
     *
     * @param localUrl 本地路径
     *
     * @return {@link Integer}
     */
    @Select("select count(*) from file where local_url=#{localUrl}")
    int checkLocalUrl(String localUrl);

    /**
     * 检查访问路径
     *
     * @param visitUrl 访问路径
     *
     * @return {@link Integer}
     */
    @Select("select count(*) from file where visit_url=#{visitUrl}")
    int checkVisitUrl(String visitUrl);

    /**
     * 添加一个文件
     *
     * @param file {@link File}
     *
     * @return 是否添加成功
     */
    @Insert("insert into file(name,suffix,local_url,visit_url,size,description,tag,user_id,category_id," +
            "is_downloadable,is_uploadable,is_deletable,is_updatable,is_visible) values(#{name},#{suffix}," +
            "#{localUrl},#{visitUrl},#{size},#{description},#{tag},#{userId},#{categoryId},#{isDownloadable}," +
            "#{isUploadable},#{isDeletable},#{isUpdatable},#{isVisible})")
    boolean insertFile(File file);

    /**
     * 删除一个文件
     *
     * @param id 文件编号
     */
    @Delete("delete from file where id=#{id}")
    void deleteFileById(int id);

    /**
     * 删除文件
     *
     * @param userId 用户编号
     */
    @Delete("delete from file where user_id=#{userId}")
    void deleteFileByUserId(int userId);

    /**
     * 删除文件
     *
     * @param categoryId 分类编号
     */
    @Delete("delete from file where category_d=#{categoryId}")
    void deleteFileByCategoryId(int categoryId);

    /**
     * 更新文件基本信息
     *
     * @param file 文件
     *
     * @return 是否更新成功
     */
    @Update("update file set name=#{name},suffix=#{suffix},local_url=#{localUrl},visit_url=#{visitUrl}," +
            "description=#{description},tag=#{tag},category_id=#{categoryId},last_modify_time=current_timestamp " +
            "where" + " id=#{id}")
    boolean updateFileInfo(File file);

    /**
     * 更新文件权限
     *
     * @param id 编号
     * @param isDownloadable 下载权限
     * @param isUploadable 上传权限
     * @param isVisible 可查权限
     * @param isDeletable 删除权限
     * @param isUpdatable 上传权限
     *
     * @return 是否更新成功
     */
    @UpdateProvider(type = FileSqlProvider.class, method = "updateAuthById")
    boolean updateAuthById(@Param("id") long id, @Param("isDownloadable") int isDownloadable, @Param("isUploadable")
            int isUploadable, @Param("isDeletable") int isDeletable, @Param("isUpdatable") int isUpdatable, @Param
            ("isVisible") int isVisible);

    /**
     * 更新文件名
     *
     * @param id 编号
     * @param name 文件名
     * @param suffix 后缀名
     */
    @Update("update file set name=#{name},suffix=#{suffix},last_modify_time=current_timestamp where id=#{id}")
    void updateFileNameById(@Param("id") int id, @Param("name") String name, @Param("suffix") String suffix);

    /**
     * 更新文件修改时间
     *
     * @param id 编号
     */
    @Update("update file set last_modify_time=current_timestamp where id=#{id}")
    void updateLastModifyTimeById(int id);

    /**
     * 更新文件本地路径
     *
     * @param id 编号
     * @param localUrl 本地路径
     */
    @Update("update file set local_url=#{localUrl} where id=#{id}")
    void updateLocalUrlById(@Param("id") int id, @Param("localUrl") String localUrl);

    /**
     * 更新文件访问路径
     *
     * @param id 编号
     * @param visitUrl 访问链接
     */
    @Update("update file set visit_url=#{visitUrl} where id=#{id}")
    void updateVisitUrlById(@Param("id") int id, @Param("visitUrl") String visitUrl);

    /**
     * 更新文件描述
     *
     * @param id 文件编号
     * @param description 描述
     */
    @Update("update file set description=#{description} where id=#{id}")
    void updateDescriptionById(@Param("id") int id, @Param("description") String description);

    /**
     * 更新文件查看次数
     *
     * @param id 编号
     */
    @Update("update file set check_times=check_times+1 where id=#{id}")
    void updateCheckTimesById(int id);

    /**
     * 更新文件下载次数
     *
     * @param id 编号
     */
    @Update("update file set download_times=download_times+1 where id=#{id}")
    void updateDownloadTimesById(long id);

    /**
     * 更新文件标签
     *
     * @param id 编号
     * @param tag 标签
     */
    @Update("update file set tag=#{tag} where id=#{id}")
    void updateTagById(@Param("id") int id, @Param("tag") String tag);

    /**
     * 更新文件分类
     *
     * @param id 编号
     * @param categoryId 分类编号
     */
    @Update("update file set category_id=#{categoryId} where id=#{id}")
    void updateCategoryById(@Param("id") int id, @Param("categoryId") int categoryId);

    /**
     * 获取文件信息
     *
     * @param visitUrl 访问链接
     *
     * @return {@link File}
     */
    @Select("select * from file where visit_url=#{visitUrl}")
    File getFileByVisitUrl(String visitUrl);

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
    @SelectProvider(type = FileSqlProvider.class, method = "getAll")
    List<FileRecord> getAll(@Param("offset") int offset, @Param("categoryId") int categoryId, @Param("orderBy")
            String orderBy, @Param("search") String search);

    /**
     * 获取用户的上传资源
     *
     * @param userId 用户编号
     * @param offset 偏移
     * @param search 搜索
     *
     * @return {@link List}
     */
    @SelectProvider(type = FileSqlProvider.class, method = "getUserUploaded")
    List<FileRecord> getUserUploaded(@Param("userId") int userId, @Param("offset") int offset, @Param("search")
            String search);

    /**
     * 获取用户的下载资源
     *
     * @param userId 用户编号
     * @param offset 偏移
     * @param search 搜索
     *
     * @return {@link List}
     */
    @SelectProvider(type = FileSqlProvider.class, method = "getUserDownloaded")
    List<FileRecord> getUserDownloaded(@Param("userId") int userId, @Param("offset") int offset, @Param("search")
            String search);

    /**
     * 查询文件基本信息
     *
     * @param userId 用户编号，不使用用户编号作为条件时设置值小于等于0即可
     * @param fileId 文件编号，不使用文件编号作为条件时设置值小于等于0即可
     * @param categoryId 分类编号，不用分类编号作为条件时设置值小于等于0即可
     * @param fileName 文件名，不使用文件名作为条件时设置值为空即可
     * @param offset 偏移
     *
     * @return 上传记录
     */
    @SelectProvider(type = FileSqlProvider.class, method = "getBasicBy")
    List<FileBasicRecord> getBasicBy(@Param("userId") int userId, @Param("fileId") long fileId, @Param("fileName")
            String fileName, @Param("categoryId") int categoryId, @Param("offset") int offset);
}
