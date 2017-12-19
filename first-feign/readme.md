## Feign介绍

* Feign是一种声明式、模板化的HTTP客户端
* 被集成到Spring Cloud Netflix 模块
* 它是Github上的一个开源项目

#### 编写Feign客户端

1. 定义访问接口

   ```java
   public interface HelloClient {

       @RequestLine("GET /hello")
       String hello();

       @RequestLine("GET /user/{id}")
       User getUser(@Param("id") int id);
   }
   ```

2. 使用main方法测试

   ```java
   public class FeignMain {

       public static void main(String[] args) {
           HelloClient client = Feign.builder()
                   .decoder(new GsonDecoder()) // json 解码
                   .target(HelloClient.class, "http://localhost:8080");
           System.out.println(client.hello()); // 访问hello 接口

           User user = client.getUser(2); // 访问getUser 接口，自动解码json到user
           System.out.println(user);
       }
   }
   ```

   ​

