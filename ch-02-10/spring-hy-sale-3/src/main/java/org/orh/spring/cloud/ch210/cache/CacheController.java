package org.orh.spring.cloud.ch210.cache;

import org.orh.spring.cloud.ch210.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberCacheService memberCacheService;

    @GetMapping("/cache")
    public Member cache() {
        // 这里没有实际应用意义，只是演示了 3次相同的请求，后两次不会发出调用，而是经过缓存
        Member member = null;
        for (int i = 0; i < 3; i++) {
            member = memberCacheService.getMember(1);
        }
        return member;
    }

    @GetMapping("/rc")
    public String rc() {
        // 发出两次请求
        logger.info(memberCacheService.getFromCache(1));
        logger.info(memberCacheService.getFromCache(1));

        // 清除缓存-注意也需要传入相同的参数
        memberCacheService.removeCache(1);

        // 再次请求
        logger.info(memberCacheService.getFromCache(1));
        return "";
    }
}
