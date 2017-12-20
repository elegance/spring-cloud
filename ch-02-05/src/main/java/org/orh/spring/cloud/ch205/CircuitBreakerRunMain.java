package org.orh.spring.cloud.ch205;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class CircuitBreakerRunMain {

    public static void main(String[] args) {
//        FallBackCommand command1 = new FallBackCommand(false); // 初次设置为 false ，后面的修改为true会不生效
//        System.out.println(command1.execute());

        FallBackCommand command2 = new FallBackCommand(true); // 输出结果 将一直未 fallback
        System.out.println(command2.execute());
    }

    static class FallBackCommand extends HystrixCommand<String> {
        private boolean forceOpen;

        protected FallBackCommand(boolean forceOpen) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withCircuitBreakerForceOpen(forceOpen)));
        }

        @Override
        protected String run() throws Exception {
            return "success";
        }

        @Override
        protected String getFallback() {
            return "fallback";
        }
    }
}
