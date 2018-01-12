package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author pantao
 * @date 2018/1/12
 */
public interface UserDAO {

    /**
     * 通过id获取一个用户
     *
     * @param id 编号
     *
     * @return {@link User}
     */
    @Select("select * from user where id=#{id}")
    User getUser(int id);

    /**
     * 通过id更新用户登录时间
     *
     * @param id 编号
     */
    @UpdateProvider(type = UserSqlProvider.class, method = "updateLoginTime")
    void updateLoginTime(int id);
}
