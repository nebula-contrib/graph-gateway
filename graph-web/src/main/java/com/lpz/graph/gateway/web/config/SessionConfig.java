//package com.lpz.graph.gateway.web.config;
//
//import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.session.MapSessionRepository;
//import org.springframework.session.SessionRepository;
//import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
//import org.springframework.session.web.http.DefaultCookieSerializer;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//
///**
// * 解决cookie HttpOnly 等问题？ 但是 导致每次request的session都会变
// */
//@Configuration
////@EnableSpringHttpSession
//public class SessionConfig {
//
//    /**
//     * @return
//     */
//    @Bean
//    public SessionRepository sessionRepository() {
//        return new MapSessionRepository(new ConcurrentHashMap<>());
//    }
//
//    /**
//     * @return
//     */
//    @Bean
//    DefaultCookieSerializerCustomizer cookieSerializerCustomizer() {
//
//        return new DefaultCookieSerializerCustomizer() {
//            @Override
//            public void customize(DefaultCookieSerializer cookieSerializer) {
//                cookieSerializer.setUseHttpOnlyCookie(true);
//                cookieSerializer.setSameSite("None");
//                cookieSerializer.setUseSecureCookie(true); // 此项必须，否则set-cookie会被chrome浏览器阻拦
//            }
//        };
//    }
//}