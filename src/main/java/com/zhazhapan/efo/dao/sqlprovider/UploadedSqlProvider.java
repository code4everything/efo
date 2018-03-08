package com.zhazhapan.efo.dao.sqlprovider;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.util.Checker;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author pantao
 * @since 2018/2/28
 */
public class UploadedSqlProvider {

    /**
     * 生成一条上传记录表的查询语句
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     *
     * @return SQL语句
     */
    public String getDownloadBy(@Param("userId") int userId, @Param("fileId") long fileId, @Param("fileName") String
            fileName, @Param("categoryId") int categoryId, @Param("offset") int offset) {
        String sql = new SQL() {{
            SELECT("f.id,f.user_id,u.username,u.email,f.name file_name,c.name category_name,f.local_url,f.visit_url,"
                    + "" + "" + "" + "f" + ".create_time");
            FROM("file f");
            JOIN("user u on f.user_id=u.id");
            JOIN("category c on f.category_id=c.id");
            if (userId > 0) {
                WHERE("f.user_id=#{userId}");
            }
            if (fileId > 0) {
                WHERE("f.id=#{fileId}");
            } else if (Checker.isNotEmpty(fileName)) {
                WHERE("f.local_url like '%" + fileName + "%'");
            }
            if (categoryId > 0) {
                WHERE("c.id=#{categoryId}");
            }
            ORDER_BY("f." + EfoApplication.settings.getStringUseEval(ConfigConsts.FILE_ORDER_BY_OF_SETTING));
        }}.toString();
        int size = EfoApplication.settings.getIntegerUseEval(ConfigConsts.FILE_PAGE_SIZE_OF_SETTING);
        return sql + " limit " + (offset * size) + "," + size;
    }
}
