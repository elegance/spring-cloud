## RestTemplate 负载均衡原理

`restTemplate`是一个普通的http client，使用`@LoadBalanced`就可以让其具有负载均衡的原理得益于`restTemplate`的拦截机制。

#### 知识点小记

* 注解 `@Autowired` 组合  `@MyLoadBalanced` 在 `List<RestTemplate> templates` 时，能将带有`@MyLoadBalanced`注解的`RestTemplate`注入到`templates`集合中(注解中 `@Qualifier)`
* **`SmartInitializingSingleton`** 所有非lazy单例Bean实例化完成后的回调方法。

#### 模拟实现 `@LoadBalanced`注解

1. 定义注解类`MyLoadBalanced`：

   ```java
   @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
   @Retention(RetentionPolicy.RUNTIME)
   @Qualifier
   public @interface MyLoadBalanced {
   }
   ```

   ​

2. 给修饰了`MyLoadBalanced`的`RestTmplate`添加拦截器

   ```java
   @SpringBootApplication
   @Configuration
   @RestController
   public class LoadBalancedApp {
       private Logger logger = LoggerFactory.getLogger(getClass());

       public static void main(String[] args) {
           SpringApplication.run(LoadBalancedApp.class, args);
       }

       @Autowired
       private RestTemplate restTemplateA;

       @GetMapping("/call")
       public String call() {
           return restTemplateA.getForObject("http://custom-service/call", String.class);
       }

       @GetMapping("/hello")
       public String hello() {
           return "hello";
       }

       // 会自动注入 带有 @MyLoadBalanced 注解的 Bean 到 templates 集合
       @Autowired(required = false)
       @MyLoadBalanced
       private List<RestTemplate> templates;

       @Autowired
       private MyHttpRequestInterceptor interceptor;

       // 容器启动初始化时，执行下面方法 给 template中的 实例添加 拦截器
       @Bean
       public SmartInitializingSingleton lbTemplateInitializing() {
           return new SmartInitializingSingleton() {
               @Override
               public void afterSingletonsInstantiated() {
                   logger.info("@MyLoadBalanced修饰的Template有{}个", templates.size());
                   for (RestTemplate tpl : templates) {
                       tpl.getInterceptors().add(interceptor);
                   }
               }
           };
       }

       @Bean
       @MyLoadBalanced
       public RestTemplate restTemplateA() {
           return new RestTemplate();
       }

       @Bean
       @MyLoadBalanced
       public RestTemplate restTemplateB() {
           return new RestTemplate();
       }
   }
   ```

3. `ClientRequestInterceptor`的实现：`MyHttpRequestInterceptor.java`:

   ```java
   /**
    * 实现对-client 端发出的 http request 进行拦截，模拟解析服务名到具体的HostPort
    */
   @Component
   public class MyHttpRequestInterceptor implements ClientHttpRequestInterceptor {

       private Logger logger = LoggerFactory.getLogger(getClass());

       @Override
       public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
           logger.info("==========http request 进入自定义拦截器");
           logger.info("请求的原URI: {}", request.getURI());

           HttpRequest newRequest = new LoadBalancedRequest(request);
           logger.info("请求的新URI: {}", newRequest.getURI());

           return execution.execute(new LoadBalancedRequest(newRequest), body);
       }


       /**
        * 解析 URI 中的服务名解析出来
        */
       class LoadBalancedRequest implements HttpRequest {
           private HttpRequest sourceRequest;

           public LoadBalancedRequest(HttpRequest request) {
               this.sourceRequest = request;
           }

           @Override
           public HttpMethod getMethod() {
               return sourceRequest.getMethod();
           }

           @Override
           public URI getURI() {

               URI newUri = null;
               try {
                   // 这里就固定访问：localhost 了， 实际的实现应该是 截取出服务名，通过 Eureka api 得到服务列表，来替换uri
                   newUri = new URI("http://localhost:8080/hello");
               } catch (URISyntaxException e) {
                   throw new RuntimeException("URI 地址错误，", e);
               }
               return newUri;
           }

           @Override
           public HttpHeaders getHeaders() {
               return sourceRequest.getHeaders();
           }
       }
   }
   ```

   ​

启动项目，访问：`http://127.0.0.1:8080/call/` 则能拿到 `/hello`返回的内容。