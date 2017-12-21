package org.orh.spring.cloud.ch207;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

// 合并处理器泛型： <BatchReturnType, ResponseType, RequestArgumentType>
public class CollapserGetValueForKeyMain extends HystrixCollapser<List<String>, String, Integer> {

    private Integer key;

    public CollapserGetValueForKeyMain(Integer key) {
        this.key = key;
    }

    @Override
    public Integer getRequestArgument() {
        return key;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        return new BatchCommand(collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<String, Integer> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }

    }

    static class BatchCommand extends HystrixCommand<List<String>> {
        private final Collection<CollapsedRequest<String, Integer>> requests;

        protected BatchCommand(Collection<CollapsedRequest<String, Integer>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
            this.requests = requests;
        }

        @Override
        protected List<String> run() throws Exception {
            ArrayList<String> response = new ArrayList<>();
            for (CollapsedRequest<String, Integer> request : requests) {
                response.add("ValueForKey: " + request.getArgument());
            }
            return response;
        }
    }

    static class Test {
        private static Logger logger = LoggerFactory.getLogger(Test.class);

        public static void main(String[] args) {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();

            try {
                Future<String> f1 = new CollapserGetValueForKeyMain(1).queue();
                Future<String> f2 = new CollapserGetValueForKeyMain(2).queue();
                Future<String> f3 = new CollapserGetValueForKeyMain(3).queue();
                Future<String> f4 = new CollapserGetValueForKeyMain(4).queue();

                logger.info(f1.get());
                logger.info(f2.get());
                logger.info(f3.get());
                logger.info(f4.get());

                int numExecuted = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size();
                logger.info("num executed: {}", numExecuted);

                if (numExecuted > 2) {
                    logger.warn("some of command should have been collapsed");
                }

                System.err.println("HystrixRequestLog.getCurrentRequest().getAllExecutedCommands(): " + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands());

                int numLogs = 0;
                for (HystrixInvokableInfo<?> command : HystrixRequestLog.getCurrentRequest().getAllExecutedCommands()) {
                    numLogs++;
                    logger.info(command.getCommandKey().name());

                    System.err.println(command.getCommandKey().name() + " => command.getExecutionEvents(): " + command.getExecutionEvents());
                    logger.info("command.executionsEvents.contains collapsed:{}", command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
                    logger.info("command.executionsEvents.contains success:{}", command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
