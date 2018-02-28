package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.dao.sqlprovider.UploadedSqlProvider;
import com.zhazhapan.efo.model.DownloadRecord;
import com.zhazhapan.efo.model.UploadedRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @since 2018/2/28
 */
@Repository
public interface UploadedDAO {

    /**
     * 查询上传记录
     *
     * @param userId 用户编号，不使用用户编号作为条件时设置值小于等于0即可
     * @param fileId 文件编号，不使用文件编号作为条件时设置值小于等于0即可
     * @param categoryId 分类编号，不用分类编号作为条件时设置值小于等于0即可
     * @param fileName 文件名，不使用文件名作为条件时设置值为空即可
     * @param offset 偏移
     *
     * @return 上传记录
     */
    @SelectProvider(type = UploadedSqlProvider.class, method = "getDownloadBy")
    List<UploadedRecord> getUploadedBy(@Param("userId") int userId, @Param("fileId") long fileId, @Param("fileName")
            String fileName, @Param("categoryId") int categoryId, @Param("offset") int offset);
}
