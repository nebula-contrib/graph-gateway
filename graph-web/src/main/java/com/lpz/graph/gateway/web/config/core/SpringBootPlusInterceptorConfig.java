package com.lpz.graph.gateway.web.config.core;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 */
@Data
@Accessors(chain = true)
public class SpringBootPlusInterceptorConfig {

    /**
     * JWT拦截器排除路径
     */
    private InterceptorConfig jwtConfig;

    /**
     * TOKEN超时拦截器排除路径
     */
    private InterceptorConfig tokenTimeoutConfig;

    /**
     * 权限拦截器排除路径
     */
    private InterceptorConfig permissionConfig;

    /**
     * 资源拦截器
     */
    private InterceptorConfig resourceConfig;

    /**
     *
     */
    @Data
    public static class InterceptorConfig {

        /**
         * 排除路径
         */
        private String[] excludePath;

        /**
         * 包含的路径
         */
        private String[] includePath;

    }

}
