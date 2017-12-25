package org.orh.spring.cloud.ch303;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableFeignClients
public class InvokerApp {
    public static void main(String[] args) {
        SpringApplication.run(InvokerApp.class, args);
    }

    @Autowired
    private ServiceClient serviceClient;

    @GetMapping("/call")
    public String call() {
        return serviceClient.hello("xiaoxiao");
    }

}
