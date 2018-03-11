package com.zhazhapan.efo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author pantao
 * @since 2018/1/29
 */
public interface IFileManagerService {


    /**
     * 下载多个文件
     *
     * @param response {@link HttpServletResponse}
     * @param items 文件集
     * @param destFile 目标文件名
     *
     * @throws IOException 异常
     */
    void multiDownload(HttpServletResponse response, String[] items, String destFile) throws IOException;

    /**
     * 上传文件（暂时还没有实现）
     *
     * @param destination 目标文件
     * @param files {@link MultipartFile}
     *
     * @return {@link JSONObject}
     */
    JSONObject upload(String destination, MultipartFile... files);

    /**
     * 解压文件
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONObject extract(JSONObject object);

    /**
     * 压缩文件
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONObject compress(JSONObject object);

    /**
     * 设置文件权限
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONObject setPermission(JSONObject object);

    /**
     * 创建文件夹
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONObject createFolder(JSONObject object);

    /**
     * 获取文件内容
     *
     * @param object {@link JSONObject}
     *
     * @return 文件内容
     */
    String getContent(JSONObject object);

    /**
     * 编辑文件
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONObject edit(JSONObject object);

    /**
     * 移除文件
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONObject remove(JSONObject object);

    /**
     * 复制文件
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONObject copy(JSONObject object);

    /**
     * 移动文件
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONObject move(JSONObject object);

    /**
     * 重命名
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONObject rename(JSONObject object);

    /**
     * 列出文件
     *
     * @param object {@link JSONObject}
     *
     * @return {@link JSONObject}
     */
    JSONArray list(JSONObject object);
}
