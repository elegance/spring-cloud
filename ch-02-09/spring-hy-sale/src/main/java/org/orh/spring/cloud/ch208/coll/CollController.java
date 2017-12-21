package org.orh.spring.cloud.ch208.coll;

import org.orh.spring.cloud.ch208.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class CollController {

    @Autowired
    private CollService collService;

    @GetMapping("/coll")
    public Member testCollapsed() throws ExecutionException, InterruptedException {
        Future<Member> f1 = collService.getMember(1L);

        sleep(500);
        Future<Member> f2 = collService.getMember(2L);

        // f1, f2 属于 1s 内的请求，将会打包一起发出

        sleep(500);
        Future<Member> f3 = collService.getMember(3L);
        // f3 属于 1s 外的请求，将单独发出

        f1.get();
        f2.get();

        return f3.get();
    }

    @GetMapping("/collMax")
    public Member testCollapsed2() {
        // 这里将会  3个一组，的发出请求，即 12/3 = 4 发出 4次请求
        for (int i = 0; i < 12; i++) {
            collService.getMember2((long) i);
        }
        return null;
    }

    @GetMapping("/collTimeAndMax")
    public Member testCollapsed3() throws ExecutionException, InterruptedException {
        // 4 个请求 ，前三个会立即发出，满足 3个一发的条件
        Future<Member> f1 = collService.getMember3((long) 1);
        Future<Member> f2 = collService.getMember3((long) 2);
        Future<Member> f3 = collService.getMember3((long) 3);

        // 第4个请求，会等待 1s，看着1s内后面还有没有兄弟一起上路，没有的话就自己独自上路了
        Future<Member> f4 = collService.getMember3((long) 4);
        f1.get();
        f2.get();
        f3.get();
        f4.get();



        return null;
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
