package org.orh.spring.cloud.ch110;

import feign.Feign;
import feign.gson.GsonDecoder;

public class FeignMain {

    public static void main(String[] args) {
        HelloClient client = Feign.builder()
                .decoder(new GsonDecoder()) // json 解码
                .target(HelloClient.class, "http://localhost:8080");
        System.out.println(client.hello()); // 访问hello 接口

        User user = client.getUser(2);
        System.out.println(user);
    }
}
