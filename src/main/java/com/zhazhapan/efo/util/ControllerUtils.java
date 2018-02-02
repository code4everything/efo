package com.zhazhapan.efo.util;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.FileExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;

/**
 * @author pantao
 * @date 2018/1/30
 */
public class ControllerUtils {

    private static Logger logger = LoggerFactory.getLogger(ControllerUtils.class);

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

    /**
     * 加载本地资源
     *
     * @param response 返回的Response
     * @param path 资源路径
     */
    public static void loadResource(HttpServletResponse response, String path) {
        File file = new File(path);
        try {
            OutputStream os = response.getOutputStream();
            os.write(FileExecutor.readFileToByteArray(file));
            os.flush();
            os.close();
        } catch (Exception e) {
            response.setStatus(404);
            logger.error(e.getMessage());
        }
    }
}
