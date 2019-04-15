package org.code4everything.efo.base.util;

import org.code4everything.boot.exception.BootException;
import org.code4everything.boot.exception.ExceptionFactory;
import org.code4everything.boot.exception.ExceptionThrower;
import org.code4everything.boot.message.VerifyCodeUtils;
import org.code4everything.efo.base.constant.EfoError;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

/**
 * @author pantao
 * @since 2019-04-15
 */
public class ExceptionUtils {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,20}$");

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{3,9}$");

    private static final ExceptionThrower THROWER = ExceptionThrower.getInstance();

    private ExceptionUtils() {}

    public static void throwIf(boolean result, int code, String msg) {
        THROWER.throwIf(result, ExceptionFactory.exception(code, msg, HttpStatus.OK));
    }

    public static void throwIfFalse(boolean result, int code, String msg) {
        THROWER.throwIf(!result, ExceptionFactory.exception(code, msg, HttpStatus.OK));
    }

    public static <T extends BootException> void throwIf(boolean result, int code, String msg, Class<T> clazz) {
        THROWER.throwIf(result, ExceptionFactory.exception(code, msg, HttpStatus.OK, clazz));
    }

    public static void checkCode(String email, String code) {
        EfoError error = EfoError.CODE_ERROR;
        throwIfFalse(VerifyCodeUtils.validate(email, code, true), error.getCode(), error.getMsg());
    }

    public static void checkPassword(String password) {
        EfoError error = EfoError.ILLEGAL_PASSWORD;
        throwIfFalse(PASSWORD_PATTERN.matcher(password).matches(), error.getCode(), error.getMsg());
    }

    public static void checkUsername(String username) {
        EfoError error = EfoError.ILLEGAL_USERNAME;
        throwIfFalse(USERNAME_PATTERN.matcher(username).matches(), error.getCode(), error.getMsg());
    }
}
