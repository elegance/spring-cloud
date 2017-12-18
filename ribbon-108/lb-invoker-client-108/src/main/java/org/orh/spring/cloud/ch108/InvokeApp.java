package org.orh.spring.cloud.ch108;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@Configuration
public class InvokeApp {

    public static void main(String[] args) {
        SpringApplication.run(InvokeApp.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/router")
    public String callService() {
        // restTemplate 会使用默认的轮询规则 来访问具体的服务
        String json = restTemplate.getForObject("http://service-provider/user", String.class);
        return json;
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/lb")
    public ServiceInstance lb() {
        ServiceInstance instance = loadBalancerClient.choose("service-provider");
        return instance;
    }

    @Autowired
    private SpringClientFactory springClientFactory;

    @GetMapping("/factory")
    public Object factory() {
        ZoneAwareLoadBalancer loadBalancer = (ZoneAwareLoadBalancer) springClientFactory.getLoadBalancer("service-provider");
        System.out.println(loadBalancer.getRule());

        ILoadBalancer loadBalancer1 = springClientFactory.getLoadBalancer("default");
        System.out.println(loadBalancer1.getClass());

        return springClientFactory.getContextNames();
    }
}
