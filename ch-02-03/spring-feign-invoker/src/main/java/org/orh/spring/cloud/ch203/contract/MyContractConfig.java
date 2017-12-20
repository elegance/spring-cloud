package org.orh.spring.cloud.ch203.contract;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyContractConfig {

    @Bean
    public Contract myContract() {
        return new MyContract();
    }
}
