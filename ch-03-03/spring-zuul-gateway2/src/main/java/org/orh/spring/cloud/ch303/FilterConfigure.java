package org.orh.spring.cloud.ch303;

import com.netflix.zuul.ZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfigure {
    @Bean
    public MyFilter myFilter() {
        return new MyFilter();
    }
}
