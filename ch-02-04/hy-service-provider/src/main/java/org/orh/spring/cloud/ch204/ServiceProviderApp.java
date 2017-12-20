package org.orh.spring.cloud.ch204;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
public class ServiceProviderApp {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderApp.class, args);
    }

    @GetMapping("/normalHello")
    public String normalHello() {
        return "Hello world";
    }

    @GetMapping("/longTimeHello")
    public String longTimeHello() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        return "Long Time Hello world";
    }
}
