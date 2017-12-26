package org.orh.spring.cloud.ch306;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Send {
    private static Logger logger = LoggerFactory.getLogger(Send.class);

    public static final String servers = "192.168.1.25:9092";

    // 发送消息时会建立一个名为 “fish-topic”的topic
    public static final String topic = "fish-topic";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Producer<String, String> producer = new KafkaProducer<>(initProperties());

        Scanner s = new Scanner(System.in); // 从控制台得到输入，发送给broker
        System.out.println("请输入要生产的鱼：");

        while (true) {
            String line = s.nextLine();
            System.out.println("生产：" + line);

            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, line);
            RecordMetadata metadata = producer.send(record).get();
            String str = String.format("offset: %s, partition: %s, topic: %s, timestamp: %s\n", metadata.offset(), metadata.partition(), metadata.topic(), metadata.timestamp());
            logger.info(str);

            if (line.equals("exit")) {
                break;
            }
            System.out.println("请输入要生产的鱼：");
        }
        s.close();
        producer.close();
    }

    public static Properties initProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
