package com.zhazhapan.efo;

import com.zhazhapan.config.JsonParser;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.util.MailSender;
import com.zhazhapan.util.ReflectUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author pantao
 */
@SpringBootApplication
@MapperScan("com.zhazhapan.efo.dao")
@EnableAutoConfiguration
public class EfoApplication {

    public static JsonParser settings = new JsonParser();

    public static Pattern usernamePattern;

    public static List<Class<?>> controllers;

    public static Hashtable<String, Integer> tokens = new Hashtable<>(16);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        settings.setJsonPath(EfoApplication.class.getResource("/settings.json"));
        MailSender.config(settings.getObjectUseEval(ConfigConsts.EMAIL_CONFIG_OF_SETTINGS));
        usernamePattern = Pattern.compile(settings.getStringUseEval(ConfigConsts.USERNAME_PATTERN_OF_SETTINGS));
        controllers = ReflectUtils.getClasses("com.zhazhapan.efo.web.controller");
        SpringApplication.run(EfoApplication.class, args);
    }
}
