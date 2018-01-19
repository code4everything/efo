package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.dao.sqlprovider.AuthSqlProvider;
import com.zhazhapan.efo.entity.Auth;
import com.zhazhapan.efo.model.AuthRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @date 2018/1/19
 */
@Repository
public interface AuthDAO {

    /**
     * 添加一条权限记录
     *
     * @param auth {@link Auth}
     */
    @Insert("insert into auth(user_id,file_id,is_downloadable,is_uploadable,is_deletable,is_updatable,is_visible) values(#{userId},#{fileId},#{isDownloadable},#{isUploadable},#{isDeletable},#{isUpdatable},#{isVisible})")
    void insertAuth(Auth auth);

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
     */
    @Delete("delete from auth where file_id=#{fileId}")
    void removeAuthByFileId(int fileId);

    /**
     * 更新权限记录
     *
     * @param id 编号
     * @param isDownloadable 下载权限
     * @param isUploadable 上传权限
     * @param isVisible 可查权限
     * @param isDeletable 删除权限
     * @param isUpdatable 更新权限
     */
    @UpdateProvider(type = AuthSqlProvider.class, method = "updateAuthById")
    void updateAuthById(@Param("id") int id, @Param("isDownloadable") int isDownloadable, @Param("isUploadable") int isUploadable, @Param("isVisible") int isVisible, @Param("isDeletable") int isDeletable, @Param("isUpdatable") int isUpdatable);

    /**
     * 获取权限记录
     *
     * @param id 编号，值小于等于0时不作为条件
     * @param userId 用户编号，值小于等于0时不作为条件
     * @param fileId 文件编号，值小于等于0时不作为条件
     * @param offset 偏移
     *
     * @return {@link List}
     */
    @SelectProvider(type = AuthSqlProvider.class, method = "getAuthBy")
    List<AuthRecord> getAuthBy(@Param("id") int id, @Param("userId") int userId, @Param("fileId") int fileId, @Param("offset") int offset);
}
