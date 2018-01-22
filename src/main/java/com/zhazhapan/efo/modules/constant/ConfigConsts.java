package com.zhazhapan.efo.modules.constant;

import com.zhazhapan.modules.constant.Values;

/**
 * @author pantao
 * @date 2018/1/12
 */
public class ConfigConsts {

    /**
     * 配置文件中登录的路径
     */
    public static final String ALLOW_LOGIN_OF_SETTINGS = "global.allowLogin";

    /**
     * 上传路径在全局配置中的路径
     */
    public static final String UPLOAD_PATH_OF_GLOBAL = "uploadPath";

    /**
     * 配置文件中全局配置的路径
     */
    public static final String GLOBAL_OF_SETTINGS = "global";

    /**
     * 配置文件中用户表的order by路径
     */
    public static final String USER_ORDER_BY_OF_SETTINGS = "user.orderBy";

    /**
     * 配置文件中用户表的page size路径
     */
    public static final String USER_PAGE_SIZE_OF_SETTINGS = "user.pageSize";

    /**
     * 配置文件中下载记录表的order by路径
     */
    public static final String DOWNLOAD_ORDER_BY_OF_SETTINGS = "download.orderBy";

    /**
     * 配置文件中下载表的page size路径
     */
    public static final String DOWNLOAD_PAGE_SIZE_OF_SETTINGS = "download.pageSize";

    /**
     * 配置文件中权限记录表的order by路径
     */
    public static final String AUTH_ORDER_BY_OF_SETTINGS = "auth.orderBy";

    /**
     * 配置文件中权限表的page size路径
     */
    public static final String AUTH_PAGE_SIZE_OF_SETTINGS = "auth.pageSize";

    /**
     * 默认上传路径，如果配置文件中的上传路径无法创建，将使用默认的上传路径
     */
    public static final String DEFAULT_UPLOAD_PATH = Values.USER_HOME + Values.SEPARATOR + "Desktop" + Values.SEPARATOR + "upload";
}
