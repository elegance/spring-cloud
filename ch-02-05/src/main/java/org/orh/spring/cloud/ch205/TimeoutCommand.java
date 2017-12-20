package org.orh.spring.cloud.ch205;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class TimeoutCommand extends HystrixCommand<String> {
    private long runTime;

    protected TimeoutCommand(long runTime) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(2000))); // 设置 2s 认定为超时
        this.runTime = runTime;
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(runTime);
        return "success";
    }

    @Override
    protected String getFallback() {
        return "fallback: timeout";
    }
}
