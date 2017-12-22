package org.orh.spring.cloud.ch210;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class DashboardApp {
    public static void main(String[] args) {
        SpringApplication.run(DashboardApp.class, args);
    }
}
