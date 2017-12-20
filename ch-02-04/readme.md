## 容错框架 Hystrix

容错，避免依赖的服务发生错误时，错误蔓延，导致雪崩。通过超时、异常等容错阀值，规则，将不可用服务至为短路状态，使用回退逻辑，回退逻辑是及时返回的（类似NIO比IO的即时返回）。

* Netflix提供的一个容错框架
* 解决依赖隔离

#### 1. 准备

* [hy-service-provider](./hy-service-provider) ,普通的 `spring-boot-starter-web`程序 ，用来提供依赖的服务，模拟返回成功、失败、超时等状态的服务
* [hy-service-invoker](hy-service-invoker)，普通java应用，使用http client + Hystrix 来测试服务调用



#### 2. 使用

1. 使用http client 访问服务:

   ```java
       public static void main(String[] args) throws IOException {
           CloseableHttpClient httpClient = HttpClients.createDefault();
           HttpGet httpGet = new HttpGet("http://localhost:8080/normalHello");

         	HttpResponse response = httpClient.execute(httpGet);
         	System.out.println(EntityUtils.toString(response.getEntity()));
       }
   ```

2. 使用Hystrix 包装([github demo](https://github.com/Netflix/Hystrix))

   * 正常的command:  逻辑放入 Command - CloseableHttpClient、HttpGet放置在run外面，不算入run的时间，不然这两个对象的初始化可能会导致进入 timeout ，发生错误。

     ```java
     public class HyNormalCommand extends HystrixCommand<String> {

         CloseableHttpClient httpClient = HttpClients.createDefault();
         HttpGet httpGet = new HttpGet("http://localhost:8080/normalHello");

         public HyNormalCommand() {
             super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
         }

         @Override
         protected String run() throws Exception {
             HttpResponse response = httpClient.execute(httpGet);
             return EntityUtils.toString(response.getEntity());
         }
     }
     ```

   * 触发错误的command: 使用command包装调用会超时的服务，逻辑是一样的，只是调用地址改为了`/longTimeHello`，另外新增`getFallback`方法，在错误发生时将会自动执行这个方法来返回结果。

     ```java
     @Override
     protected String getFallback() {
       return "service unavailable.";
     }
     ```

     ​

     ​
     ​