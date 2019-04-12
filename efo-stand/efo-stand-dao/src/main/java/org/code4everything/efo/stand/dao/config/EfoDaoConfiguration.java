package org.code4everything.efo.stand.dao.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author pantao
 * @since 2019-04-12
 */
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan("org.code4everything.efo.stand.dao.domain")
@EnableJpaRepositories(basePackages = "org.code4everything.efo.stand.dao.repository")
public class EfoDaoConfiguration {}
