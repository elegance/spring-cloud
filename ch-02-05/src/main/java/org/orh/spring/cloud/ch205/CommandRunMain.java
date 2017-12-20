package org.orh.spring.cloud.ch205;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CommandRunMain {
    Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommandRunMain runMain = new CommandRunMain();
        runMain.testSynchronous();
        runMain.testAsynchronous1();
        runMain.testAsynchronous2();
        runMain.testObservable();
    }

    public void testSynchronous() {
        String rs = new CommandHelloWorld("World").execute();
        logger.info("synchronous rs: {}", rs);
    }

    public void testAsynchronous1() throws ExecutionException, InterruptedException {
        logger.info("Asynchronous rs: {}", new CommandHelloWorld("world").queue().get());
    }

    public void testAsynchronous2() throws ExecutionException, InterruptedException {
        Future<String> fWorld = new CommandHelloWorld("world").queue();
        Future<String> fBob = new CommandHelloWorld("Bob").queue();

        logger.info("Asynchronous rs2: {}, {}", fWorld.get(), fBob.get());
    }

    public void testObservable() {
        Observable<String> oWorld = new CommandHelloWorld("World").observe();
        new CommandHelloWorld("Bob").toObservable();

        // blocking
        String rs1 = oWorld.toBlocking().single();
        logger.info("observable blocking rs1: {}", rs1);

        // non-blocking
        // 一般观察者有：next、complete、error 等方法
        oWorld.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                logger.info("Observable Completed. -- 流完成");
            }

            @Override
            public void onError(Throwable throwable) {
                logger.info("Observable error. -- 流发生错误");
            }

            @Override
            public void onNext(String s) {
                logger.info("next - 发出消息");
                logger.info("observable non-blocking rs1: {}", rs1);
            }
        });

        // 观察者可以是不完整的：只有 next 时调用，即一个 Action
        oWorld.subscribe((v -> {
            logger.info("observable non-blocking rs2: {}", rs1);
        }));
    }



    static class CommandHelloWorld extends HystrixCommand<String>{
        private String name;

        protected CommandHelloWorld(String name) {
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
            this.name = name;
        }

        @Override
        protected String run() throws Exception {
            return "Hello " + name + "!";
        }
    }


}
