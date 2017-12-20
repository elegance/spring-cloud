## Spring Cloud 整合 Feign

#### 准备项目

* Eureka 服务器，[spring-feign-server](./spring-feign-server)
* 服务提供者，[spring-feign-provider](./spring-feign-provider)
* 服务调用者，[spring-feign-invoker](./spring-feign-invoker)

#### 1. 调用者使用Feign

1. 加入[maven依赖](http://cloud.spring.io/spring-cloud-static/Edgware.RELEASE/single/spring-cloud.html#netflix-feign-starter)

2. 启用FeignClient，`@EnableFeignClients`

3. 编写Client 接口类：

   ```java
   @FeignClient("spring-feign-provider") // 指定服务名，来结构 IP、port ，服务实例从 eureka中取
   public interface ServiceClient {

       @GetMapping("/hello/{name}")
       public String hello(@PathVariable("name") String name);

       @GetMapping("/user")
       public InvokerApp.User getUser();
   }
   ```

   这里都是使用的spring 的注解，而不是前面讲的Feign注解，如`@RequestLine`，这是因为前面我们提到过的spring 提供了注解翻译器。

4. 测试使用：注解`@Autorewired` 来注入`ServiceClient`即可使用了，可以从浏览器看到负载均衡的效果(spring-feign-provider启动多实例)

#### 2. Feign 的默认配置

* **解码器（Decoder）: bean名称为feignDecoder，ResponseEntityDecoder类**
* **编码器（Encoder）:  bean名称为feignEncoder，SpringEncoder类**
* **日志（Logger）：bean名称为feignLogger，Slf4jLogger类**
* **注解翻译器（Contract）: bean名称为feignContract，SpringMvcContract类**
* **Feign实例的创建者（Feign.Builder）: bean名称为feignBuilder，HystrixFeign.Builder类**
* **Feign客户端（Client）: bean名称为feignClient，LoadBalancedFeignCliet类**



