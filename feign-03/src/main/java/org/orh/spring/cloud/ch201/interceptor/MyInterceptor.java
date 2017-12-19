package org.orh.spring.cloud.ch201.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class MyInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("进入拦截器。。。");
        requestTemplate
                .header("token", "xxx-xxx-xxx")
                .header("Content-type", "application/json");
    }
}
