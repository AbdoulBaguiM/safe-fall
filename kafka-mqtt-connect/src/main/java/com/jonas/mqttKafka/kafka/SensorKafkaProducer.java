package com.jonas.mqttKafka.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SensorKafkaProducer {

    String pubTopic;

    private Producer<String, String> ProducerProperties() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaAppConfigs.APPLICATION_ID);
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaAppConfigs.BOOTSTRAP_SERVERS);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new KafkaProducer<>(producerProps);
    }

    public void publishMessages(String data) {

        final Producer<String, String> producer = ProducerProperties();

        final ProducerRecord<String, String> record =
                new ProducerRecord<>(
                        pubTopic, UUID.randomUUID().toString(), data
                );

        producer.send(record, (metadata, exception) -> {
            if (metadata != null) {
                System.out.println("Sending data: -> (" + record.key() + " , " + record.value() + ")");
            } else {
                System.out.println("Error Sending Record -> " + record.value());
            }
        });

        producer.close();
    }
}
