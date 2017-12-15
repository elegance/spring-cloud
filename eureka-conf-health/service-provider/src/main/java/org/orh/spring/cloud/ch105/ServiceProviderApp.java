package org.orh.spring.cloud.ch105;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class ServiceProviderApp {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderApp.class, args);
    }

    public volatile static boolean dbCanUse = true;

    @GetMapping("/db/{can}")
    public void setDbCanUse(@PathVariable boolean can) {
        dbCanUse = can;
    }

    @GetMapping("/service")
    public String testService() {
        return "service is up";
    }
}
