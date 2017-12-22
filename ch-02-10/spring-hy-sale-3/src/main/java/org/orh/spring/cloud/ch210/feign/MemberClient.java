package org.orh.spring.cloud.ch210.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "spring-hy-member", fallback = MemberClientFallback.class)
public interface MemberClient {

    @GetMapping("/hello")
    public String hello();
}
