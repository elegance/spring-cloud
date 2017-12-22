package org.orh.spring.cloud.ch210.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private MemberClient memberClient;

    @GetMapping("/hello")
    public String hello() {
        return memberClient.hello();
    }
}
