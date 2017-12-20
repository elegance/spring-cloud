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

  ​

1. 配置注解翻译器
   使用前面章节实现的`@MyUrl`与`MyConstract`配置注解翻译器

   * 将`MyConstract`继承`SpringMvcContract`让其既能支持spring 的注解，也支持自定义注解：

     ```java
     public class MyContract extends SpringMvcContract {

         @Override
         protected void processAnnotationOnMethod(MethodMetadata methodMetadata, Annotation annotation, Method method) {
             super.processAnnotationOnMethod(methodMetadata, annotation, method);
             
             if (annotation.annotationType().isAssignableFrom(MyUrl.class)) {
                 MyUrl myUrl = method.getDeclaredAnnotation(MyUrl.class);
                 String httpMethod = myUrl.method();
                 String uri = myUrl.uri();

                 methodMetadata.template()
                         .method(httpMethod)
                         .append(uri);
             }
         }
     }
     ```

   * 新建配置类，让Spring 容器知道这个 注解翻译器的存在

     ```java
     @Configuration
     public class MyContractConfig {

         @Bean
         public Contract myContract() {
             return new MyContract();
         }
     }
     ```

   * 测试：在服务接口中使用自定义的注解，暴露服务访问是否成功

#### 3. 可选配置

* Logger.Level：接口日志的记录级别，相当于调用了Feign.Builder().logLevel 方法
* Retryer：重试处理器
* ErrorDecoder：异常解码器
* RequestOptioins：设置请求的配置项
* Collection<RequestInterceptor>：设置请求拦截器

#### 4. 压缩配置

* feign.compression.request.enabled
* feign.compression.response.enabled
* feign.compression.request.mime-types
* feign.compression.request.min-request-size

