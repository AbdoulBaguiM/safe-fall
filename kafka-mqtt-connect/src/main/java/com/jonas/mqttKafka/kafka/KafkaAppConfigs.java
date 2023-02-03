package com.jonas.mqttKafka.kafka;

import org.apache.commons.configuration.CompositeConfiguration;

import java.util.Collections;
import java.util.List;

import static com.jonas.mqttKafka.MqttToKafkaMainTest.getCompositeConfiguration;

public class KafkaAppConfigs {
    private static final CompositeConfiguration config = getCompositeConfiguration();
    final static String APPLICATION_ID = config.getString("kafka.application.id");
    final static String BOOTSTRAP_SERVERS = config.getString("kafka.host");
    final static String CONSUMER_GROUP_ID_CONFIG = config.getString("kafka.consumer.group.id");
    final static String CONSUMER_AUTO_OFFSET_RESET_CONFIG = "earliest";
    public final static String TOPIC_NAME = config.getString("kafka.topics");
    final static int NUM_EVENTS = 1000000;
}
