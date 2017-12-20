## Hystrix 的使用

Hystrix 在客户端 设定超时时间，执行请求逻辑，如果超时执行回退逻辑。

**Command的包装执行逻辑，来执行请求**

#### 1. 命令执行方式

* toObservable  (以下方法的基础，Observable 在被订阅之前不会执行)
* observe，返回Observable
* queue ，返回 Future<R>
* execute （同步），实际执行的 queue().get()

可以查看官方[Netflix Hystrix Github examples](https://github.com/Netflix/Hystrix/tree/master/hystrix-examples/src/main/java/com/netflix/hystrix/examples/basic)

#### 2. Hystrix 配置

参考官方： [Netflix Github Configuration](https://github.com/Netflix/Hystrix/wiki/Configuration)

* 配置超时时间
* 配置key
  * Groupkey 组名
  * CommandKey 命令名称
  * ThreadPoolKey 线程池名称

#### 3. 回退的情况

* 执行超时等失败情况
* 断路器打开 - (保险丝断开l)
* 线程池满载

#### 4. 回退模式

命令中包括多个子命令的情况，主命令中处理 fall back。