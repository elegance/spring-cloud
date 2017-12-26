package org.orh.spring.cloud.ch306;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

public class Recv {

    private static Logger logger = LoggerFactory.getLogger(Recv.class);

    public static void main(String[] args) {
        String group = "black-cat-group";
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(initProperties(group));
        consumer.subscribe(Arrays.asList(Send.topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String str = String.format("groupId = %s, offset = %d, key = %s, value = %s, timestamp = %s \n",
                        group, record.offset(), record.key(), record.value(), record.timestamp());
                logger.info(str);
            }
        }
    }

    public static Properties initProperties(String groupId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Send.servers);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
