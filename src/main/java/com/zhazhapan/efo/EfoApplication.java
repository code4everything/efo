package com.zhazhapan.efo;

import com.zhazhapan.config.JsonParser;
import com.zhazhapan.efo.modules.constant.ConfigConsts;
import com.zhazhapan.util.MailSender;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author pantao
 */
@SpringBootApplication
@MapperScan("com.zhazhapan.efo.dao")
@EnableAutoConfiguration
public class EfoApplication {

    public static JsonParser settings = new JsonParser();

    public static void main(String[] args) throws IOException {
        settings.setJsonPath(EfoApplication.class.getResource("/settings.json"));
        MailSender.config(settings.getObjectUseEval(ConfigConsts.EMAIL_CONFIG_OF_SETTINGS));
        SpringApplication.run(EfoApplication.class, args);
    }
}
