package com.zhazhapan.efo.util;

import com.zhazhapan.util.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pantao
 * @date 2018/1/18
 */
public class BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {}

    public static String toPrettyJson(Object object) {
        String result;
        try {
            result = com.zhazhapan.util.BeanUtils.toPrettyJson(object, com.zhazhapan.util.BeanUtils.FieldModifier.PRIVATE);
        } catch (IllegalAccessException e) {
            result = Formatter.formatJson("{\"error\":\"internal error, please try again later\"}");
            logger.error(e.getMessage());
        }
        return result;
    }
}
