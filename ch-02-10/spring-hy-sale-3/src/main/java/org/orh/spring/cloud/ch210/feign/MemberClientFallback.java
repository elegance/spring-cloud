package org.orh.spring.cloud.ch210.feign;

import org.springframework.stereotype.Component;

@Component
public class MemberClientFallback implements MemberClient {
    @Override
    public String hello() {
        return "fallback hello";
    }
}
