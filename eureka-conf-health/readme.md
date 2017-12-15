## Eureka常用配置与健康检测

service-provider注册到Eureka server上后，如果service-provider宕机，默认在Eureka server的服务列表中还是有service-provider是注册信息的。

#### 1. 常用配置

1. Eureka-server配置

   ```properties
   eureka.server.enable-self-preservation	# 设为false，关闭自我保护
   eureka.server.eviction-interval-timer-in-ms # 清理间隔（单位毫秒，默认是60*1000）
   ```

2. Eureka client-服务提供者配置

   ```properties
   eureka.instance.lease-renewal-interval-in-seconds	# 续约更新时间间隔（默认30秒）
   eureka.instance.lease-expiration-duration-in-seconds # 续约到期时间（默认90秒）
   ```

#### 2. 关于“自我保护模式”

`eureka.server.enable-self-preservation`是否开启自我保护模式，默认为`true`。

在不考虑`保护模式`的情况下，如果Eureka Server在一定时间内没有接收到某个微服务实例的心跳，Eureka Server将会注销该实例（默认90秒）。但是当网络分区故障发生时，微服务与Eureka Server之间无法正常通信，以上行为可能变得非常危险了——因为微服务本身其实是健康的，此时本不应该注销这个微服务。

Eureka通过“自我保护模式”来解决这个问题——当Eureka Server节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。一旦进入该模式，Eureka Server就会保护服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）。当网络故障恢复后，该Eureka Server节点会自动退出自我保护模式。

自我保护模式是一种应对网络异常的安全保护措施。它的架构哲学是宁可同时保留所有微服务（健康的微服务和不健康的微服务都会保留），也不盲目注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮、稳定。

#### 3. 示例配置

server:

```yaml
server:
  port: 8761
spring:
  application:
    name: eureka-server
eureka:
  client:
    # 单节点测试用，不抓取，不注册
    fetch-registry: false
    register-with-eureka: false
  server:
    # 关闭自我保护模式
    enable-self-preservation: false
    # 执行清理列表的工作(清理已经失效的任务)，设定为5s，注意此为演示使用
    eviction-interval-timer-in-ms: 5000
```

service-provider:

```yaml
spring:
  application:
    name: service-provider
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    # 10s 抓取一次服务列表
    registry-fetch-interval-seconds: 10
  instance:
    # 5s 发送一个续约更新心跳包（演示使用）
    lease-renewal-interval-in-seconds: 5
    # 服务多久没有心跳包判断其为不可用
    # 太长有可能导致流量路由到不存活的实例
    # 太短有可能导致，短暂的网络故障，该实例可能被取消流量
    lease-expiration-duration-in-seconds: 10
```

## 结合spring-boot-starter-actuator 

#### 1. 添加如下依赖：

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

可以访问：`http://host:port/health` 查看健康状态了。

#### 2. 自定义Health指示器

在`Controller`中增加变量，我们通过新增接口对这个变量的修改配合自定义的健康指示器`MyHealthIndicator`，来切换服务端健康状况。

```java
public volatile static boolean dbCanUse = true;
@GetMapping("/db/{can}")
public void setDbCanUse(@PathVariable boolean can) {
        dbCanUse = can;
}
```

```java
/**
 * 自定义健康指示器
 */
@Component
public class MyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if (ServiceProviderApp.dbCanUse) {
            return new Health.Builder(Status.UP).build();
        }
        return new Health.Builder(Status.DOWN).build();
    }
}
```

通过浏览器访问: `http://localhost:8080/db/false` 设定健康状态为`DOWN`

#### 3. 自定义Health检查处理器

光有上面的`健康简称器`来检测服务状体是否成功还不行，`Eureka client`还需要利用这个指示器来检查Health的，所以需要做一个适配，建立自定义的健康检查处理器，`Eureka client`会根据你的配置定时（默认30s）来执行这个检查。

```java
/**
 * 自定义健康检查器
 */
@Component
public class MyHealthCheckHandler implements HealthCheckHandler {

    @Autowired
    private MyHealthIndicator healthIndicator;

    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus instanceStatus) {
        if (healthIndicator.health().getStatus().equals(Status.DOWN)) {
            return InstanceInfo.InstanceStatus.DOWN;
        }
        return InstanceInfo.InstanceStatus.UP;
    }
}
```

这样在：`http://localhost:8761/` 中我们可以观察，通过`http://localhost:8080/db/false`，将应用状态设定为`false`,  等待一定时间(具体看配置)刷新，可以看到服务处于`DOWN`状态，通过`http://localhost:8080/db/true`，等待一定时间刷新，可以看到服务恢复`UP`状态。