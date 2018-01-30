package com.zhazhapan.efo.util;

import com.zhazhapan.util.Formatter;

/**
 * @author pantao
 * @date 2018/1/30
 */
public class Utils {

    public static int stringToInt(String string) {
        try {
            return Formatter.stringToInt(string);
        } catch (Exception e) {
            return 0;
        }
    }
}
