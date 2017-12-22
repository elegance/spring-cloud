## Spring Cloud 整合Hystrix （三） 整合Feign

前面我们使用Hystrix 都是使用的RestTemplate，下面来看下怎么整合Feign，通过 Client接口的形式来访问服务。

项目基于[分支2-09](https://github.com/elegance/spring-cloud/tree/2-09/ch-02-09)，以下：

* [spring-hy-server-3](spring-hy-server-3) , Eureka 服务


* [spring-hy-member-3](spring-hy-member-3)，会员模块
* [spring-hy-sale-3](spring-hy-sale-3)，销售模块，会依赖调用会员模块，加入`hystrix`保护调用链路
* [spring-hy-dashboard](spring-hy-dashboard) hystrix 仪表板，监控 hystrix 项目的调用情况

#### 1. Feign 整合Hystrix

* 添加依赖，`spring-cloud-starter-openfeign`, [Spring Cloud 官方文档](http://cloud.spring.io/spring-cloud-static/Edgware.RELEASE/single/spring-cloud.html#netflix-feign-starter)

* 编写接口与实现回退
  如：
  服务接口：

  ```java
  FeignClient(value = "spring-hy-member", fallback = MemberClientFallback.class)
  public interface MemberClient {...}
  ```

  回退类：

  ```java
  @Component
  public class MemberClientFallback implements MemberClient {
  ```

  ​

* 启动类`@EnableFeignClients`, 配置开启：`feign.hystrix.enable=true`

启动三个项目，访问：`http://localhost:8084/hello`, 测试hystrix 是否生效，然后关闭`spring-hy-member-3`看是否执行 fall back.

测试断路器：

* 配置指定方法的断路器开启条件：**限定时间**里的**限定请求次数**，**有限定百分比**的失败

  ```yaml
  hystrix:
    command:
      # 给指定client的 指定方法设置 hystrix 参数
      # 如果需要对全局设定，将以下一行改为 default即可
      MemberClient#hello():
        execution:
          isolation:
            thread:
              # 500ms认定为超时
              timeoutInMilliseconds: 500
        circuitBreaker:
              # 请求阀值 数为3，即 10s内有3个请求，则会计数 失败超过50%
          requestVolumeThreshold: 3
  ```

#### 2. Hystrix 监控

* 需监控项目(这里是)添加，`spring-boot-starter-actuator`依赖
* 新建`spring-hy-dashboard`监控项目，依赖：
  * ~~spring-cloud-starter-hystrix-netflix-dashboard~~ ([官方文档](http://cloud.spring.io/spring-cloud-static/Edgware.RELEASE/single/spring-cloud.html#netflix-hystrix-dashboard-starter)好像有误) `spring-cloud-starter-hystrix-dashboard`

被监控项目的ping: http://ip:port/hystrix.stream

hystrix 监控：http://ip:port/hystrix