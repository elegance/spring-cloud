package org.orh.spring.cloud.ch308;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@SpringBootApplication
@EnableBinding(ReceiveService.class)
public class ConsumerApp {

    private static Logger logger = LoggerFactory.getLogger(ConsumerApp.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }

    @StreamListener("myInput")
    public void onReceive(String msg) {
        logger.info("接收到消息：{}", msg);
    }
}
