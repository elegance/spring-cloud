package org.orh.spring.cloud.ch203;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableFeignClients
public class InvokerApp {
    public static void main(String[] args) {
        SpringApplication.run(InvokerApp.class, args);
    }

    @Autowired
    ServiceClient serviceClient;

    @GetMapping("/call")
    public String call() {
        return serviceClient.hello("笑笑");
    }

    @GetMapping("/hello")
    public String hello() {
        return serviceClient.helloWithoutArgs();
    }

    @GetMapping("/get")
    public User getUser() {
        return serviceClient.getUser();
    }

    public static class User {
        private String userName;
        private int age;
        private String serverNodeInfo;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getServerNodeInfo() {
            return serverNodeInfo;
        }

        public void setServerNodeInfo(String serverNodeInfo) {
            this.serverNodeInfo = serverNodeInfo;
        }
    }
}
