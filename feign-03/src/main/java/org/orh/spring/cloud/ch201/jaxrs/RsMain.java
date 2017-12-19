package org.orh.spring.cloud.ch201.jaxrs;

import feign.Feign;
import feign.jaxrs.JAXRSContract;

public class RsMain {
    public static void main(String[] args) {
        RsClient client = Feign.builder()
                .contract(new JAXRSContract()) //  使用 JAXRS Contract
                .target(RsClient.class, "http://localhost:8080");

        System.out.println(client.hello());
    }
}
