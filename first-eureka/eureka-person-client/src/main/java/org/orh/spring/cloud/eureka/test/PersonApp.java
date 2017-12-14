package org.orh.spring.cloud.eureka.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableAutoConfiguration
public class PersonApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PersonApp.class)
                .web(true)
                .run(args);
    }
}
