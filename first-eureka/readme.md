## Eureka 集群 
(基于 branch: 1-03)

#### 1. Eureka 服务集群

基本原理：启动多个Eureka服务（不同端口）, Eureka服务节点相互注册，需要增加如下配置：

```yaml
spring:
  application:
    name: eureka-server
---
spring:
  profiles: slave1
server:
  port: 8761
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8762/eureka
---
spring:
  profiles: slave2
server:
  port: 8762
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```
有了上面配置，我们可以指定程序参数分别为：`--spring.profiles.active=slave1`、`--spring.profiles.active=slave2` 来启动两个Eureka 服务，当然你可以从命令行、或者控制台接收参数来指定profile。
* 先启动的Eureka服务节点会不断尝试注册到另一个节点上，此时报错是正常的，等另一个Eureka服务节点启动后，注册成功过，便不会再有错误


#### 2. 服务提供者(Eureka client)-启动多实例，注册到Eureka集群

注册地址修改为：

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/, http://localhost:8762/eureka/
```

使用`--server.port=9091`、`--server.port=9092`启动两个实例，将会注册到Eureka集群。

#### 3. 修改使用者的服务发现地址，同上

修改后启动，在请求`http://localhost:8082/callPlice` 可以发现每次调用在9091/9092间轮询

---

## 项目简介(branch: 1-03)
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