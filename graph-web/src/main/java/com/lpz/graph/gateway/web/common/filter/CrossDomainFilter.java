package com.lpz.graph.gateway.web.common.filter;

import com.alibaba.fastjson.JSON;
import com.lpz.graph.gateway.common.param.resp.Response;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 设置允许跨域
 */
@Slf4j
public class CrossDomainFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Request-Headers", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "content-type,x-auth-token");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "*");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setCharacterEncoding("UTF-8");
            PrintWriter w = response.getWriter();
            Response apiResult = Response.buildSuccess("ok");
            w.append(JSON.toJSONString(apiResult));
            return;
        }

        filterChain.doFilter(servletRequest, httpServletResponse);

    }

    @Override
    public void destroy() {

    }
}
