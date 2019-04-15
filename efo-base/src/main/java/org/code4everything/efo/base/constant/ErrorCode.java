package org.code4everything.efo.base.constant;

/**
 * @author pantao
 * @since 2019-04-11
 */
public class ErrorCode {

    /**
     * 频繁发送验证码
     */
    public static final int CODE_FREQUENTLY = 1001;

    /**
     * 邮箱已存在
     */
    public static final int EMAIL_EXISTS = 1002;

    /**
     * 用户名已存在
     */
    public static final int USERNAME_EXISTS = 1003;

    /**
     * 密码格式不合法
     */
    public static final int ILLEGAL_PASSWORD = 1004;

    /**
     * 用户名格式不合法
     */
    public static final int ILLEGAL_USERNAME = 1005;

    /**
     * 验证码不正确
     */
    public static final int CODE_ERROR = 1006;

    private ErrorCode() {}
}
