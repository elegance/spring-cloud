package org.orh.spring.cloud.ch205;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class PrimarySecondaryTest {
    public static void main(String[] args) throws InterruptedException {
        MainCommand command = new MainCommand();
        System.out.println(command.execute());
    }

    static class MainCommand extends HystrixCommand<String> {

        protected MainCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("MainCommand"));
        }

        @Override
        protected String run() throws Exception {
            // 并发执行
            new CommandA().execute(); // 异步
            new CommandB().execute(); // 异步
            return "Main success";
        }

        @Override
        protected String getFallback() {
            return "Main fall back";
        }
    }

    static class CommandA extends HystrixCommand<String> {
        protected CommandA() {
            super(HystrixCommandGroupKey.Factory.asKey("CommandA"));
        }

        @Override
        protected String run() throws Exception {
            System.out.println("Command A run方法:" + Thread.currentThread().getId());
            return "CommandA";
        }
    }

    static class CommandB extends HystrixCommand<String> {
        protected CommandB() {
            super(HystrixCommandGroupKey.Factory.asKey("CommandB"));
        }

        @Override
        protected String run() throws Exception {
            System.out.println("Command B run方法:" + Thread.currentThread().getId());
            return "CommandB";
        }
    }
}
