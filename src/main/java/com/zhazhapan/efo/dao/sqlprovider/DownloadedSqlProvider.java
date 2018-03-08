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
public class DownloadedSqlProvider {

    /**
     * 生成一条下载记录表的查询语句
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     *
     * @return SQL语句
     */
    public String getDownloadBy(@Param("userId") int userId, @Param("fileId") long fileId, @Param("fileName") String
            fileName, @Param("categoryId") int categoryId, @Param("offset") int offset) {
        String sql = new SQL() {{
            SELECT("d.id,d.user_id,d.file_id,u.username,u.email,f.name file_name,c.name category_name,f.visit_url,d"
                    + ".create_time");
            FROM("download d");
            JOIN("user u on d.user_id=u.id");
            JOIN("file f on d.file_id=f.id");
            JOIN("category c on f.category_id=c.id");
            if (userId > 0) {
                WHERE("d.user_id=#{userId}");
            }
            if (fileId > 0) {
                WHERE("d.file_id=#{fileId}");
            } else if (Checker.isNotEmpty(fileName)) {
                WHERE("f.local_url like '%" + fileName + "%'");
            }
            if (categoryId > 0) {
                WHERE("c.id=#{categoryId}");
            }
            ORDER_BY("d." + EfoApplication.settings.getStringUseEval(ConfigConsts.DOWNLOAD_ORDER_BY_OF_SETTINGS));
        }}.toString();
        int size = EfoApplication.settings.getIntegerUseEval(ConfigConsts.DOWNLOAD_PAGE_SIZE_OF_SETTINGS);
        return sql + " limit " + (offset * size) + "," + size;
    }
}
