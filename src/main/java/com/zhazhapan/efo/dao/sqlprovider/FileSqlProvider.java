package com.zhazhapan.efo.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author pantao
 * @date 2018/1/19
 */
public class FileSqlProvider {

    public String updateAuthById() {
        return CommonSqlProvider.updateAuthById("file");
    }

    public String getAll() {
        return getBaseSql() + " where f.is_visible=1";
    }

    public String getUserUploaded() {
        return getAll() + " and f.user_id=#{userId}";
    }

    public String getUserDownloaded() {
        return getBaseSql() + " join download d on d.file_id=f.id where d.user_id=#{userId}";
    }

    public String getBaseSql() {
        return new SQL() {{
            SELECT("f.id,f.user_id,u.username,u.avatar,f.name file_name,f.size,f.create_time,c.name category_name,f"
                    + ".description,f.tag,f.check_times,f.download_times,f.visit_url,f.is_uploadable,f.is_deletable,"
                    + "f.is_updatable,f.is_downloadable,f.is_visible");
            FROM("file f");
            JOIN("user u on u.id=f.user_id");
            JOIN("category c on c.id=f.category_id");
        }}.toString();
    }
}
