package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.dao.sqlprovider.DownloadSqlProvider;
import com.zhazhapan.efo.model.DownloadRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @date 2018/1/18
 */
@Repository
public interface DownloadDAO {

    /**
     * 新增一条下载记录
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     */
    @Insert("insert into download(user_id,file_id) values(#{userId},#{fileId})")
    void insertDownload(int userId, long fileId);

    /**
     * 查询下载记录
     *
     * @param userId 用户编号，不用用户编号作为条件时设置值小于等于0即可
     * @param fileId 文件编号，不用文件编号作为条件时设置值小于等于0即可
     * @param offset 偏移
     *
     * @return 下载记录
     */
    @SelectProvider(type = DownloadSqlProvider.class, method = "getDownloadBy")
    List<DownloadRecord> getDownloadBy(@Param("userId") int userId, @Param("fileId") int fileId, @Param("offset") int
            offset);
}
