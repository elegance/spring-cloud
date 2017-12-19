package org.orh.spring.cloud.ch106;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class ServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApp.class, args);
    }

    @GetMapping("/user/{id}")
    public User user(@PathVariable int id, HttpServletRequest request) {
        User user = new User();
        user.setUserName("XiaoXiao");
        user.setAge(id);
        user.setServerNodeInfo(request.getRequestURL().toString());
        return user;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    static class User {
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
