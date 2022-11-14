package com.lpz.graph.gateway.web.config;

import com.lpz.graph.gateway.web.config.core.CsrfSecurityRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * Spring Security
 *
 * @Author: lpz
 * @Date: 2019-09-20 13:00
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CsrfSecurityRequestMatcher csrfSecurityRequestMatcher;

    @Autowired
    private HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //临时注释
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
//                .csrf().disable() //关闭CSRF
                .csrf().requireCsrfProtectionMatcher(csrfSecurityRequestMatcher)
                .csrfTokenRepository(httpSessionCsrfTokenRepository)
        ;

//        http.csrf().disable();
    }

    /**
     * 配置一个userDetailsService Bean不在生成默认security.user用户
     *
     * @return
     */
    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

}
