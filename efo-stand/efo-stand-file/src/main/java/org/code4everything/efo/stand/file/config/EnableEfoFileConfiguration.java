package org.code4everything.efo.stand.file.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author pantao
 * @since 2019/4/17
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EfoFileConfiguration.class})
public @interface EnableEfoFileConfiguration {}
