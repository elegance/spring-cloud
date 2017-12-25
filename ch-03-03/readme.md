## 网关Zuul(三) 路由配置

#### 1. 简单路由

客户端请求达到网关，网关代为发送请求目标地址，请求响应经过网关再返回给客户端。

* **SimpleHostRoutingFilter**
* 配置连接池
  * zuul.host.maxTotalConnectoins：目标主机的最大连接数，默认值200。配置该项，相当于调用了`PoolingHttpClientConnectionManager.setMaxTotal()`方法
  * zuul.host.maxPerRouteConnections：每个主机的初始连接数，默认值20.配置该项，相当于调用了`PoolingHttpClientConnectionManager.setDefaultMaxPerRoute()`方法

配置示例：

```yaml
zuul:
  routes:
    # 简单路由：path + url
    route163:
      path: /163
      url: http://www.163.com
    # 简单路由，省略path，serviceId 将作为默认path
    baidu:
      url: http://www.baidu.com
```

#### 2. 跳转路由

直接 forward 到目标地址，响应不经过网关。

* **SendForwardFilter**

配置示例：

```yaml
zuul:
  routes:
    # forward : 直接的跳向目标地址，响应不经过gateway
    # 测试：http://localhost:9000/fd/
    fd:
      url: forward:/fd
```



#### 3. Ribbon 路由

通过Ribbon Discover 服务，具有负载均衡功能。

配置示例：

```yaml
zuul:
  routes:
    #Ribbon 路由：serviceId + path
    #测试：http://localhost:9000/invoker/call
    invoker-service:
      serviceId: spring-zuul-invoker
      path: /invoker/**
    # Ribbon 路由，省略显示配置 serviceId
    # 测试：http://localhost:9000/service/hello/jim
    spring-zuul-service:
      path: /service/**
```



#### 4. 自定义路由

容器返回Bean `PatternServiceRouterMapper`，使用正则表达式，命名捕获来设定处理url。

代码示例：(可替代3中Ribbon路由的两个配置效果)：

```java
@Configuration
public class RouteConfigure {

    @Bean
    public PatternServiceRouteMapper patternServiceRouteMapper() {
        // 访问网关：/xxx/** ，将会被路由到：spring-zuul-xxx
        // 理由 正则命名捕获，比如项目服务约定为：spring-zuul-xxModule，最后部分为模块部分，处理 /xxModule/请求
        System.out.println("初始化自定义路由。。。");
        return new PatternServiceRouteMapper("spring-zuul-(?<module>.+)", "${module}/**");
    }
}
```



#### 5. 排除服务，不被路由到

```yaml
zuul:
  ignored-services: spring-zuul-service
  ignored-patterns: /service/**
```



#### 6. 请求头配置

如果想将一些请求头（敏感请求头sensitive.headers）不传递到原服务，可以有以下选项

```yaml
zuul:
  sensitive-headers: Cookie
```

#### 7. 路由端点

* Actuator 依赖
* management.security.enable  设为false来 测试访问：http://locahost:9000/routes