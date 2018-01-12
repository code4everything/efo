package com.zhazhapan.efo.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author pantao
 * @date 2018/1/12
 */
public class UserSqlProvider {

    public String updateLoginTime() {
        return new SQL() {{
            UPDATE("user");
            SET("last_login_time=" + new Date());
            WHERE("id=#{id}");
        }}.toString();
    }
}
