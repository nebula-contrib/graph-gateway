package com.lpz.graph.gateway.web.config.core;

import com.lpz.graph.gateway.web.common.aop.LogAop;
import com.lpz.graph.gateway.web.common.interceptor.PermissionInterceptor;
import com.lpz.graph.gateway.web.common.interceptor.RefererInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({SpringBootPlusProperties.class})
public class SpringBootPlusConfig {

    /**
     * 属性
     * 配置日志AOP
     *
     * @param springBootPlusProperties
     * @return
     */
    @Bean
    public LogAop logAop(SpringBootPlusProperties springBootPlusProperties) {
        LogAop logAop = new LogAop();
        logAop.setRequestLogFormat(springBootPlusProperties.isRequestLogFormat());
        logAop.setResponseLogFormat(springBootPlusProperties.isResponseLogFormat());
        log.info("init LogAop success, {}", springBootPlusProperties);
        return logAop;
    }


    /**
     * 权限拦截器
     *
     * @return
     */
    @Bean
    public PermissionInterceptor permissionInterceptor() {
        PermissionInterceptor permissionInterceptor = new PermissionInterceptor();
        return permissionInterceptor;
    }

    /**
     * csrf referer拦截器
     *
     * @return
     */
    @Bean
    public RefererInterceptor refererInterceptor() {
        RefererInterceptor refererInterceptor = new RefererInterceptor();
        return refererInterceptor;
    }


//    /**
//     * CORS跨域设置　Tododo: 后面可用此方式取代CrossDomainFilter
//     *
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean corsFilter(SpringBootPlusCorsProperties corsProperties) {
//        log.debug("corsProperties:{}", corsProperties);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        // 跨域配置
//        corsConfiguration.setAllowedOrigins(corsProperties.getAllowedOrigins());
//        corsConfiguration.setAllowedHeaders(corsProperties.getAllowedHeaders());
//        corsConfiguration.setAllowedMethods(corsProperties.getAllowedMethods());
//        corsConfiguration.setAllowCredentials(corsProperties.isAllowCredentials());
//        corsConfiguration.setExposedHeaders(corsProperties.getExposedHeaders());
//        corsConfiguration.setMaxAge(corsConfiguration.getMaxAge());
//
//        source.registerCorsConfiguration(corsProperties.getPath(), corsConfiguration);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        bean.setEnabled(corsProperties.isEnable());
//        return bean;
//    }


}
