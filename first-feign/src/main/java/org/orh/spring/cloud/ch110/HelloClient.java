package org.orh.spring.cloud.ch110;

import feign.Param;
import feign.RequestLine;

public interface HelloClient {

    @RequestLine("GET /hello")
    String hello();

    @RequestLine("GET /user/{id}")
    User getUser(@Param("id") int id);
}
