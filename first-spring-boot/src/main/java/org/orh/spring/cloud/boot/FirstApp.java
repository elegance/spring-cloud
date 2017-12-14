package org.orh.spring.cloud.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FirstApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FirstApp.class)
                .properties("spring.config.location=classpath:custom.yaml")
//                .properties("spring.profiles.active=test")
                .properties("spring.profiles.active=test")
                .run(args);

    }
}
