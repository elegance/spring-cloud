package org.orh.spring.cloud.ch308;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(SendService.class)
public class ProducerApp {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class, args);
    }
}
