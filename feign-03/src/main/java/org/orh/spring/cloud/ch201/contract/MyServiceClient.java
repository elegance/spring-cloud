package org.orh.spring.cloud.ch201.contract;

public interface MyServiceClient {
    @MyUrl(uri = "/hello", method = "GET")
    String hello();
}
