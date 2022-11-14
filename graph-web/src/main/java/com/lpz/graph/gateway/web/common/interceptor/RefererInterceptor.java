package com.lpz.graph.gateway.web.common.interceptor;

import com.lpz.graph.gateway.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;

/**
 * 检查Referer，防止csrf问题，拦截器
 *
 * @author lpz
 * @Date: 2019-09-20
 */
@Slf4j
public class RefererInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 检查Referer，防止csrf问题
//        String path = request.getServletPath(); // /login/getCaptcha
        String requestURL = request.getRequestURL().toString();
        log.debug("RefererInterceptor... getRequestURL: {}", requestURL);
        if (StringUtil.isBlank(requestURL) /*|| isIgnore(path)*/) {
            return true;
        }
        URL url = new URL(requestURL);
        String host = url.getHost();
        //
        String referer = request.getHeader("Referer");
        if (StringUtil.isNotBlank(referer)) {
            URL refererUrl = new URL(referer);
            String refererHost = refererUrl.getHost();
            if (!refererHost.equalsIgnoreCase(host)) {
                log.error("illegal Referer: {}, host:{}", referer, host);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

}
