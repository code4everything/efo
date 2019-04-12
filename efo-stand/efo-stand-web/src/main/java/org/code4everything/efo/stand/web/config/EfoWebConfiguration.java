package org.code4everything.efo.stand.web.config;

import org.code4everything.boot.config.BootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author pantao
 * @since 2019-04-12
 */
@Configuration
public class EfoWebConfiguration {

    @Autowired
    public EfoWebConfiguration(JavaMailSender mailSender, @Value("${spring.mail.username}") String outbox) {
        BootConfig.setMailSender(outbox, mailSender);
    }
}
