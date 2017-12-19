package org.orh.spring.cloud.ch201;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

public class FeignMain {

    public static void main(String[] args) {
        HelloClient client = Feign.builder()
                .client(new MyFeignClient())
                .decoder(new GsonDecoder()) // json 解码器，用于解码收到的response
                .encoder(new GsonEncoder()) // json 编码器，用于编码发送请求
                .target(HelloClient.class, "http://localhost:8080");

        String resultRs = client.hello();
        System.out.println(resultRs);
    }
}
