package org.code4everything.efo.base.constant;

/**
 * @author pantao
 * @since 2019-04-11
 */
public enum EfoError {

    /**
     *
     */
    CODE_FREQUENTLY(1001, "发送验证码过于频繁"),

    EMAIL_EXISTS(1002, "邮箱已存在"),

    USERNAME_EXISTS(1003, "用户名已存在"),

    ILLEGAL_PASSWORD(1004, "密码格式不合法"),

    ILLEGAL_USERNAME(1005, "用户名格式不合法"),

    CODE_ERROR(1006, "验证码不正确");

    private final int code;

    private final String msg;

    EfoError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
