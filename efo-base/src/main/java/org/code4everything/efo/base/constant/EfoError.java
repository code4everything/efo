package org.code4everything.efo.base.constant;

import org.code4everything.boot.web.mvc.exception.ExceptionBiscuit;
import org.springframework.http.HttpStatus;

/**
 * @author pantao
 * @since 2019-04-11
 */
public enum EfoError implements ExceptionBiscuit {

    /**
     *
     */
    CODE_FREQUENTLY(1001, "发送验证码过于频繁"),

    EMAIL_EXISTS(1002, "邮箱已存在"),

    USERNAME_EXISTS(1003, "用户名已存在"),

    ILLEGAL_PASSWORD(1004, "密码格式不合法"),

    ILLEGAL_USERNAME(1005, "用户名格式不合法"),

    CODE_ERROR(1006, "验证码不正确"),

    UPLOAD_ERROR(1007, "内部错误，文件上传失败");

    private final int code;

    private final String msg;

    EfoError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.OK;
    }
}
