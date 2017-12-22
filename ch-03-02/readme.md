## 网关Zuul(二) 集群中使用Zuul

Spring Cloud 中来使用 Zuul

#### 准备项目

* Eureka服务器，[spring-zuul-eu-server](spring-zuul-eu-server)
* 服务提供者，[spring-zuul-service](spring-zuul-service)
* 服务调用者，[spring-zuul-invoker](spring-zuul-invoker)
* 网关，[spring-zuul-gateway](spring-zuul-gateway)

下面三个项目都是Eureka 的Client，将注册到Eureka服务器上。
Eureka 服务：http://locahost:8761/

服务提供者：http://locahost:8080/user/1

服务调用者：http://locahost:8081/invoker/1

网关调用：http://locahost:9000/invoker/invoker/1

#### 更换Http客户端

* 默认使用 HttpClient
* 可以更换为 OKHttpClient
  * ribbon.okhttp.enabled=true
  * maven: okhttp 依赖