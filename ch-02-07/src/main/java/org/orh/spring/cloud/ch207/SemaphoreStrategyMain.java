package org.orh.spring.cloud.ch207;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommandProperties;

public class SemaphoreStrategyMain {

    public static void main(String[] args) {
        // 将 隔离策略 改为 信号量
        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.default.execution.isolation.strategy", HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE);
        // 将信号量 的阀值 设定为 3，即超过3 的部分，将会fallback
        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests", 3);


        for (int i = 1; i <= 6; i++) {
            final int index = i;
            // 因为 Semaphore 是信号量，不是多线程，不支持异步，所以需要建立线程来测试
            new Thread(() -> {
                MyCommand command = new MyCommand(index);
                command.execute(); // 3个正常，3个 fallback
            }).start();
        }
    }

}
