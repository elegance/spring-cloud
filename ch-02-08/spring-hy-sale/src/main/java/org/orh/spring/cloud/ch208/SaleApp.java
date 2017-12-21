package org.orh.spring.cloud.ch208;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class SaleApp {
    public static void main(String[] args) {
        SpringApplication.run(SaleApp.class, args);
    }
}
