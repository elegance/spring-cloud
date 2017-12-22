package org.orh.spring.cloud.ch210.coll;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.orh.spring.cloud.ch210.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class CollService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @HystrixCollapser(batchMethod = "getMembers", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "1000") // 收集1000ms内的请求
    })
    public Future<Member> getMember(Long id) {
        logger.info("执行单个请求的的方法");    //收集器中的代码不会执行，真正执行的是：“batch”方法，这里的方法用于收集请求参数，以及限定返回结果
        return null;
    }

    @HystrixCollapser(batchMethod = "getMembers", collapserProperties = {
            @HystrixProperty(name = "maxRequestsInBatch" , value = "3") // 达到 3个请求时发送
    })
    public Future<Member> getMember2(Long id) {
        logger.info("执行单个请求的方法--here is never be execute");
        return null;
    }

    // 满足任意一个条件，就会执行批量请求：1秒内的请求 或者 请求量达到了 3个
    @HystrixCollapser(batchMethod = "getMembers", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "1000"),
            @HystrixProperty(name = "maxRequestsInBatch" , value = "3")
    })
    public Future<Member> getMember3(Long id) {
        logger.info("执行单个请求的方法--here is never be execute");
        return null;
    }

    // 等待满足上面的收集器满足后，进入下面的批量执行方法
    @HystrixCommand
    public List<Member> getMembers(List<Long> ids) {
        logger.info("调用批量接口：" + Arrays.toString(ids.toArray()));

        List<Member> members = new ArrayList<>();
        // 模拟调用 批量的接口：

        for (Long id : ids) {
            Member member = new Member();
            member.setId(id);
            member.setName("笑笑-" + id);
            members.add(member);
        }
        return members;
    }

}
