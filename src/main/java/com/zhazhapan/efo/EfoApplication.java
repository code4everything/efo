package com.zhazhapan.efo;

import com.zhazhapan.config.JsonParser;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author pantao
 */
@SpringBootApplication
@MapperScan("com.zhazhapan.efo.dao")
public class EfoApplication {

    public static JsonParser settings = new JsonParser();

    private static Logger logger = LoggerFactory.getLogger(EfoApplication.class);

    public static void main(String[] args) throws IOException {
        settings.setJsonPath(EfoApplication.class.getResource("/settings.json"));
        SpringApplication.run(EfoApplication.class, args);
    }
}
