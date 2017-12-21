package org.orh.spring.cloud.ch208.cache;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.orh.spring.cloud.ch208.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MemberCacheService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @CacheResult
    @HystrixCommand
    public Member getMember(long id ) {
        logger.info("发出请求调用服务");
        return restTemplate.getForObject("http://spring-hy-member/member/{id}", Member.class, id);
    }

    @CacheResult
    @HystrixCommand(commandKey = "cacheKey")
    public String getFromCache(Integer id) {
        logger.info("执行getFromCache");
        return "success";
    }

    @CacheRemove(commandKey = "cacheKey")
    @HystrixCommand
    public String removeCache(Integer id) {
        logger.info("删除缓存");
        return "";
    }
}
