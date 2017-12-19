package org.orh.spring.cloud.ch201.contract;

import feign.Feign;

public class TestMyContract {
    public static void main(String[] args) {
        MyServiceClient client = Feign.builder()
                .contract(new MyContract()) // 使用自定义的翻译器
                .target(MyServiceClient.class, "http://localhost:8080");

        System.out.println(client.hello());
    }
}
