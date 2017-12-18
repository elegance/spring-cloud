## Ribbon简介
* 负载均衡框架，支持可插拔的负载均衡规则
* 支持多种协议，如HTTP、UDP等
* 提供负载均衡客户端

## Ribbon 子模块
* ribbon-core：主要包括负载均衡器、负载均衡器接口、内置的负载均衡类实现
* ribbon-eureka：为Eureka客户端提供的负载均实现类
* ribbon-httpclient：为HttpClient提供的负载均衡实现类

## 负载均衡器组件
一个负载均衡器至少要提供以下功能：
* 维护各个服务的IP、端口等信息
* 根据特定逻辑选取服务

#### Ribbon 负载均衡器的实现
* ServerList
* Ping
* Rule

#### 开发实现
[test-service](test-service): 用spring-boot 提供一个简单的http服务，启动类指定不同的`server.port`来提供多个服务实例，以便测试负载均衡
[ribbon-client](ribbon-client): 使用简单java程序作为客户端来调用服务。

启动：多个`test-service`服务，启动`TestRibbon.java`，可以发现服务默认是轮询调用的。

如果你的项目没有使用`spring cloud`，那么也可选用`ribbon`来做负载均衡。

#### 配置格式
`client.nameSpace.property=value`如`my-client.ribbon.listOfServers=servre1:80`