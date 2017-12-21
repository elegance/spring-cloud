## Spring Cloud 整合 Hystrix（二）请求合并与缓存

基于分支[2-08](https://github.com/elegance/spring-cloud/tree/2-08/ch-02-08)项目开始。

#### 1. 使用缓存

* 新建过滤器，让其做请求初始化工作： `HystrixRequestContext ctx = HystrixRequestContext.initializeContext();`
* 使用缓存注解：`@CacheResult` + `@HystrixCommand` 服务方法上

#### 2. 删除缓存

* 服务接口使用注解：`@CacheResult` + `@HystrixCommand(commandKey = "cacheKey")`  指定 `commandKey`
* 删除接口使用注解：`@CacheRemove(commandKey = "cacheKey")` + `@HystrixCommand` ，当外部调用这个接口时（如果有参数则必须与缓存的服务接口参数一致），这回清除缓存

#### 3. 请求合并

* 合并**一段时间内的多个请求**，再执行**批量的接口**，**Hystrix**帮我们做了 **一段时间**、**合并多个请求**的动作。即要有以下条件
  * 提供给单个请求的收集接口，使用`@HystrixCollapser`标识接口为收集接口，返回Future<R>，来处理收集时间的窗口，收集接口的**方法体不会执行**，重要的是**参数**、**返回值**
  * 有提供给多个请求的处理接口（batch），真正执行请求
  * 满足配置的参数时，执行batch接口，副作用就是单个请求可能等待一个时间窗口才真正执行（依赖Future来处理时间窗口）。
* 合并指定数量的请求

配置示例：

* 指定时间

  ```java
  @HystrixCollapser(batchMethod = "getMembers", collapserProperties = {
              @HystrixProperty(name = "timerDelayInMilliseconds", value = "1000") // 收集1000ms内的请求
      })
      public Future<Member> getMember(Long id) {
          logger.info("执行单个请求的的方法");    //收集器中的代码不会执行，真正执行的是：“batch”方法，这里的方法用于收集请求参数，以及限定返回结果
          return null;
      }
  ```

  ​


* 指定请求数量

  ```java
    @HystrixCollapser(batchMethod = "getMembers", collapserProperties = {
              @HystrixProperty(name = "maxRequestsInBatch" , value = "3") // 达到 3个请求时发送
      })
      public Future<Member> getMember2(Long id) {
          logger.info("执行单个请求的方法--here is never be execute");
          return null;
      }
  ```

* 时间 + 数量 同时

  ```java
   // 满足任意一个条件，就会执行批量请求：1秒内的请求 或者 请求量达到了 3个
      @HystrixCollapser(batchMethod = "getMembers", collapserProperties = {
              @HystrixProperty(name = "timerDelayInMilliseconds", value = "1000"),
              @HystrixProperty(name = "maxRequestsInBatch" , value = "3")
      })
      public Future<Member> getMember3(Long id) {
          logger.info("执行单个请求的方法--here is never be execute");
          return null;
      }
  ```

  ​

