package org.orh.spring.cloud.eureka.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
public class EurekaPoliceClientApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaPoliceClientApp.class)
                .web(true)
                .run(args);
    }
}
