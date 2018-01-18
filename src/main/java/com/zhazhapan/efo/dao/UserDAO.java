package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @date 2018/1/12
 */
@Repository
public interface UserDAO {

    /**
     * 通过id获取一个用户
     *
     * @param id 编号
     *
     * @return {@link User}
     */
    @Select("select * from user where id=#{id}")
    User getUserById(int id);

    /**
     * 获取所有用户，包括管理员
     *
     * @return {@link List}
     */
    @Select("select * from user")
    List<User> getAllUser();

    /**
     * 获取所有管理员
     *
     * @return {@link List}
     */
    @Select("select * from user where permission=2")
    List<User> getAllAdministrator();

    /**
     * 获取所有用户，不包括管理员
     *
     * @return {@link List}
     */
    @Select("select * from user where permission=1")
    List<User> getAllUserWithoutAdministrator();

    /**
     * 用户登录
     *
     * @param usernameOrEmail 用户名
     * @param password 密码
     *
     * @return {@link User}
     */
    @Select("select * from user where (username=#{usernameOrEmail} or email=#{usernameOrEmail}) and password=sha2(#{password},256)")
    User doLogin(@Param("usernameOrEmail") String usernameOrEmail, @Param("password") String password);

    /**
     * 添加一个用户
     *
     * @param user {@link User}
     */
    @Insert("insert into user(username,real_name,email,password) values(#{username},#{realName},#{email},sha2(#{password},256))")
    void insertUser(User user);

    /**
     * 通过id更新用户登录时间
     *
     * @param id 编号
     */
    @Update("update user set last_login_time=current_timestamp where id=#{id}")
    void updateUserLoginTime(int id);

    /**
     * 更新操作用户权限
     *
     * @param id 用户编号
     * @param isDownloadable 下载权限
     * @param isUploadable 上传权限
     * @param isVisible 可查权限
     * @param isDeletable 删除权限
     * @param isUpdatable 更新权限
     */
    @Update("update user set is_downloadable=#{isDownloadable},is_visible=#{isVisible},is_uploadable=#{isUploadable},is_deletable=#{isDeletable},is_updatable=#{isUpdatable} where id=#{id}")
    void updateUserAuth(@Param("id") int id, @Param("isDownloadable") int isDownloadable, @Param("isUploadable") int isUploadable, @Param("isVisible") int isVisible, @Param("isDeletable") int isDeletable, @Param("isUpdatable") int isUpdatable);
}
