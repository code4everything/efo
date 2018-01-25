package com.zhazhapan.efo.interceptor;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.annotation.EfoInterceptor;
import com.zhazhapan.efo.entity.User;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.modules.constant.DefaultValues;
import com.zhazhapan.util.Checker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pantao
 * @date 2018/1/25
 */
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            EfoInterceptor interceptor = ((HandlerMethod) handler).getMethodAnnotation(EfoInterceptor.class);
            if (Checker.isNull(interceptor)) {
                for (Class<?> type : EfoApplication.controllers) {
                    RequestMapping mapping = type.getAnnotation(RequestMapping.class);
                    if (Checker.isNotNull(mapping) && request.getServletPath().startsWith(mapping.value()[0])) {
                        interceptor = type.getAnnotation(EfoInterceptor.class);
                        break;
                    }
                }
            }
            if (Checker.isNotNull(interceptor)) {
                InterceptorLevel level = interceptor.value();
                User user = (User) request.getSession().getAttribute("user");
                if (level != InterceptorLevel.NONE) {
                    boolean isRedirect = Checker.isNull(user) || (level == InterceptorLevel.ADMIN && user.getPermission() > 1);
                    if (isRedirect) {
                        response.sendRedirect(DefaultValues.SIGNIN_PAGE);
                    }
                }
            }
        }
        return true;
    }
}
