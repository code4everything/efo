package com.zhazhapan.efo.dao.sqlprovider;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author pantao
 * @date 2018/1/19
 */
public class UserSqlProvider {

    public String updateAuthById() {
        return CommonSqlProvider.updateAuthById("user");
    }

    public String getUserBy(@Param("permission") int permission) {
        String sql = new SQL() {{
            SELECT("*");
            FROM("user");
            if (permission == DefaultValues.THREE_INT) {
                WHERE("permission!=3");
            } else if (permission == DefaultValues.TWO_INT) {
                WHERE("permission=2");
            } else {
                WHERE("permission=1");
            }
            ORDER_BY(EfoApplication.settings.getStringUseEval(ConfigConsts.USER_ORDER_BY_OF_SETTINGS));
        }}.toString();
        return sql + " limit #{offset}," + EfoApplication.settings.getIntegerUseEval(ConfigConsts.USER_PAGE_SIZE_OF_SETTINGS);
    }
}
