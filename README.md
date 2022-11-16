# graph-gateway
## 框架描述
graph-gateway 是一套基于Java语言开发的后端服务系统，使用轻量级开源框架 Springboot 基于 [nebula-java](https://github.com/vesoft-inc/nebula-java) 组件封装搭建的一套服务系统。
gateway 意为大门，对于熟悉Java编程语言的朋友，可以通过使用 graph-gateway 作为入口实现自己服务系统的基础框架搭建，快速开发一个基于 [NebulaGraph](https://github.com/vesoft-inc/nebula) 的定制化图可视化平台，像对接关系型数据库一样玩转 Nebula 图数据库。

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

## 框架介绍
该项目采用经典的 MVC 框架模式搭建，代码结构简洁明了：

![图片](https://github.com/mathlpz/graph-gateway/blob/master/docs/mvc-framework.png)

## 接口说明
graph-gateway 已完成对接图数据库访问的多个基础接口开发，并集成 Swagger 组件通过 Restful 风格展示 API 文档列表：

| 接口名称       | 接口地址              | 请求    | 接口说明                | 
|---------------|-----------------------|--------|-------------------------|
| connect       | /api/db/connect       | POST   | 数据库连接               |
| exec          | /api/db/exec          | POST   | 数据库nGql执行           |
| disconnect    | /api/db/disconnect    | POST   | 数据库断开连接           |
| initializeGql | /api/db/initializeGql | POST   | 随机查询N个节点子图数据   |
| neighborhood  | /api/db/neighborhood  | POST   | 查询多个节点共同邻居      |
| displayNames  | /api/db/displayNames  | POST   | 获取tag自定义展示名称     |

![图片](https://github.com/mathlpz/graph-gateway/blob/master/docs/interface-intro.png)

项目启动后，开发者用户可通过访问服务器地址，如 URL(http://localhost:8778/swagger-ui/index.html) 调试接口，熟悉功能。

## 版本说明
graph-gateway 目前版本是基于 [nebula-java](https://github.com/vesoft-inc/nebula-java) v2.6.2版本开发调试的，如果需要对接其他版本，可直接升级pom文件中 nebula-java 相应的版本号来开发：
```xml
    <properties>
        <graph-client.version>2.6.2</graph-client.version>

        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
```

## 写在最后
由于该框架是由本人独自开发维护，有不完善的地方希望多提宝贵意见修改。大家可以下载源码后根据自己的需求编写修改，同时也希望有更多的朋友能够参与其中一起开发完善。

## License
该系统遵循开源协议 [Apache License, Version 2.0, January 2004](https://www.apache.org/licenses/LICENSE-2.0).

