## 消息驱动（三）Kafka 的使用

kafka对消息保存时根据Topic进行归类,发送消息者成为Producer(push),消息接受者成为Consumer(poll)。利用zookeeper存储broker信息（topic、partition）、consumer信息（offset）。

消息消费模式：

* consumer group 组内队列模式
* consumer group 之间广播模式，订阅了topic 的组都会收到消息。

注意：

* 如果你的kafka server不在本地，注意配置好`advertised.listeners` 指定ip，因为客户端访问报的错误不明显，耗费了不少时间。