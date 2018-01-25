package com.zhazhapan.efo.config;

import com.zhazhapan.efo.interceptor.WebInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author pantao
 * @date 2018/1/22
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".html");
        registry.viewResolver(viewResolver);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("/index");
        registry.addViewController("/signin").setViewName("/signin");
        registry.addViewController("/admin").setViewName("/admin");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }

    @Bean
    public WebInterceptor webInterceptor() {
        return new WebInterceptor();
    }
}
