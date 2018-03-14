package com.zhazhapan.efo.dao.sqlprovider;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.util.Checker;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author pantao
 * @since 2018/1/19
 */
public class AuthSqlProvider {

    public String updateAuthById() {
        return CommonSqlProvider.updateAuthById("auth");
    }

    public String batchDelete(@Param("ids") String ids) {
        return "delete from auth where id in " + (ids.startsWith("(") ? "" : "(") + ids + (ids.endsWith(")") ? "" :
                ")");
    }

    public String getAuthBy(@Param("id") long id, @Param("userId") int userId, @Param("fileId") long fileId, @Param
            ("fileName") String fileName, @Param("offset") int offset) {
        String sql = new SQL() {{
            SELECT("a.id,a.user_id,a.file_id,u.username,f.name file_name,f.local_url,a.is_downloadable,a" + "" + "" +
                    ".is_uploadable,a.is_deletable,a.is_updatable,a.is_visible,a.create_time");
            FROM("auth a");
            JOIN("user u on u.id=a.user_id");
            JOIN("file f on f.id=a.file_id");
            if (id > 0) {
                WHERE("a.id=#{id}");
            }
            if (userId > 0) {
                WHERE("u.id=#{userId}");
            }
            if (fileId > 0) {
                WHERE("f.id=#{fileId}");
            } else if (Checker.isNotEmpty(fileName)) {
                WHERE("f.local_url like '%" + fileName + "%'");
            }
            ORDER_BY("a." + EfoApplication.settings.getStringUseEval(ConfigConsts.AUTH_ORDER_BY_OF_SETTINGS));
        }}.toString();
        int size = EfoApplication.settings.getIntegerUseEval(ConfigConsts.AUTH_PAGE_SIZE_OF_SETTINGS);
        return sql + " limit " + (offset * size) + "," + size;
    }
}
