package org.orh.spring.cloud.ch301;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "spring-zuul-service")
public interface ServiceClient {

    @GetMapping("/user/{id}")
    public User user(@PathVariable("id") long id);

    public static class User {
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
