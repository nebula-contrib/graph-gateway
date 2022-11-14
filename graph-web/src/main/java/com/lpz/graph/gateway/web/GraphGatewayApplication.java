package com.lpz.graph.gateway.web;

import com.lpz.graph.gateway.web.util.PrintApplicationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 图数据库系统启动入口
 *
 * @author lpz
 * @since 2022-03-01
 */
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableCaching
@ComponentScan({"com.lpz.graph.gateway"})
//@MapperScan({"com.lpz.graph.gateway.dao.mapper"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
//@SpringBootApplication
@Slf4j
public class GraphGatewayApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 启动graph-gateway
        ConfigurableApplicationContext context = SpringApplication.run(GraphGatewayApplication.class, args);
        // 打印项目信息
        PrintApplicationInfo.print(context);
    }

}

