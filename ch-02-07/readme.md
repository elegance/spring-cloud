## Hystrix的使用 （三） 隔离、缓存、合并请求

#### 1. 隔离策略

* Thread 线程，默认策略，由线程池来决定
* Semaphore 信号量，当一个请求的并发数高于设定的阀值，则不会在执行命令。比Thread轻量，开销较小，缺点是不支持异步、超时机制，按照[wiki文档](https://github.com/Netflix/Hystrix/wiki/Configuration#execution.isolation.strategy)，这种方式通常适合请求量高单秒内超过100个请求，适合非网络请求。

测试隔离策略`THREAD`, 线程池满载fall back:  [ThreadStrategyMain.java](src/main/java/org/orh/spring/cloud/ch207/ThreadStrategyMain.java)

```java
public static void main(String[] args) {
        // 设定线程池 coreSize 为3，即 active 达到3 后的任务 会执行fallback
        // Hystrix 线程池 队列默认为-1，所以不会进入队列，以及 maxSize
        ConfigurationManager.getConfigInstance().setProperty("hystrix.threadpool.default.coreSize", 3);

        for (int i = 1; i <= 6; i++) {
            MyCommand command = new MyCommand(i);
            command.queue(); // 异步 （get 会变为future.get() 阻塞当前线程）
        }
    }
```

测试隔离策略`SEMAPHORE`，达到信号量的限定阀值 fall back: [SemaphoreStrategyMain.java](src/main/java/org/orh/spring/cloud/ch207/SemaphoreStrategyMain.java)

```java
public static void main(String[] args) {
        // 将 隔离策略 改为 信号量
        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.default.execution.isolation.strategy", HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE);
        // 将信号量 的阀值 设定为 3，即超过3 的部分，将会fallback
        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests", 3);


        for (int i = 1; i <= 6; i++) {
            final int index = i;
            // 因为 Semaphore 是信号量，不是多线程，不支持异步，所以需要建立线程来测试
            new Thread(() -> {
                MyCommand command = new MyCommand(index);
                command.execute(); // 3个正常，3个 fallback
            }).start();
        }
    }
```

#### 2. 缓存

* 在一个请求上下文`HystrixRequestContext`
* 通过`HystrixCommandKey` 与 `cacheKey`
* 重写`getCacheKey`方法

测试： [CacheMain.java](src/main/java/org/orh/spring/cloud/ch207/CacheMain.java)

#### 3. 合并请求

默认情况下，会为命令分配线程池来执行，线程池也会消耗，对于同类型的请求（URL相同，参数不同），Hystrix提供了合并请求的功能，在一次请求过程中，可以将一个时间段内的相同请求（参数不同），收集到同一个命令中执行，节省了线程开销，减少网络连接，类似数据库的批处理功能。

合并请求至少包含以下3个条件：

* 需要有一个执行请求的命令，将全部的参数整理，然后调用外部服务
* 需要有一个合并处理器，用于收集请求，以及处理结果
* 外部接口提供支持，例如外部的服务提供了 “/person/{personName}”的服务用于查找一个Person，如果合并请求，外部还需要提供一个 “/persons” 的服务，用于查找多个 Person。

测试类：[CollapserGetValueForKeyMain.java](src/main/java/org/orh/spring/cloud/ch207/CollapserGetValueForKeyMain.java)