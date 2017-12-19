package org.orh.spring.cloud.ch201.interceptor;

import feign.Feign;
import feign.Logger;
import feign.jaxrs.JAXRSContract;
import org.orh.spring.cloud.ch201.jaxrs.RsClient;

public class TestMyInterceptor {
    public static void main(String[] args) {
        RsClient rsClient = Feign.builder()
                .contract(new JAXRSContract())
                .logLevel(Logger.Level.FULL)
                .logger(new Logger.JavaLogger().appendToFile("test.log"))
                .requestInterceptor(new MyInterceptor())
                .target(RsClient.class, "http://localhost:8080");

        System.out.println(rsClient.hello());
    }
}
