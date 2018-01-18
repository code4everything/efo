package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.model.DownloadRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @date 2018/1/18
 */
@Repository
public interface DownloadDAO {

    @Insert("insert into download(user_id,file_id) values(#{userId},#{fileId})")
    void insertDownload(int userId, int fileId);

    @Select("select d.id,d.user_id,d.file_id,u.username,u.email,f.name file_name,c.name category_name,f.visit_url from download d join user u on d.user_id=u.id join file f on d.file_id=f.id join category c on f.category_id=c.id order by d.id desc limit #{offset},#{size}")
    List<DownloadRecord> getAllDownload(int offset, int size);

    @Select("select d.id,d.user_id,d.file_id,u.username,u.email,f.name file_name,c.name category_name,f.visit_url from download d join user u on d.user_id=u.id join file f on d.file_id=f.id join category c on f.category_id=c.id where d.user_id=#{id} order by id desc limit #{offset},#{size}")
    List<DownloadRecord> getDownloadByUserId(int id);

    @Select("select d.id,d.user_id,d.file_id,u.username,u.email,f.name file_name,c.name category_name,f.visit_url from download d join user u on d.user_id=u.id join file f on d.file_id=f.id join category c on f.category_id=c.id where d.file_id=#{id} order by id desc limit #{offset},#{size}")
    List<DownloadRecord> getDownloadByFileId(int id);
}
