
package com.lpz.graph.gateway.web.config;

import com.lpz.graph.gateway.web.common.filter.CrossDomainFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;


/**
 */
@Configuration
public class FilterConfig {

    /**
     * @return
     */
    @Bean
    public Filter crossDomainFilter() {
        return new CrossDomainFilter();
    }


    /**
     * @return
     */
//    @Bean
//    public Filter xssFilter() {
//        return new XssFilter();
//    }

    /**
     * @return
     */
//    @Bean
//    @Order(1)
//    public FilterRegistrationBean requestPathFilterBean() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(requestPathFilter());
//        registration.addUrlPatterns("/*");
//        return registration;
//    }

    /**
     * @return
     */
    @Bean
    @Order(2)
    public FilterRegistrationBean crossDomainFilterBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(crossDomainFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

//    /**
//     * xss过滤拦截器
//     */
//    @Bean
//    @Order(3)
//    public FilterRegistrationBean xssFilterRegistrationBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(xssFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        Map<String, String> initParameters = Maps.newHashMap();
//        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*,/static/*,/webjars/*,/swagger-resources/*");
//        initParameters.put("isIncludeRichText", "true");
//        filterRegistrationBean.setInitParameters(initParameters);
//        return filterRegistrationBean;
//    }

}
