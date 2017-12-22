package org.orh.spring.cloud.ch210;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MemberApp {
    public static void main(String[] args) {
        SpringApplication.run(MemberApp.class, args);
    }
}
