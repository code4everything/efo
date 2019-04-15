package org.code4everything.efo.base.util;

import org.code4everything.boot.exception.BootException;
import org.code4everything.boot.exception.ExceptionFactory;
import org.code4everything.boot.exception.ExceptionThrower;
import org.springframework.http.HttpStatus;

/**
 * @author pantao
 * @since 2019-04-15
 */
public class ExceptionUtils {

    private static final ExceptionThrower THROWER = ExceptionThrower.getInstance();

    private ExceptionUtils() {}

    public static void throwIf(boolean result, int code, String msg) {
        THROWER.throwIf(result, ExceptionFactory.exception(code, msg, HttpStatus.OK));
    }

    public static <T extends BootException> void throwIf(boolean result, int code, String msg, Class<T> clazz) {
        THROWER.throwIf(result, ExceptionFactory.exception(code, msg, HttpStatus.OK, clazz));
    }
}
