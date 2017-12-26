## 消息驱动 (一) Stream介绍

Spring Cloud Stream简化消息开发，通过Stream提供的API 来编写生产者、消费者端代码，解耦与具体的中间件耦合。

* 消息生产者
* 消息中间件(RabbitMQ、kafka..)
* 消息消费者



#### JMS 规范 (Java Message Service)

Java Message Service 定义了Java 消息系统的API。

* **提供者**：实现JMS规范的消息中间件服务
* **客户端**：发送或接收消息的应用程序
* **生产者/发布者**：创建并发送消息的客户端

#### AMQP 规范(Advanced Message Queueing Protocol)

独立于平台的底层消息传递协议，不限定语言。