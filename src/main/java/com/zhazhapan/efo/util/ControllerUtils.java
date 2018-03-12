package com.zhazhapan.efo.util;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author pantao
 * @since 2018/1/30
 */
public class ControllerUtils {

    private ControllerUtils() {}

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
     * @param download 直接下载
     */
    public static void loadResource(HttpServletResponse response, String path, boolean download) throws IOException {
        if (Checker.isNotEmpty(path)) {
            File file = new File(path);
            if (download) {
                setResponseFileName(response, file.getName());
            }
            OutputStream os = response.getOutputStream();
            os.write(FileExecutor.readFileToByteArray(file));
            os.flush();
            os.close();
        } else {
            response.sendRedirect(DefaultValues.NOT_FOUND_PAGE);
        }
    }

    /**
     * 设置响应头的文件名
     *
     * @param response {@link HttpServletResponse}
     * @param fileName 文件名
     */
    public static void setResponseFileName(HttpServletResponse response, String fileName) throws
            UnsupportedEncodingException {
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"),
                "ISO-8859-1"));
    }
}
