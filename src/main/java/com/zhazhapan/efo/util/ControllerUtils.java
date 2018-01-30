package com.zhazhapan.efo.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author pantao
 * @date 2018/1/30
 */
public class ControllerUtils {

    /**
     * 获取一个简单的响应状态
     *
     * @param isSuccess 是否操作成功
     *
     * @return 响应JSON字符串
     */
    public static String getResponse(boolean isSuccess) {
        JSONObject jsonObject = new JSONObject();
        if (isSuccess) {
            jsonObject.put("status", "success");
        } else {
            jsonObject.put("status", "error");
        }
        return jsonObject.toString();
    }
}
