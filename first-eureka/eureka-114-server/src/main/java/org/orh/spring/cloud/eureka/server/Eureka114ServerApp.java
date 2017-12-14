package org.orh.spring.cloud.eureka.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Eureka114ServerApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Eureka114ServerApp.class)
                .web(true)
                .run(args);
    }
}
