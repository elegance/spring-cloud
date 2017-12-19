package org.orh.spring.cloud.ch109;

import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;

@SpringBootApplication
@Configuration
@RestController
public class LoadBalancedApp {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(LoadBalancedApp.class, args);
    }

    @Autowired
    private RestTemplate restTemplateA;

    @GetMapping("/call")
    public String call() {
        return restTemplateA.getForObject("http://custom-service/call", String.class);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    // 会自动注入 带有 @MyLoadBalanced 注解的 Bean 到 templates 集合
    @Autowired(required = false)
    @MyLoadBalanced
    private List<RestTemplate> templates;

    @Autowired
    private MyHttpRequestInterceptor interceptor;

    // 容器启动初始化时，执行下面方法 给 template中的 实例添加 拦截器
    @Bean
    public SmartInitializingSingleton lbTemplateInitializing() {
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                logger.info("@MyLoadBalanced修饰的Template有{}个", templates.size());
                for (RestTemplate tpl : templates) {
                    tpl.getInterceptors().add(interceptor);
                }
            }
        };
    }

    @Bean
    @MyLoadBalanced
    public RestTemplate restTemplateA() {
        return new RestTemplate();
    }

    @Bean
    @MyLoadBalanced
    public RestTemplate restTemplateB() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate restTemplateC() {
        return new RestTemplate();
    }
}
