## Feign 的使用

#### 1. 编码器、解码器

```java
 HelloClient client = Feign.builder()
                .decoder(new GsonDecoder()) // json 解码器，用于解码收到的response
                .encoder(new GsonEncoder()) // json 编码器，用于编码发送请求
                .target(HelloClient.class, "http://localhost:8080");
```

另外可以使用Xml编码解码、自定义编码解码等

#### 2. 自定义Feign客户端

* Feign 默认使用**HttpURLConnection**连接HTTP服务
* 支持插件式，编写自定义客户端

使用`HttpClient`来访问HTTP服务，加入`httpClient`依赖

```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.4</version>
</dependency>
```

1. 编写自定义Cliet：`MyClient`实现 Feign的`feign.Client`接口(Demo 只是为了理解，不可用于生产)

   ```MyFeignClient
   /**
    * 使用自定义的 Client 来替代 Feign HttpURLConnect 发起连接
    */
   public class MyFeignClient implements Client {
       private Logger logger = LoggerFactory.getLogger(getClass());

       @Override
       public Response execute(Request request, Request.Options options) throws IOException {
           logger.info("use MyFeignClient...");

           CloseableHttpClient httpClient = HttpClients.createDefault();
           final String method = request.method();
           Response response = null;

           // feign request -> http client request
           // method、uri、headers... 挨个获取设置
           HttpRequestBase httpRequestBase = new HttpRequestBase() {
               @Override
               public String getMethod() {
                   return method;
               }
           };
           try {
               httpRequestBase.setURI(new URI(request.url()));
               request.headers().entrySet().forEach(entry -> {
                   entry.getValue().stream().forEach(e -> httpRequestBase.addHeader(entry.getKey(), e));
               });
               HttpResponse httpResponse = httpClient.execute(httpRequestBase);
               byte[] responseBody = EntityUtils.toByteArray(httpResponse.getEntity());

               // http response -> feign response
               response = Response.builder()
                       .body(responseBody)
                       .status(httpResponse.getStatusLine().getStatusCode())
                       .headers(new HashMap<>())
                       .build();
           } catch (URISyntaxException e) {
               throw new RuntimeException("请求地址错误，", e);
           }
           return response;
       }
   }
   ```

   ​

2. 测试main中：

   ```java
    HelloClient client = Feign.builder()
                   .client(new MyClient())
   ....
   ```

   ​