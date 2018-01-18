package com.zhazhapan.efo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pantao
 */
@SpringBootApplication
@MapperScan("com.zhazhapan.efo.dao")
public class EfoApplication {

    private static Logger logger = LoggerFactory.getLogger(EfoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EfoApplication.class, args);
    }
}
