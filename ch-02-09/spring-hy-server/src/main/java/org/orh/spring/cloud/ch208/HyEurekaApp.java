package org.orh.spring.cloud.ch208;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HyEurekaApp {
    public static void main(String[] args) {
        SpringApplication.run(HyEurekaApp.class, args);
    }
}
