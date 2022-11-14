package com.lpz.graph.gateway.web.util;

import lombok.extern.slf4j.Slf4j;
import org.fusesource.jansi.Ansi;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * <p>
 * 打印项目信息
 * </p>
 **/
@Slf4j
public class PrintApplicationInfo {


    /**
     * 执行之前，打印前置条件提示
     */
    public static void printTip() {
        StringBuffer tip = new StringBuffer();
        tip.append("======================================================================================\n");
        tip.append("                                                                                  \n");
        tip.append("                               !!!准备工作!!!                                      \n");
        tip.append("                                                                                  \n");
        tip.append("======================================================================================\n");
        log.info("\n{}", Ansi.ansi().eraseScreen().fg(Ansi.Color.YELLOW).a(tip.toString()).reset().toString());
    }

    /**
     * 启动成功之后，打印项目信息
     */
    public static void print(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();

        // 项目路径
        String contextPath = environment.getProperty("server.servlet.context-path");
        // 项目端口
        String port = environment.getProperty("server.port");
        // 项目环境
        String active = environment.getProperty("spring.profiles.active");

        log.info("contextPath : {}", contextPath);
        log.info("port : {}", port);
        log.info("profilesActive : {}", active);


        String swaggerUrl = "http://" + IpUtil.getLocalhostIp() + ":" + port + contextPath + "swagger-ui/index.html";
        log.info("swagger docs : {}", swaggerUrl);
        log.info("graph-gataway project start success...........");
        log.info("{}", AnsiUtil.getAnsi(Ansi.Color.BLUE, "start success!"));
    }

}
