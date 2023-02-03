package com.jonas.mqttKafka.mqtt;

import org.apache.commons.configuration.CompositeConfiguration;

import static com.jonas.mqttKafka.MqttToKafkaMainTest.getCompositeConfiguration;

public class MqttAppConfigs {
    private static final CompositeConfiguration config = getCompositeConfiguration();
    final static String MQTT_BROKER = config.getString("mqtt.broker");
    final static String MQTT_PORT = config.getString("mqtt.port");
    final static String MQTT_USERNAME = config.getString("mqtt.username");
    final static String MQTT_PASSWORD = config.getString("mqtt.password");
    final static String MQTT_PUB_CLIENT_ID = config.getString("mqtt.pub.client.id");
    final static String MQTT_SUB_CLIENT_ID = config.getString("mqtt.sub.client.id");
    final static String MQTT_QOS = config.getString("mqtt.qos");
    public final static String MQTT_TOPIC = config.getString("mqtt.topic");


}
