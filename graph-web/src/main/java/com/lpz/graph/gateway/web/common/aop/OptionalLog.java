package com.lpz.graph.gateway.web.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志记录
 *
 * @author lpz
 */
@Documented
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalLog {

    /**
     * 日志模块
     *
     * @return
     */
    String module() default "";

    /**
     * 记录日志的操作类型，日志说明
     */
    String operateType() default "";

}