## Spring 中使用Ribbon

#### 准备工作

* 搭建Eureka服务，[eureka-server](./eureka-server)
* 服务提供者，[service-provider](./service-provider-108)
* 服务调用者，[lb-invoker-client](./lb-invoker-client-108)

#### 1. 客户端使用 `@LoadBalanced`注解实现负载均衡效果

```java
@SpringBootApplication
@RestController
@Configuration
public class InvokeApp {

    public static void main(String[] args) {
        SpringApplication.run(InvokeApp.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/router")
    public String callService() {
        // restTemplate 会使用默认的轮询规则 来访问具体的服务
        String json = restTemplate.getForObject("http://service-provider/user", String.class);
        return json;
    }
}
```

#### 2. 在客户端使用自定义的规则实现负载均衡

* 新建`MyRule.java` 自定义规则实现类，

* 新建配置类`MyConfig.java`,  标识为Spring Bean

  ```java
  @Bean
  public IRule myRule() {
    return new MyRule();
  }
  ```

  ​

此时，有以下方式可以让自定义规则生效：

* 第一种：在`MyConfig.java`类上直接加上 `@Configuration`注解，让上面的Bean直接的加入Spring容器，注意此时是全局有效的，即`@LoadBalanced`发出的请求会从容器中寻找`IRule`的实现作为默认规则。


* 第二种：只对具体的服务生效，新建`MyClient.java`，并使用`@RibbonClient(name = "service-provider", configuration = MyConfig.class)`注解修饰，其中`name`是我们要访问的服务，`configuration`为配置类，只不过此处的配置类不要`@Configuration`自动装载，而是交由`@RibbonClient`来触发装载。

  ​

另外我们可以在`application.yaml`文件中使用 [`ribbon` 配置](https://github.com/Netflix/ribbon/wiki/Working-with-load-balancers#components-of-load-balancer)的方式使自定义规则生效

```yaml
service-provider:
  ribbon:
    NFLoadBalancerRuleClassName: org.orh.spring.cloud.ch108.MyRule
```

