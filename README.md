# graph-gateway
## 框架描述
graph-gateway 是一个基于Java语言开发的后端服务框架，使用轻量级开源框架 Springboot 基于 [nebula-java](https://github.com/vesoft-inc/nebula-java) 组件封装搭建的一个服务系统。
<br/>
gateway 意为大门，对于熟悉Java编程语言的用户，可以通过引用 graph-gateway 作为入口实现自己的服务系统研发，快速开发一个基于 [NebulaGraph](https://github.com/vesoft-inc/nebula) 的定制化图可视化平台。

## 项目要求
- Springboot
- Maven
- JDK 1.8+

## 使用示例
用户克隆代码后，配置好环境，可直接运行服务，默认启动端口是：8778。
<br/>
可通过修改配置文件修改启动端口和服务路径：
```yml
server:
  port: 8778
  servlet:
    context-path: /
```

<br/>

服务启动入口代码：
```java
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableCaching
@ComponentScan({"com.lpz.graph.gateway"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
@Slf4j
public class GraphGatewayApplication {

    public static void main(String[] args) {
        // 启动graph-gateway
        ConfigurableApplicationContext context = SpringApplication.run(GraphGatewayApplication.class, args);
        // 打印项目信息
        PrintApplicationInfo.print(context);
    }

}
```

<br/>
项目采用经典的 MVC 框架模式搭建，代码结构如下：

![图片](https://github.com/mathlpz/graph-gateway/blob/master/docs/mvc-framework.png)

<br/>
已完成图数据库访问的多个基础接口开发列表：

![图片](https://github.com/mathlpz/graph-gateway/blob/master/docs/interface-intro.png)

项目启动后，用户可通过访问地址 URL(http://localhost:8778/swagger-ui/index.html) 调试。


## 写在最后
由于本框架是由自己一人开发和维护，有些不完善的地方希望大家多提宝贵意见修改。
<br/>
大家可以下载源码在本地根据自己的需要编写修改，同时也希望有更多的朋友能够参与其中一起开发完善框架。

## License
该系统遵循开源协议 [Apache License, Version 2.0, January 2004](https://www.apache.org/licenses/LICENSE-2.0).

