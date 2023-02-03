package com.jonas.mqttKafka.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SensorKafkaConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, KafkaAppConfigs.APPLICATION_ID);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaAppConfigs.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaAppConfigs.CONSUMER_GROUP_ID_CONFIG);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaAppConfigs.CONSUMER_AUTO_OFFSET_RESET_CONFIG);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singleton(KafkaAppConfigs.TOPIC_NAME));
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(3000));

        for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.value());
        }
    }
}
