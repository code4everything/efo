package com.zhazhapan.efo.modules.constant;

/**
 * @author pantao
 * @since 2018/1/12
 */
public class ConfigConsts {

    /**
     * 最大标签长度
     */
    public static final String TAG_SIZE_OF_SETTING = "file.tag.maxSize";

    /**
     * 每个标签最大长度
     */
    public static final String TAG_LENGTH_OF_SETTING = "file.tag.maxLength";

    /**
     * 文件标签
     */
    public static final String TAG_REQUIRE_OF_SETTING = "file.tag.require";

    /**
     * 文件默认排序方式
     */
    public static final String FILE_ORDER_BY_OF_SETTING = "file.orderBy";

    /**
     * 文件分页大小
     */
    public static final String FILE_PAGE_SIZE_OF_SETTING = "file.pageSize";

    /**
     * 匿名用户下载权限
     */
    public static final String ANONYMOUS_DOWNLOADABLE_OF_SETTING = "global.anonymousUser.downloadable";

    /**
     * 匿名用户访问权限
     */
    public static final String ANONYMOUS_VISIBLE_OF_SETTING = "global.anonymousUser.visible";

    /**
     * 文件后缀匹配
     */
    public static final String FILE_SUFFIX_MATCH_OF_SETTING = "file.suffixMatch.pattern";

    /**
     * 是否覆盖文件
     */
    public static final String FILE_COVER_OF_SETTING = "file.coverIfExists";

    /**
     * 自定义文件上传链接
     */
    public static final String CUSTOM_LINK_RULE_OF_SETTING = "file.linkRule.custom";

    /**
     * 最大上传大小路径
     */
    public static final String FILE_MAX_SIZE_OF_SETTING = "file.maxSize";

    /**
     * 上传路径在全局中的路径
     */
    public static final String UPLOAD_PATH_OF_SETTING = "global.uploadPath";

    /**
     * 上传形式路径
     */
    public static final String UPLOAD_FORM_OF_SETTING = "global.uploadForm";

    /**
     * token的路径
     */
    public static final String TOKEN_OF_SETTINGS = "global.tokenPath";

    /**
     * 是否验证邮箱的路径
     */
    public static final String EMAIL_VERIFY_OF_SETTINGS = "user.emailVerify";

    /**
     * 用户默认权限
     */
    public static final String FILE_DEFAULT_AUTH_OF_SETTING = "file.defaultAuth";

    /**
     * 默认权限
     */
    public static final String AUTH_DEFAULT_OF_SETTING = "auth.default";

    /**
     * 用户默认权限
     */
    public static final String USER_DEFAULT_AUTH_OF_SETTING = "user.defaultAuth";

    /**
     * 默认权限路径
     */
    public static final String[] AUTH_OF_SETTINGS = {"isDownloadable", "isUploadable", "isDeletable", "isUpdatable",
            "isVisible"};

    /**
     * 密码最短长度的路径
     */
    public static final String PASSWORD_MIN_LENGTH_OF_SETTINGS = "user.password.minLength";

    /**
     * 密码最长长度的路径
     */
    public static final String PASSWORD_MAX_LENGTH_OF_SETTINGS = "user.password.maxLength";

    /**
     * 用户名匹配模式的路径
     */
    public static final String USERNAME_PATTERN_OF_SETTINGS = "user.usernameMatch.pattern";

    /**
     * 邮件配置的路径
     */
    public static final String EMAIL_CONFIG_OF_SETTINGS = "user.emailConfig";

    /**
     * 邮件配置在用户配置中的路径
     */
    public static final String EMAIL_CONFIG_OF_USER = "emailConfig";

    /**
     * 配置文件中用户配置的路径
     */
    public static final String USER_OF_SETTINGS = "user";

    /**
     * 是否允许用户注册的路径
     */
    public static final String ALLOW_REGISTER_OF_SETTINGS = "global.allowRegister";

    /**
     * 配置文件中登录的路径
     */
    public static final String ALLOW_LOGIN_OF_SETTINGS = "global.allowLogin";

    /**
     * token在全局中的路径
     */
    public static final String TOKEN_PATH_OF_GLOBAL = "tokenPath";

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
    public static final String DEFAULT_UPLOAD_PATH = DefaultValues.STORAGE_PATH + "upload";

    private ConfigConsts() {}
}
