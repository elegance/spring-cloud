## Ribbon负载均衡器
`ILoadBalancer` 负载均衡器接口，测试：
```java
    // 负载均衡器
    ILoadBalancer loadBalancer = new BaseLoadBalancer();

    // 定义服务列表
    List<Server> serverList = new ArrayList<>();
    serverList.add(new Server("localhost", 8080));
    serverList.add(new Server("localhost", 8084));

    // 设置服务列表
    loadBalancer.addServers(serverList);

    for (int i = 0; i < 10; i++) {
        Server server = loadBalancer.chooseServer(null);
        System.out.println(server); // 轮询输出 8084/8080，因为BaseLoadBalancer 默认Rule 是 RoundRibbonRule
    }
```
#### 自定义负载均衡规则
编写类实现`IRule`接口，或者继承`AbstractLoadBalancerRule`：
[MyRuel.java](src/main/java/org/orh/spring/cloud/ch107/MyRule.java)、
[TestLBMyRuel.java](src/main/java/org/orh/spring/cloud/ch107/TestLBMyRule.java)

#### 配置中指定规则
主要参考 [ribbon官方文档](https://github.com/Netflix/ribbon/wiki/Working-with-load-balancers)，
如：`my-client.ribbon.NFLoadBalancerRuleClassName=org.orh.xxx.MyRule`

#### Ribbon 内置的负载均衡规则

内置的规则基本可以满足大部分需求，如果有足够复杂的需求，再自定义规则。

* [RoundRibbonRule](https://github.com/Netflix/ribbon/wiki/Working-with-load-balancers#roundrobinrule): 轮询服务列表

* [AvailabilityFilterRule](https://github.com/Netflix/ribbon/wiki/Working-with-load-balancers#availabilityfilteringrule): 将忽略断路连接或超过一定并发数量的连接的服务，默认逻辑有以下

  * 最近`3`次RestClient无法建立连接，一旦进入断路状态，将保持这个状态`30`s，如果继续连接失败，保持时间将会成倍增加

* [WeightedResponseTimeRule](https://github.com/Netflix/ribbon/wiki/Working-with-load-balancers#weightedresponsetimerule): 根据响应时间作为权重判断，响应时间越小，权重值越大。

* ZoneAvoidanceRule: 使用Zone对服务进行分类

* BestAvailableRule：忽略短路服务器，并选择并发数较低的服务器

* RandomRule: 随机选择一个可用服务器

* RetryRule: 可重试选择服务器

  ​

#### 负载均衡器其他组件及配置

[参考官方wiki](https://github.com/Netflix/ribbon/wiki/Working-with-load-balancers#components-of-load-balancer)