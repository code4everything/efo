package com.zhazhapan.efo.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.zhazhapan.util.Checker;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pantao
 * @date 2018/2/5
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<>(2);
        attributes.put("code", "502");
        attributes.put("message", ex.getMessage());
        String queryString = request.getQueryString();
        attributes.put("url", request.getRequestURI() + (Checker.isNullOrEmpty(queryString) ? "" : "?" + queryString));
        view.setAttributesMap(attributes);
        mv.setView(view);
        mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mv;
    }
}
