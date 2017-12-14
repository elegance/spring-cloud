## 项目简介
用114服务来类比理解Eureka

* [eureka-114-server](eureka-114-server) 114 作为Eureka 服务，提供服务列表
* [eureka-police-client](eureka-police-client) 警察局，作为Eureka 客户端，在服务商注册服务，提供服务调用
* [eureka-person-client](eureka-police-client) 个人，使用Eureka 客户端，在服务上查询服务，调用调用

#### 注意事项
* `eureka-114-server` 作为Eureka 服务时，禁用以下Client的行为配置
```yaml
eureka:
  client:
    # server 启动时，不需要以下 client 类的操作
    # 不要把自己当做 client 取注册
    register-with-eureka: false
    # 不要去抓取注册信息
    fetch-registry: false
```
启动浏览器：http://127.0.0.1:8761

* `eureka-police-client` 服务提供者，注意提供应用名称
```yaml
spring:
  application:
    name: eureka-police-client
```

* `eureka-person-client` 最终用户，需要去服务注册中心，依赖`Robbin`客户端负载均衡