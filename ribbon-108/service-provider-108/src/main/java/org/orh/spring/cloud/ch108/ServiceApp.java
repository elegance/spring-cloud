package org.orh.spring.cloud.ch108;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class ServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApp.class, args);
    }

    @GetMapping("/user")
    public User getUser(HttpServletRequest request) {
        User user = new User();
        user.setUserName("XiaoXiao");
        user.setAge(18);
        user.setServerNodeInfo(request.getRequestURL().toString());
        return user;
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
