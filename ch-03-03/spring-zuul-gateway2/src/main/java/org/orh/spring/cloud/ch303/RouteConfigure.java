package org.orh.spring.cloud.ch303;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfigure {

    @Bean
    public PatternServiceRouteMapper patternServiceRouteMapper() {
        // 访问网关：/xxx/** ，将会被路由到：spring-zuul-xxx
        // 理由 正则命名捕获，比如项目服务约定为：spring-zuul-xxModule，最后部分为模块部分，处理 /xxModule/请求
        System.out.println("初始化自定义路由。。。");
        return new PatternServiceRouteMapper("spring-zuul-(?<module>.+)", "${module}/**");
    }
}
