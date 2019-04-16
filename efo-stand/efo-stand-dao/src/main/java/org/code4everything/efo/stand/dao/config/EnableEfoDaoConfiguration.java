package org.code4everything.efo.stand.dao.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author pantao
 * @since 2019/4/16
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EfoDaoConfiguration.class})
public @interface EnableEfoDaoConfiguration {}
