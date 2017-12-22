package org.orh.spring.cloud.ch301;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/invoker/{id}")
    public String invoker(@PathVariable("id") long id) {
        ServiceClient.User user = serviceClient.user(id);
        System.out.println(user.getUserName());
        return "success-" + user.getUserId();
    }

}

