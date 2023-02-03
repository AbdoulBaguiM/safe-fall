package com.jonas.mqttKafka.mqtt;

import com.jonas.mqttKafka.kafka.SensorKafkaProducer;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

@Data
public class SensorMqttClient {
    String subTopic;

    SensorKafkaProducer sensorKafkaProducer;

    public SensorMqttClient(String subTopic, SensorKafkaProducer sensorKafkaProducer) {
        this.subTopic = subTopic;
        this.sensorKafkaProducer = sensorKafkaProducer;
    }

    private MqttClient initMqttClientConnection() throws MqttException {
        MqttClient mqttClient = new MqttClient(MqttAppConfigs.MQTT_BROKER, MqttAppConfigs.MQTT_SUB_CLIENT_ID);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(MqttAppConfigs.MQTT_USERNAME);
        connOpts.setPassword(MqttAppConfigs.MQTT_PASSWORD.toCharArray());
        connOpts.setAutomaticReconnect(true);
        connOpts.setConnectionTimeout(10);
        connOpts.setKeepAliveInterval(30);

        System.out.println("Connecting to Broker at " + MqttAppConfigs.MQTT_BROKER);
        mqttClient.connect(connOpts);
        System.out.println("Connected");
        return mqttClient;
    }

    private void subscribeToTopic(MqttClient mqttClient) throws MqttException {
        mqttClient.setCallback(new OnMessageCallback(sensorKafkaProducer));
        System.out.println("Subscribing client to topic: " + subTopic);
        mqttClient.subscribe(subTopic, Integer.parseInt(MqttAppConfigs.MQTT_QOS));
        System.out.println("Subscribed...");
    }

    public void connect() {
        try {
            MqttClient mqttClient = initMqttClientConnection();
            subscribeToTopic(mqttClient);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(MqttClient mqttClient) throws MqttException {
        mqttClient.disconnect();
        System.out.println("Exiting");
        System.exit(0);
    }

}
