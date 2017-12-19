package org.orh.spring.cloud.ch201;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.orh.spring.cloud.ch110.User;

public interface HelloClient {

    @RequestLine("GET /hello")
    String hello();

    @RequestLine("GET /user/{id}")
    User getUser(@Param("id") int id);

    @RequestLine(value = "POST /user")
    @Headers("Content-type: application/json")
    String create(User user);
}
