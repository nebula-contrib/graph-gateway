
package com.lpz.graph.gateway.web.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 */
@Slf4j
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ObjectMapper mapper;

//    @Autowired
//    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // /webjars/springfox-swagger-ui/css/reset.css
        // /login/getCaptchaImg、/label/query/list
        String servletPath = request.getServletPath();
        log.debug("PermissionInterceptor... getServletPath: {}", servletPath);

//        Cookie[] cookies = request.getCookies();
//        String header = cookies.getNsidxxxx;
//        if (StringUtil.isNotBlank(header)) {
//            Object obj = redisService.get(header);
//            if (Objects.nonNull(obj)) {
//                log.info(obj.toString());
//                return true;
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

}
