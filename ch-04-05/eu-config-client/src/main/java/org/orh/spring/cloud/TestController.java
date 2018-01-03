package org.orh.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private Environment env;

    @GetMapping("/prop/{name}")
    public String prop(@PathVariable("name") String propName) {
        System.out.println(propName);
        return env.getProperty(propName);
    }
}
