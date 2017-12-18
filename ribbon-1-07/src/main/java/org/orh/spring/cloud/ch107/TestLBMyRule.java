package org.orh.spring.cloud.ch107;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;

public class TestLBMyRule {
    public static void main(String[] args) {
        // 负载均衡器
        BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

        // 定义服务列表
        List<Server> serverList = new ArrayList<>();
        serverList.add(new Server("localhost", 8080));
        serverList.add(new Server("localhost", 8084));

        // 设置服务列表
        loadBalancer.addServers(serverList);

        // 自定义规则
        MyRule rule = new MyRule();
        loadBalancer.setRule(rule);

        for (int i = 0; i < 10; i++) {
            Server server = loadBalancer.chooseServer(null);
            System.out.println(server); // 轮询输出 8084/8080，因为BaseLoadBalancer 默认Rule 是 RoundRibbonRule
        }
    }
}
