package com.lpz.graph.gateway.web.common.aop;

import com.lpz.graph.gateway.common.constant.SessionConstants;
import com.lpz.graph.gateway.common.util.DateUtil;
import com.lpz.graph.gateway.common.util.StringUtil;
import com.lpz.graph.gateway.web.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 切面日志
 *
 * @author lpz
 */
@Slf4j
@Aspect
@Component
public class OptionalLogAop {

    /**
     * 配置接入点，即为所要记录的action操作目录
     */
    @Pointcut("execution(* com.lpz.graph.gateway.web.controller..*.*(..))")
    private void controllerAspect() {
    }

    /**
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 拦截的放参数类型
        Signature sign = joinPoint.getSignature();
        if (!(sign instanceof MethodSignature)) {
            log.error("该注解只能用于方法， OptionalLog only use for method... {}", sign);
//            throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method currentMethod = methodSignature.getMethod();
        // 获取方法（此为自定义注解）
        OptionalLog annot = currentMethod.getAnnotation(OptionalLog.class);
        if (Objects.isNull(annot)) {
            return joinPoint.proceed();
        }

        // 拦截的方法名称。当前正在执行的方法
        String methodName = methodSignature.getName();
        //
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        // 从session获取用户名
        String userName = (String) request.getSession().getAttribute(SessionConstants.SESSION_KEY_USERNAME);
        // 获取系统当前时间
        String recordTime = DateUtil.formatDateToStr(new Date(), DateUtil.FORMATSTR_YYYY_MM_DD_HHMMSS);
        // 获取访问真实IP
        String ipAddress = IpUtil.getRequestIp();
        //获取请求路径
        String requestURI = request.getRequestURI();
        // 获取注解的modules 设为操作模块， 设为执行方法
        String module = annot.module();
        String operateType = annot.operateType();

        //接受客户端的数据
        StringBuilder paramsBuild = new StringBuilder();
        Map<String, String[]> maps = request.getParameterMap();
        maps.entrySet().forEach(entry -> {
            String[] strings = entry.getValue();
            for (String string : strings) {
                paramsBuild.append(entry.getKey()).append("-").append(string).append(",");
            }
        });
        String params = paramsBuild.toString();
        if (StringUtil.isNotBlank(params) && params.length() > 0) {
            params = params.substring(0, params.length() - 1);
        }
        Object[] arguments = joinPoint.getArgs();
        for (Object argument : arguments) {
            System.out.println(argument);
        }

        // 执行目标方法，获得返回值。response
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            log.error("处理响应结果异常response exception， {}", e.getMessage(), e);
        } finally {
            log.info("OptionalLog record... userName:{}, requestURI:{}, params:{}, module:{}, operateType:{}, ip:{}, result:{}, recordTime:{}",
                    userName, requestURI, params, module, operateType, ipAddress, result, recordTime);
        }

        return result;
    }

}