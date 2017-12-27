package org.orh.spring.cloud.ch308;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private SendService sendService;

    @GetMapping("/send/{msg}")
    public String send(@PathVariable("msg") String msg) {
        Message message = MessageBuilder.withPayload(msg).build();
        sendService.sendOrder().send(message);
        logger.info("发送消息：{}", msg);
        return "success";
    }
}
