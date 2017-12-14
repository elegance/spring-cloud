package org.orh.spring.cloud.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private ApplicationContext ctx;

    @GetMapping("/hello")
    public String hello() {
        String rs = ctx.getEnvironment().getProperty("common.config");
        String jdbc = ctx.getEnvironment().getProperty("project.jdbc");
        return rs + ", jdbc:" + jdbc;
    }


    @GetMapping("/profile")
    public Object profileTest() {
        return ctx.getEnvironment().getActiveProfiles();
    }
}
