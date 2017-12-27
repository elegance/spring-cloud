## 消息驱动(四)整合Spring Cloud

准备项目：

* Eureka 服务, [spring-stream-server](spring-stream-server)
* 消息生产者, [spring-stream-producer](spring-stream-consumer) 
* 消息消费者, [spring-stream-consumer](spring-stream-consumer)

####  1. 发送消息

* 依赖 - 使用 `rabbitmq`

  ```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
  </dependency>
  ```

* 发送服务接口

  ```java
  public interface SendService {

      @Output("myInput")
      SubscribableChannel sendOrder();
  }
  ```

* 调用发送接口服务

  ```java
  @GetMapping("/send/{msg}")
  public String send(@PathVariable("msg") String msg) {
    Message message = MessageBuilder.withPayload(msg).build();
    sendService.sendOrder().send(message);
    logger.info("发送消息：{}", msg);
    return "success";
  }
  ```

* 启用服务接口

  ```java
  @SpringBootApplication
  @EnableBinding(SendService.class)
  public class ProducerApp {
      public static void main(String[] args) {
          SpringApplication.run(ProducerApp.class, args);
      }
  }
  ```

#### 2. 接收消息

* 依赖（同发送，作为消息的客户端）

* 接收接口

  ```java
  public interface ReceiveService {

      @Input("myInput")
      SubscribableChannel myInput();
  }
  ```

* 定义接收`Listener`

  ```java
  @StreamListener("myInput")
  public void onReceive(String msg) {
    logger.info("接收到消息：{}", msg);
  }
  ```

  ​

* 启用绑定接口

  ```java
  @EnableBinding(ReceiveService.class)
  ```

#### 3. 更换为 kafka

只需更换 `pom.xml`中`spring-cloud-stream-binder-rabbit`为`spring-cloud-stream-binder-kafka`即可，代码都无需变动。

如果你的kafka不再本地，可以像以下这样配置：

```yaml
spring:
  cloud:
    stream:
      kafka:
        binder:
          brokers: 192.168.1.25
          zkNodes: 192.168.1.25
```

更多配置请参考[官方文档](http://cloud.spring.io/spring-cloud-static/Edgware.RELEASE/single/spring-cloud.html#_kafka_binder_properties)

#### 4. 消费者组

消费者组内只有一个消费者消费，如果有多个消费者组订阅了消息，消息将发送到每一个组，即**消费者组内队列模式，组间广播模式**

配置方式：`spring.cloud.stream.bindings.<channelName>.group`，如示例项目中,我们可以配置不同的group：`spring.cloud.stream.bindings.myInput.group=groupA`，来启动多个实例测试。

#### 5. Spring Cloud 内置接口

* Sink

  ```java
  public interface Sink {
      String INPUT = "input";

      @Input("input")
      SubscribableChannel input();
  }
  ```

* Source

  ```java
  public interface Source {
      String OUTPUT = "output";

      @Output("output")
      MessageChannel output();
  }
  ```

  ​


* Processor 

  ```java
  public interface Processor extends Source, Sink
  ```

* 使用例子：

  ```java
  @EnableBinding(Processor.class)
  public class TransformProcessor {

    @Autowired
    VotingService votingService;

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public VoteResult handle(Vote vote) {
      return votingService.record(vote);
    }
  }
  ```

  ​