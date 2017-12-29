## 配置中心(四) 常用配置

源码：基于[ch-04-02](../ch-04-02])

#### 1. 服务健康检测

* 配置服务-Config Server 默认会去检查仓库的 `uri`，以此来标识服务的`health`状态为`UP/DONW`
* 检查某个目录是否存在，配置`spring.cloud.config.server.health.repositories.test-`，不存在则会`DOWN`

#### [2. 客户端错误提前与重试机制](http://cloud.spring.io/spring-cloud-static/Edgware.RELEASE/single/spring-cloud.html#config-client-retry)

客户端默认是会去访问配置的配置服务地址的，如`http://localhost:8888`，默认情况下如果服务不可用，客户端是可以启动的，这可能会埋下一些风险。如果你的应用是明确需要依赖服务端配置的，建议配置错误错误提前`spring.cloud.config.failFast=true`。

这样配置时，当应用访问配置服务，服务不可用时，应用启动将会失败，为了避免因为网络抖动的原因不可访问服务，可以配置`重试机制`来多尝试几次连接服务。重试机制首先需要以下的两个依赖：

* `org.springframework.retry/spring-retry`
* `org.springframework.boot/spring-boot-starter-aop`

有以下选项可配置：

```yaml
spring:
  cloud:
    config:
      retry:
        initial-interval: 1000 # 初始间隔
        max-interval: 2000 # 最大间隔
        multiplier: 1.1 #递增系数
        max-attempts: 6 #最大尝试次数
```



#### 3. 安全配置

前面开发，Config server关闭认证，客户端直接连接的，实际开发中肯定需要开启认证。

##### 配置服务-Config Server的配置：

* 增加 `spring-boot-starter-security`依赖

* 配置访问的用户名、密码

  ```yaml
  security:
    user:
      name: user
      password: 123456
  ```

再次访问：`http://localhost:8888/application-dev.properties`则需要用户名密码了。

##### 客户端配置：

* 配置`bootstrap.yml`:

  ```yaml
  spring:
    cloud:
      config:
        username: user
        password: 123456
  ```

  ​