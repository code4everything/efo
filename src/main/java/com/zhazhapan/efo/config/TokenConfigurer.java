package com.zhazhapan.efo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Hashtable;

/**
 * @author pantao
 * @date 2018/1/26
 */
public class TokenConfigurer {

    private static Logger logger = LoggerFactory.getLogger(TokenConfigurer.class);

    public static String generateToken(String token, User user) {
        if (Checker.isNotEmpty(token)) {
            EfoApplication.tokens.remove(token);
        }
        return generateToken(user);
    }

    public static String generateToken(User user) {
        String token = RandomUtils.getRandomStringOnlyLetter(64);
        EfoApplication.tokens.put(token, user);
        saveToken();
        return token;
    }

    public static void saveToken() {
        String tokens = Formatter.mapToJson(EfoApplication.tokens);
        try {
            FileExecutor.saveFile(EfoApplication.settings.getStringUseEval(ConfigConsts.TOKEN_OF_SETTINGS), tokens);
        } catch (IOException e) {
            logger.error("save token error： " + e.getMessage());
        }
    }

    public static Hashtable<String, User> loadToken() {
        Hashtable<String, User> tokens = new Hashtable<>(16);
        try {
            String token = FileExecutor.readFile(EfoApplication.settings.getStringUseEval(ConfigConsts.TOKEN_OF_SETTINGS));
            JSONArray array = JSON.parseArray(token);
            array.forEach(object -> {
                JSONObject jsonObject = (JSONObject) object;
                String key = jsonObject.getString("key");
                User user = new User("", "", "", "");
                try {
                    BeanUtils.jsonPutIn(jsonObject.getJSONObject("value"), user);
                } catch (IllegalAccessException e) {
                    logger.error("parse json error: " + e.getMessage());
                }
                tokens.put(key, user);
            });
        } catch (IOException e) {
            logger.error("load token error: " + e.getMessage());
        }
        return tokens;
    }
}
