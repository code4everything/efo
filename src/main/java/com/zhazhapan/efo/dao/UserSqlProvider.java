package com.zhazhapan.efo.dao;

import org.apache.ibatis.jdbc.SQL;

import java.sql.Timestamp;

/**
 * @author pantao
 * @date 2018/1/12
 */
public class UserSqlProvider {

    public String updateUserLoginTime() {
        return new SQL() {{
            UPDATE("user");
            SET("last_login_time=" + new Timestamp(System.currentTimeMillis()));
            WHERE("id=#{id}");
        }}.toString();
    }
}
