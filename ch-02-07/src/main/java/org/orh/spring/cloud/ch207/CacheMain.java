package org.orh.spring.cloud.ch207;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CacheMain {
    private static Logger logger = LoggerFactory.getLogger(CacheMain.class);

    public static void main(String[] args) {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        String cacheKey = "command-cache";

        CacheCommand c1 = new CacheCommand(cacheKey);
        CacheCommand c2 = new CacheCommand(cacheKey);
        CacheCommand c3 = new CacheCommand(cacheKey);

        c1.execute();
        c2.execute();
        c3.execute();

        logger.info("c1 response is from cache: {}", c1.isResponseFromCache()); // false, elapsed 1s
        logger.info("c2 response is from cache: {}", c2.isResponseFromCache()); // true, straight away
        logger.info("c3 response is from cache: {}", c3.isResponseFromCache()); // true, straight away

        HystrixRequestCache cache = HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("CacheCommand"),
                HystrixConcurrencyStrategyDefault.getInstance());
        cache.clear(cacheKey); // 清空缓存

        CacheCommand c4 = new CacheCommand(cacheKey);
        c4.execute();
        logger.info("c4 response is from cache: {}", c4.isResponseFromCache()); // false, elapsed 1s

        ctx.shutdown();
    }

    static class CacheCommand extends HystrixCommand<String> {
        private String cacheKey;

        public CacheCommand(String cacheKey) {
            super(Setter.withGroupKey(
                    HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandKey(
                            HystrixCommandKey.Factory.asKey("CacheCommand"))
            );
            this.cacheKey = cacheKey;
        }

        @Override
        protected String run() throws Exception {
            TimeUnit.SECONDS.sleep(50);
            return "success";
        }

        @Override
        public String getCacheKey() {
            return cacheKey;
        }

        @Override
        protected String getFallback() {
            return "fallback";
        }
    }
}
