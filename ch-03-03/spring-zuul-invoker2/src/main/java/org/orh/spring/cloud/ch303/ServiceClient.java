package org.orh.spring.cloud.ch303;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(serviceId = "spring-zuul-service")
public interface ServiceClient {

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name);

}
