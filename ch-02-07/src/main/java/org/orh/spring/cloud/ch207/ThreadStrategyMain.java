package org.orh.spring.cloud.ch207;

import com.netflix.config.ConfigurationManager;

public class ThreadStrategyMain {

    public static void main(String[] args) {
        // 设定线程池 coreSize 为3，即 active 达到3 后的任务 会执行fallback
        // Hystrix 线程池 队列默认为-1，所以不会进入队列，以及 maxSize
        ConfigurationManager.getConfigInstance().setProperty("hystrix.threadpool.default.coreSize", 3);

        for (int i = 1; i <= 6; i++) {
            MyCommand command = new MyCommand(i);
            command.queue(); // 异步 （get 会变为future.get() 阻塞当前线程）
        }
    }

}
