## 消息驱动(二) RabbitMQ的使用

RabbitMQ 基于AMQP 协议，选用Erlang语言作为技术实现。

#### 下载和运行

[安装请参考官网。](http://www.rabbitmq.com/download.html)

* 查看插件：`rabbitmq-plugins list`
* 开启管理插件：`rabbitmq-plugins enable rabbitmq_management`，通过浏览器访问`http://localhost:15672`，默认用户名密码`guest/guest`

管理界面有6个功能菜单：

* Overview: 查看RabbitMQ 的基本信息
* Connections: 查看RabbitMQ 客户端的连接信息
* Channels: 查看RabbitMQ 的通道
* Exchanges：查看RabbitMQ 的交换机
* Queues：查看RabbitMQ 的队列
* Admin：管理RabbitMQ 的用户、虚拟主机、策略等数据

补充说明：RabbitMQ 只有 Queue，没有 Topic，因为可以通过 Exchange 与Queue的组合来实现 Topic所具备的功能。

#### 简单示例

通过官网可以看到[更多示例](http://www.rabbitmq.com/getstarted.html)。

* 依赖

  ```xml
  <dependency>
      <groupId>com.rabbitmq</groupId>
      <artifactId>amqp-client</artifactId>
      <version>5.1.1</version>
  </dependency>
  ```

发送消息：

```java
public class Send {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = "test_queue";
        channel.queueDeclare(queueName, false, false, false, null);

        String message = "Hello world!";
        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
```

接收消息：

```java
public class Recv {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = "test_queue";
        channel.queueDeclare(queueName, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
```

