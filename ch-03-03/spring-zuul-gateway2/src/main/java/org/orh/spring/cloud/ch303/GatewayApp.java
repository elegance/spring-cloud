package org.orh.spring.cloud.ch303;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringBootApplication
@EnableZuulProxy
@RestController
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }

    @GetMapping("fd")
    public String forward() {
        return "forward";
    }

    @PostConstruct
    public void zuulInit() {
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        String resourceDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        // 读取配置，获取本项目目录
        String scriptRoot = System.getProperty("zuul.filter.root", resourceDir + File.separator + "groovy");
        System.out.println("动态过滤器，监控目录：" + scriptRoot);

        // 获取刷新间隔
        String refreshInterval = System.getProperty("zuul.filter.refreshInterval", "5");

        if (scriptRoot.length() > 0) {
            scriptRoot = scriptRoot + File.separator;
        }
        FilterFileManager.setFilenameFilter(new GroovyFileFilter());
        try {
            FilterFileManager.init(Integer.parseInt(refreshInterval),
                    scriptRoot);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
