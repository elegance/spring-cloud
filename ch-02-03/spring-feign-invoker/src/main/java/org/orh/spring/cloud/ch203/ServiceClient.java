package org.orh.spring.cloud.ch203;

import org.orh.spring.cloud.ch203.contract.MyUrl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient("spring-feign-provider") // 指定服务名，来结构 IP、port ，服务实例从 eureka中取
public interface ServiceClient {

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name);

    @GetMapping("/user")
    public InvokerApp.User getUser();

    @MyUrl(uri = "/hello", method = "GET")
    public String helloWithoutArgs();
}
