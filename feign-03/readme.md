## Feign 的注解翻译器

Feign不限定使用固定的注解，如`@RequestLine`,它提供了“注解翻译器”，我们使用其他的注解方式比如`jaxrs`方式的注解， 也可以实现自己的注解翻译器-继承`BaseContract`，将已有的注解和Feign适配上。

#### 1. 使用 jaxrs  规范注解

```xml
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-jaxrs</artifactId>
    <version>9.5.1</version>
</dependency>
<dependency>
    <groupId>javax.ws.rs</groupId>
    <artifactId>jsr311-api</artifactId>
    <version>1.1.1</version>
</dependency>
```

jaxrs Client：

```java
public interface RsClient {

    @GET
    @Path("/hello")
    String hello();
}
```

使用：

```java
public class RsMain {
    public static void main(String[] args) {
        RsClient client = Feign.builder()
                .contract(new JAXRSContract()) //  使用 JAXRS Contract
                .target(RsClient.class, "http://localhost:8080");

        System.out.println(client.hello());
    }
}
```

#### 2. 自定义注解 来理解 Contract 的翻译

1. 自定义注解：`MyUrl`

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyUrl {

    String uri();

    String method();
}
```

2. 在服务接口上使用注解：

```java
public interface MyServiceClient {
    @MyUrl(uri = "/hello", method = "GET")
    String hello();
}
```

3. 自定义翻译器翻译`MyContract`来翻译`MyUrl`:

```java
public class MyContract extends Contract.BaseContract {

    @Override
    protected void processAnnotationOnMethod(MethodMetadata methodMetadata, Annotation annotation, Method method) {
        if (annotation.annotationType().isAssignableFrom(MyUrl.class)) {
            MyUrl myUrl = method.getDeclaredAnnotation(MyUrl.class);
            String httpMethod = myUrl.method();
            String uri = myUrl.uri();

            methodMetadata.template().method(httpMethod)
                    .append(uri);
        }
    }
  ...
```

4. 测试使用：

```java
public class TestMyContract {
    public static void main(String[] args) {
        MyServiceClient client = Feign.builder()
                .contract(new MyContract()) //使用自定义的翻译器
                .target(MyServiceClient.class, "http://localhost:8080");

        System.out.println(client.hello());
    }
}
```

## 请求拦截器

设置公共信息，比如权限请求头，或者设置json请求头等。

1. 新建拦截器：

   ```java
   public class MyInterceptor implements RequestInterceptor {

       @Override
       public void apply(RequestTemplate requestTemplate) {
           System.out.println("进入拦截器。。。");
           requestTemplate
                   .header("token", "xxx-xxx-xxx")
                   .header("Content-type", "application/json");
       }
   }
   ```

   ​

2. 测试：

   ```java
   public static void main(String[] args) {
           RsClient rsClient = Feign.builder()
                   .contract(new JAXRSContract())
                   .requestInterceptor(new MyInterceptor())
                   .target(RsClient.class, "http://localhost:8080");

           System.out.println(rsClient.hello());
       }
   ```

   ​

## 接口日志

```java
Feign.builder()
                .contract(new JAXRSContract())
                .logLevel(Logger.Level.FULL)
                .logger(new Logger.JavaLogger().appendToFile("test.log"))
```

