package org.orh.spring.cloud.ch206;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.util.backoff.BackOff;

import java.util.concurrent.TimeUnit;

/**
 * 测试断路器在关闭后，再次打开的情况
 */
public class CircuitBreakerCloseTest {
    public static void main(String[] args) throws InterruptedException {
        boolean isFail = true; // 初始发出的请求都为失败，让其触发断路器打开
        // 即开启断路器条件未：10s 内有超过5个请求，并且请求的个数中有50%是失败的（大于500则会失败）
        for (int i = 0; i < 25; i++) {
            TestCommand command = new TestCommand(isFail);
            System.out.println(command.execute());

            HystrixCommandMetrics.HealthCounts healthCounts = command.getMetrics().getHealthCounts();
            System.out.println("断路状态：" + command.isCircuitBreakerOpen() + ", 发出的请求数量：" + healthCounts.getTotalRequests());

            if (command.isCircuitBreakerOpen()) {
                isFail = false;
                System.out.println("等待休眠期结束");
                TimeUnit.SECONDS.sleep(1); // 休息1s，可以观察 Hystrix ：sleepWindowInMilliseconds ，默认5s会尝试发出请求
            }
        }
    }

    static class TestCommand extends HystrixCommand<String> {
        private boolean isFail;


        protected TestCommand(boolean isFail) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties.Setter()
                                    .withCircuitBreakerRequestVolumeThreshold(5) // 5次 ，阀值5次，即10s 内，请求个数达到5个 （默认20个）
                                    .withCircuitBreakerErrorThresholdPercentage(50) // 错误量比例 达到 50，即 5个中 有 3个失败
                                    .withExecutionTimeoutInMilliseconds(500) // 认定执行逻辑超时的时间
                            // 即开启断路器条件未：10s 内有超过5个请求，并且请求的个数中有50%是失败的（大于500则会失败）
                    )
            );
            this.isFail = isFail;
        }

        @Override
        protected String run() throws Exception {
            long runTime = isFail ? 600 : 200;
            Thread.sleep(runTime);
            return "success";
        }

        @Override
        protected String getFallback() {
            return "fallback";
        }
    }
}
