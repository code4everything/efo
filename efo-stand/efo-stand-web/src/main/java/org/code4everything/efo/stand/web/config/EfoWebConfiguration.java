package org.code4everything.efo.stand.web.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.code4everything.boot.config.BootConfig;
import org.code4everything.boot.constant.MessageConsts;
import org.code4everything.boot.web.CorsUtils;
import org.code4everything.boot.web.mvc.DefaultExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author pantao
 * @since 2019-04-12
 */
@Configuration
public class EfoWebConfiguration implements WebMvcConfigurer {

    @Autowired
    public EfoWebConfiguration(JavaMailSender mailSender, @Value("${spring.mail.username}") String outbox) {
        BootConfig.setMailSender(outbox, mailSender);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
        handler.addException(401, MessageConsts.UNKNOWN_ACCOUNT_ZH, UnknownAccountException.class);
        handler.addException(401, MessageConsts.PASSWORD_ERROR_ZH, AuthenticationException.class);
        handler.addException(400, "参数校验失败", MethodArgumentNotValidException.class);
        handler.addException(400, "参数格式不正确", IllegalArgumentException.class);
        resolvers.add(handler);
    }

    @Bean
    public CorsFilter corsFilter() {
        return CorsUtils.newCorsFilter();
    }
}
