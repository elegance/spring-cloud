package org.orh.spring.cloud.ch309;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RestController
@RefreshScope
public class ClientApp {
    public static void main(String[] args) {
        SpringApplication.run(ClientApp.class, args);
    }

    @Autowired
    private Environment env;

    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstruct method execute...");
    }


    @GetMapping("/prop/{name}")
    public String prop(@PathVariable("name") String propName) {
        System.out.println(propName);
        return env.getProperty(propName);
    }

    @Value("${test.user.name}")
    private String name;

    @GetMapping("/name")
    public String name() {
        return this.name;
    }
}
