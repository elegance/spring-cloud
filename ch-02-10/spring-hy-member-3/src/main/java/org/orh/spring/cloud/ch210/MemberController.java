package org.orh.spring.cloud.ch210;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    /**
     * 提供服务，根据会员ID ，返回会员信息
     *
     * @param id 会员Id
     * @return
     */
    @GetMapping("/member/{id}")
    public Member member(@PathVariable("id") long id) {
        Member member = new Member();
        member.setId(id);
        member.setName("笑笑-" + id);
        return member;
    }

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(200);
        return "hello";
    }
}
