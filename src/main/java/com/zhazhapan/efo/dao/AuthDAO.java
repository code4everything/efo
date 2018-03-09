package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.dao.sqlprovider.AuthSqlProvider;
import com.zhazhapan.efo.entity.Auth;
import com.zhazhapan.efo.model.AuthRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2018/1/19
 */
@Repository
public interface AuthDAO {

    /**
     * 检测某个权限是否存在
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     *
     * @return {@link Auth}
     */
    @Select("select * from auth where user_id=#{userId} and file_id=#{fileId}")
    Auth exists(@Param("userId") int userId, @Param("fileId") long fileId);

    /**
     * 批量删除权限记录
     *
     * @param ids 权限编号集
     *
     * @return 是否删除成功
     */
    @DeleteProvider(type = AuthSqlProvider.class, method = "batchDelete")
    boolean batchDelete(@Param("ids") String ids);

    /**
     * 添加一条权限记录
     *
     * @param auth {@link Auth}
     *
     * @return 是否添加成功
     */
    @Insert("insert into auth(user_id,file_id,is_downloadable,is_uploadable,is_deletable,is_updatable,is_visible) " +
            "values(#{userId},#{fileId},#{isDownloadable},#{isUploadable},#{isDeletable},#{isUpdatable},#{isVisible})")
    boolean insertAuth(Auth auth);

    /**
     * 删除一条权限记录
     *
     * @param id 编号
     */
    @Delete("delete from auth where id=#{id}")
    void removeAuthById(int id);

    /**
     * 删除一条权限记录
     *
     * @param userId 编号
     */
    @Delete("delete from auth where user_id=#{userId}")
    void removeAuthByUserId(int userId);

    /**
     * 删除一条权限记录
     *
     * @param fileId 编号
     *
     * @return 是否删除成功
     */
    @Delete("delete from auth where file_id=#{fileId}")
    boolean removeAuthByFileId(long fileId);

    /**
     * 更新权限记录
     *
     * @param id 编号
     * @param isDownloadable 下载权限
     * @param isUploadable 上传权限
     * @param isVisible 可查权限
     * @param isDeletable 删除权限
     * @param isUpdatable 更新权限
     *
     * @return 是否更新成功
     */
    @UpdateProvider(type = AuthSqlProvider.class, method = "updateAuthById")
    boolean updateAuthById(@Param("id") long id, @Param("isDownloadable") int isDownloadable, @Param("isUploadable")
            int isUploadable, @Param("isDeletable") int isDeletable, @Param("isUpdatable") int isUpdatable, @Param
            ("isVisible") int isVisible);

    /**
     * 获取权限记录
     *
     * @param id 编号，值小于等于0时不作为条件
     * @param userId 用户编号，值小于等于0时不作为条件
     * @param fileId 文件编号，值小于等于0时不作为条件
     * @param fileName 模糊搜索文件名（当参数不为空时）
     * @param offset 偏移
     *
     * @return {@link List}
     */
    @SelectProvider(type = AuthSqlProvider.class, method = "getAuthBy")
    List<AuthRecord> getAuthBy(@Param("id") long id, @Param("userId") int userId, @Param("fileId") long fileId,
                               @Param("fileName") String fileName, @Param("offset") int offset);
}
