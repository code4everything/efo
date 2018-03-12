package com.zhazhapan.efo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.Formatter;
import com.zhazhapan.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;

/**
 * @author pantao
 * @since 2018/1/26
 */
public class TokenConfig {

    private static Logger logger = LoggerFactory.getLogger(TokenConfig.class);

    public static String generateToken(String token, int userId) {
        if (Checker.isNotEmpty(token)) {
            EfoApplication.tokens.remove(token);
        }
        return generateToken(userId);
    }

    public static String generateToken(int userId) {
        String token = RandomUtils.getRandomStringOnlyLetter(ValueConsts.THIRTY_TWO_INT);
        EfoApplication.tokens.put(token, userId);
        saveToken();
        return token;
    }

    public static void saveToken() {
        String tokens = Formatter.mapToJson(EfoApplication.tokens);
        try {
            FileExecutor.saveFile(SettingConfig.getStoragePath(ConfigConsts.TOKEN_OF_SETTINGS), tokens);
        } catch (Exception e) {
            logger.error("save token error： " + e.getMessage());
        }
    }

    public static Hashtable<String, Integer> loadToken() {
        Hashtable<String, Integer> tokens = new Hashtable<>(ValueConsts.SIXTEEN_INT);
        try {
            String token = FileExecutor.readFile(SettingConfig.getStoragePath(ConfigConsts.TOKEN_OF_SETTINGS));
            JSONArray array = JSON.parseArray(token);
            array.forEach(object -> {
                JSONObject jsonObject = (JSONObject) object;
                tokens.put(jsonObject.getString(ValueConsts.KEY_STRING), jsonObject.getInteger(ValueConsts
                        .VALUE_STRING));
            });
        } catch (Exception e) {
            logger.error("load token error: " + e.getMessage());
        }
        return tokens;
    }

    public static void removeTokenByValue(int userId) {
        if (userId > 0) {
            String removeKey = "";
            for (String key : EfoApplication.tokens.keySet()) {
                if (EfoApplication.tokens.get(key) == userId) {
                    removeKey = key;
                    break;
                }
            }
            if (Checker.isNotEmpty(removeKey)) {
                EfoApplication.tokens.remove(removeKey);
                TokenConfig.saveToken();
            }
        }
    }
}
