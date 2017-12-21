package org.orh.spring.cloud.ch208;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MemberService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getMemberFallback", groupKey = "MemberGroup", commandKey = "MemberCommand",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "200")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1")
            }
    )
    public Member getMember(long id)  {
        sleep(50);
        return restTemplate.getForObject("http://spring-hy-member/member/{id}", Member.class, id);
    }

    public Member getMemberFallback(long id) {
        Member member = new Member();
        member.setId(id);
        member.setName("error member");
        return member;
    }
    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch (Exception e) {
            // ignore
        }
    }
}
