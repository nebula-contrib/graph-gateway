
package com.lpz.graph.gateway.web.config;

import com.lpz.graph.gateway.web.common.interceptor.PermissionInterceptor;
import com.lpz.graph.gateway.web.config.core.CsrfSecurityRequestMatcher;
import com.lpz.graph.gateway.web.config.core.SpringBootPlusProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private SpringBootPlusProperties springBootPlusProperties;

    @Autowired
    private PermissionInterceptor permissionInterceptor;

//    @Autowired
//    private RefererInterceptor refererInterceptor;

    // 添加视图控制器配置默认的欢迎页
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // /**： 匹配所有路径
        // Referer拦截器
//        registry.addInterceptor(refererInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(springBootPlusProperties.getInterceptorConfig()
//                        .getPermissionConfig().getExcludePath());
        // 权限拦截器注册
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(springBootPlusProperties.getInterceptorConfig()
                        .getPermissionConfig().getExcludePath());

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 设置项目静态资源访问
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 设置swagger静态资源访问
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        // 设置上传文件访问路径
        registry.addResourceHandler(springBootPlusProperties.getResourceAccessPatterns())
                .addResourceLocations("file:" + springBootPlusProperties.getUploadPath());
    }

    /**
     * 允许跨域访问
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST")
                .maxAge(3600);
    }


    /**
     * @return
     */
    @Bean
    public CsrfSecurityRequestMatcher csrfSecurityRequestMatcher() {
        return new CsrfSecurityRequestMatcher();
    }

    /**
     * @return
     */
    @Bean
    public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        return new CookieCsrfTokenRepository();
    }

    /**
     * @return
     */
    @Bean
    public HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

}
