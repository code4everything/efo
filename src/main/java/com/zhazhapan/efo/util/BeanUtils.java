package com.zhazhapan.efo.util;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.Formatter;
import com.zhazhapan.util.enums.FieldModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author pantao
 * @since 2018/1/18
 */
public class BeanUtils {

    private static final String ERROR_JSON = "{\"error\":\"internal error, please try again later\"}";

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {}

    /**
     * 将Bean转换成JSON
     *
     * @param object Bean对象
     *
     * @return {@link String}
     */
    public static String toPrettyJson(Object object) {
        String result;
        try {
            result = com.zhazhapan.util.BeanUtils.toPrettyJson(object, FieldModifier.PRIVATE);
        } catch (IllegalAccessException e) {
            result = Formatter.formatJson(ERROR_JSON);
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 将类属性装换成JSON（只能转换有get方法的）
     *
     * @param object 转换的对象
     *
     * @return {@link JSONObject}
     */
    public static JSONObject beanToJson(Object object) {
        try {
            return com.zhazhapan.util.BeanUtils.beanToJson(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
