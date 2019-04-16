package org.code4everything.efo.stand.web;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.code4everything.boot.annotation.EnableSurfaceConfiguration;
import org.code4everything.boot.annotation.EnableSurfaceMailSender;
import org.code4everything.efo.stand.dao.config.EnableEfoDaoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pantao
 * @since 2019-04-10
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableEfoDaoConfiguration
@EnableSurfaceMailSender
@EnableSurfaceConfiguration
public class EfoStandWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EfoStandWebApplication.class, args);
    }
}
