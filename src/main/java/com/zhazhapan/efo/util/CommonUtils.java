package com.zhazhapan.efo.util;

import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.modules.constant.ValueConsts;

/**
 * @author pantao
 * @date 2018/1/29
 */
public class CommonUtils {

    /**
     * 将相对路径转换成绝对路径
     *
     * @param path 文件路径
     *
     * @return {@link String}
     */
    public static String checkPath(String path) {
        return path.startsWith(ValueConsts.SEPARATOR) || path.startsWith(DefaultValues.COLON + ValueConsts.SEPARATOR, 1) ? path : DefaultValues.STORAGE_PATH + path;
    }
}
