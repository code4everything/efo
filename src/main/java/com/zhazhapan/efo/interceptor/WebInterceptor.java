package com.zhazhapan.efo.interceptor;

import com.zhazhapan.efo.EfoApplication;
import com.zhazhapan.efo.annotation.AuthInterceptor;
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
        String url = request.getServletPath();
        boolean shouldIntercept = url.startsWith(DefaultValues.INDEX_PAGE);
        InterceptorLevel level = InterceptorLevel.USER;
        if (handler instanceof HandlerMethod) {
            AuthInterceptor interceptor = ((HandlerMethod) handler).getMethodAnnotation(AuthInterceptor.class);
            if (Checker.isNull(interceptor)) {
                for (Class<?> type : EfoApplication.controllers) {
                    RequestMapping mapping = type.getAnnotation(RequestMapping.class);
                    if (Checker.isNotNull(mapping)) {
                        for (String path : mapping.value()) {
                            if (url.startsWith(path)) {
                                interceptor = type.getAnnotation(AuthInterceptor.class);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            if (Checker.isNotNull(interceptor)) {
                level = interceptor.value();
                shouldIntercept = true;
            }
        }
        if (shouldIntercept && level != InterceptorLevel.NONE) {
            User user = (User) request.getSession().getAttribute("user");
            boolean isRedirect = Checker.isNull(user) || (level == InterceptorLevel.ADMIN && user.getPermission() > 1);
            if (isRedirect) {
                response.sendRedirect(DefaultValues.SIGNIN_PAGE);
            }
        }
        return true;
    }
}
