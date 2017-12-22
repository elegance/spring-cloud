package org.orh.spring.cloud.ch210;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/router/{id}")
    public Member router(@PathVariable("id") long id) {
        return memberService.getMember(id);
    }
}
