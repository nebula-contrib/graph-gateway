package com.lpz.graph.gateway.web.controller.base;

import com.lpz.graph.gateway.common.constant.ErrorCode;
import com.lpz.graph.gateway.common.constant.SessionConstants;
import com.lpz.graph.gateway.common.exception.BizException;
import com.lpz.graph.gateway.common.param.resp.SessionBo;
import com.lpz.graph.gateway.common.util.CacheManager;
import com.lpz.graph.gateway.common.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 */
@Slf4j
public abstract class BaseController extends ApiController {

    //    @Autowired
//    private RedisService redisService;
    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected HttpServletRequest request;


    @Value("${spring.profiles.active}")
    protected String active;


    /**
     * 获取当前请求
     *
     * @return request
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前请求
     *
     * @return response
     */
    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * @return
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }


    /**
     * 从内存中 获取session
     *
     * @param request
     * @return
     * @author lpz
     */
    protected SessionBo getSession(HttpServletRequest request) {

        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies) || cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (SessionConstants.SESSION_KEY.equals(name)) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        if (StringUtil.isBlank(sessionId)) {
            log.error("the httpRequest param sessionId is null...");
            throw new BizException(ErrorCode.NOT_LOGIN_ERROR);
        }
        //
        SessionBo sessionBo = CacheManager.get(sessionId);
//        SessionBo sessionBo = (SessionBo) redisService.get(sessionId);
        if (null == sessionBo) {
            log.error("find cache value is null... request sessionId:{}", sessionId);
            throw new BizException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return sessionBo;
    }

    /**
     * @return
     */
    public String getSessionUserName() {
        return (String) getRequest().getSession().getAttribute(SessionConstants.SESSION_KEY_USERNAME);
    }


    /**
     * 设置cookie
     *
     * @param response
     * @param key
     * @param value
     */
    public void setCookie(HttpServletResponse response, String key, String value) {
        // 登陆成功后更新session？？
        request.getSession(true);
        request.changeSessionId();
        Cookie cookie = new Cookie(key, value);
//        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

}
