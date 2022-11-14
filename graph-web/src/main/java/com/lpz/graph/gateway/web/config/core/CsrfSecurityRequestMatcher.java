package com.lpz.graph.gateway.web.config.core;

import com.lpz.graph.gateway.common.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @Author: lpz
 * @Date: 2019-09-20 14:46
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher {

    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    // ^匹配输入字符串的开始位置，$匹配字符串输入的结尾位置
    // 不处理的请求
    private static Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    private static Set<String> noAuthUrlList = new HashSet<>();

    @Override
    public boolean matches(HttpServletRequest request) {
        boolean matches = !allowedMethods.matcher(request.getMethod()).matches();
        if (matches) {
            if (CollectionUtils.isEmpty(noAuthUrlList)) {
                // /api-docs/**
                String[] excludePath = springBootPlusProperties.getInterceptorConfig().getPermissionConfig().getExcludePath();
                List<String> excludePathList = Arrays.asList(excludePath);
                excludePathList.forEach(str -> {
                            String replace = str.replace("/**", "/");
                            if (StringUtil.isNotBlank(replace)) {
                                noAuthUrlList.add(replace);
                            }
                        }
                );
            }
            //
            if (CollectionUtils.isNotEmpty(noAuthUrlList)) {
                String servletPath = request.getServletPath();
                for (String execludeUrl : noAuthUrlList) {
                    if (servletPath.contains(execludeUrl)) {
                        return false;
                    }
                }
            }
        }
        return matches;
    }
}
