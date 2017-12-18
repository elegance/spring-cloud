package org.orh.spring.cloud.ch108;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class MyRule extends AbstractLoadBalancerRule {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Server choose(Object key) {
        logger.info("使用自定义规则负载均衡。");

        List<Server> servers = super.getLoadBalancer().getAllServers();

        Random random = new Random();
        int rNum = random.nextInt(10);

        if (rNum > 7) { // 让70的几率 在 8080 端口所在服务
            return getServerByPort(servers, 8080);
        }

        return getServerByPort(servers, 8084);
    }

    private Server getServerByPort(List<Server> servers, int port) {
        for (int j = 0; j < servers.size(); j++) {
            if (servers.get(j).getPort() == port) {
                return servers.get(j);
            }
        }
        return null;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
