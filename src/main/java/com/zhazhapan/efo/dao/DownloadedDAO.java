package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.dao.sqlprovider.DownloadedSqlProvider;
import com.zhazhapan.efo.model.DownloadRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2018/1/18
 */
@Repository
public interface DownloadedDAO {

    /**
     * 新增一条下载记录
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     */
    @Insert("insert into download(user_id,file_id) values(#{userId},#{fileId})")
    void insertDownload(@Param("userId") int userId, @Param("fileId") long fileId);

    /**
     * 查询下载记录
     *
     * @param userId 用户编号，不使用用户编号作为条件时设置值小于等于0即可
     * @param fileId 文件编号，不使用文件编号作为条件时设置值小于等于0即可
     * @param categoryId 分类编号，不用分类编号作为条件时设置值小于等于0即可
     * @param fileName 文件名，不使用文件名作为条件时设置值为空即可
     * @param offset 偏移
     *
     * @return 下载记录
     */
    @SelectProvider(type = DownloadedSqlProvider.class, method = "getDownloadBy")
    List<DownloadRecord> getDownloadedBy(@Param("userId") int userId, @Param("fileId") long fileId, @Param
            ("fileName") String fileName, @Param("categoryId") int categoryId, @Param("offset") int offset);

    /**
     * 删除文件
     *
     * @param fileId 文件编号
     *
     * @return 是否删除成功
     */
    @Delete("delete from download where file_id=#{fileId}")
    boolean removeByFileId(long fileId);
}
