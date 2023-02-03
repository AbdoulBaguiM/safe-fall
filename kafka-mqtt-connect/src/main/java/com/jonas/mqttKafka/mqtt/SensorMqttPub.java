package com.jonas.mqttKafka.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SensorMqttPub {

    public static void main(String[] args) {

        String topic = "topic/demo";
        String content = "{ \"values\":{ \"tempSoil\": 0, \"tempAir\": 0, \"humidity\": 0, \"moisture\": 0, \"ph\": 0, \"npk\": { \"n\": 0, \"p\": 0, \"k\": 0 } }, \"timestamp\": \"23-01-2023 19:30:45\", \"nodeId\": 123456789, \"grid\": \"121\" }";

        try {
            MqttClient client = new MqttClient(MqttAppConfigs.MQTT_BROKER, MqttAppConfigs.MQTT_PUB_CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MqttAppConfigs.MQTT_USERNAME);
            options.setPassword(MqttAppConfigs.MQTT_PASSWORD.toCharArray());
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);

            client.connect(options);

            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(Integer.parseInt(MqttAppConfigs.MQTT_QOS));

            for (int i = 0; i < 10; i++) {
                client.publish(topic, message);
            }
            System.out.println("Message published");
            System.out.println("topic: " + topic);
            System.out.println("message content: " + content);

            client.disconnect();
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
