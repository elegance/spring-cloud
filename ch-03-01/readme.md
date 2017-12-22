## 网关Zuul(一)介绍与使用

前面章节的测试是直接通过访问我们服务提供的地址、端口的，真实情况需要统一对外的提供服务。

#### Zuul 介绍

* 是 Netflix 的一个子项目
* 提供代理、过滤、路由等功能

#### 编写第一个Zuul 程序

先单纯的使用 web 项目整合 zuul 来体验 zuul。

* 建立服务项目，[zuul-service](zuul-service)，简单的web服务，8080端口

* 建立网关项目，[zuul-gateway](zuul-gateway)

  * 添加依赖 `spring-cloud-starter-netflix-zuul`，启用`zuul`: `@EnableZuulProxy`

  * `application.yaml`配置

    ```yaml
    server:
      port: 9000
    zuul:
      routes:
      	# service 是访问前缀，可以修改
        service:
          url: http://localhost:8080/hi

    ```

测试：

原服务：http://localhost:8080/hello/test 

通过zuul代理访问服务：http://localhost:9000/service/hello/test

#### 其他

* `ZuulServlet` pre、routing、post 三个阶段，异常过滤器处理