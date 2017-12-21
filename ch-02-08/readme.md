## Spring Cloud 整合 Hystrix （一）

[Spring Cloud Hystrix 整合-官方文档](http://cloud.spring.io/spring-cloud-static/Edgware.RELEASE/single/spring-cloud.html#netflix-hystrix-starter)

#### 准备项目

* [spring-hy-server](spring-hy-server): Eureka 服务


* [spring-hy-sale](spring-hy-sale): 销售模块，依赖会员模块，即在此项目中整合 Hystrix，因为其依赖的服务有可能down
* [spring-hy-member](spring-hy-member): 会员模块

#### 1. spring-hy-sale 使用 Hystrix 

* 添加依赖: `spring-cloud-starter-netflix-hystrix`
* 启用断路器：`@EnableCircuitBreaker`
* 编写`MemberService`，新建方法利用`RestTemplate`调用 会员模块的查询会员方法，记得此处的`RestTemplate`需要添加`@LoadBalanced`,因为是通过服务名调用。
* 给`MemberService`中的方法添加`@HystrixCommand(fallback = "methodName")`注解，即该方法有了`Hystrix`的保护

测试：

* **正常执行**：测试访问：`http://127.0.0.1:8084/router/1`
* **`hystrix`生效**：停掉`spring-hy-member`会员模块，再次访问，我们会发现等待约`1s`中返回`fallback`方法中的内容
* **触发`CircuitBreak`断路器开启**：连续快速多次访问(默认10秒内20次，50%失败)可以使，后面的访问会立即返回`fallback`内容。
* **测试默认5s休眠期**：`5s`内请求都是立即返回`fallback内容`，不会等待，`5s`后的第一个请求大约会有`1s`等待，其后再次`5s`内立即返回`fallback内容`
* **恢复正常，触发断路器关闭**：启动`spring-hy-member`,再次访问地址

#### 2. Hystrix 注解的配置

服务类修饰：

```java
@DefaultProperties(defaultFallback = "getMemberFallback")
public class MemberService {
```



服务具体方法修饰:

```java
@HystrixCommand(fallbackMethod = "getMemberFallback", groupKey = "MemberGroup", commandKey = "MemberCommand",
            commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "200")
            },
            threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "2")
            }
    )
public Member getMember(long id)  {
  ....
```

