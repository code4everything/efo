package org.code4everything.efo.base.util;

import org.code4everything.boot.message.VerifyCodeUtils;
import org.code4everything.boot.web.mvc.AssertUtils;
import org.code4everything.boot.web.mvc.exception.ExceptionFactory;
import org.code4everything.efo.base.constant.EfoError;

import java.util.regex.Pattern;

/**
 * @author pantao
 * @since 2019-04-15
 */
public final class Checker {

    // TODO: 2019/5/24 将用户名和密码格式由管理员配置

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,20}$");

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{3,9}$");

    private Checker() {}

    /**
     * 校验验证码
     */
    public static void checkCode(String email, String code) {
        VerifyCodeUtils.assertCorrect(email, code, true, ExceptionFactory.exception(EfoError.CODE_ERROR));
    }

    /**
     * 校验密码格式
     */
    public static void checkPassword(String password) {
        AssertUtils.throwIf(!PASSWORD_PATTERN.matcher(password).matches(), EfoError.ILLEGAL_PASSWORD);
    }

    /**
     * 校验用户名格式
     */
    public static void checkUsername(String username) {
        AssertUtils.throwIf(!USERNAME_PATTERN.matcher(username).matches(), EfoError.ILLEGAL_USERNAME);
    }
}
