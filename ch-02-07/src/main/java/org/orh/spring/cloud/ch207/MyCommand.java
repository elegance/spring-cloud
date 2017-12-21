package org.orh.spring.cloud.ch207;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCommand extends HystrixCommand<String> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private int index;

    protected MyCommand(int index) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.index = index;
    }

    @Override
    protected String run() throws Exception {
        logger.info("正常执行: " + index);
        return "";
    }

    @Override
    protected String getFallback() {
        logger.warn("fallback：" + index);
        return "";
    }
}
