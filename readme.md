## Spring Cloud 介绍与环境搭建

#### 架构演进：

* 单体应用：项目变大，启动、研发耗时，服务无法区别对待，模块影响
* SOA：面向服务架构，分模块，如销售模块、交易模块、库存模块，调用方式 通过企业服务总线（ESB ） 调用webService。
* 微服务：服务细化，rest 调用

#### Spring Cloud
* 是一个工具箱
* 基于Spring Boot，封装了 Netflix 的框架
* 将 Netflix 与 Spring 容器进行整合

#### Spring Cloud 整合的Netflix框架
* Eureka: 基于REST服务的分布式中间件，主要用于服务管理（服务注册、服务发现）。
* Hystrix: 容错框架，通过添加延迟阀值以及容错的逻辑，帮助控制分布式系统组件间的交互，多次失败熔断保护等。
* Feign: 一个REST客户端，目的是为了简化Web Service客户端的开发
* Ribbon: 负载均衡框架
* Zuul: 服务网关，提供代理、过滤、路由等功能