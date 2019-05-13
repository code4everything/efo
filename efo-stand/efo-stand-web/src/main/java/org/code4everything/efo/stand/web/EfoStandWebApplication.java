package org.code4everything.efo.stand.web;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.code4everything.boot.config.EnableSurfaceConfiguration;
import org.code4everything.boot.config.EnableSurfaceLog;
import org.code4everything.boot.config.EnableSurfaceMail;
import org.code4everything.efo.stand.dao.config.EnableEfoDaoConfiguration;
import org.code4everything.efo.stand.file.config.EnableEfoFileConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pantao
 * @since 2019-04-10
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableSurfaceMail
@EnableSurfaceLog
@EnableSurfaceConfiguration
@EnableEfoDaoConfiguration
@EnableEfoFileConfiguration
public class EfoStandWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EfoStandWebApplication.class, args);
    }
}
