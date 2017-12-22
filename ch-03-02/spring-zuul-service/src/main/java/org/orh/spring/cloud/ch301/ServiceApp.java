package org.orh.spring.cloud.ch301;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApp.class, args);
    }

    @GetMapping("/user/{id}")
    public User user(@PathVariable("id") long id) {
        User user = new User();
        user.setUserId(id);
        user.setUserName("笑笑");
        return user;
    }

    static class User {
        private long userId;
        private String userName;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
