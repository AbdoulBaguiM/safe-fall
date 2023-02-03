package com.jonas.mqttKafka;

import com.jonas.mqttKafka.kafka.KafkaAppConfigs;
import com.jonas.mqttKafka.kafka.SensorKafkaProducer;
import com.jonas.mqttKafka.mqtt.MqttAppConfigs;
import com.jonas.mqttKafka.mqtt.SensorMqttClient;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

public class MqttToKafkaMainTest {

    public static void main(String[] args) {
        SensorKafkaProducer sensorKafkaProducer = new SensorKafkaProducer(KafkaAppConfigs.TOPIC_NAME);
        SensorMqttClient sensorMQTTClient = new SensorMqttClient(MqttAppConfigs.MQTT_TOPIC, sensorKafkaProducer);
        sensorMQTTClient.connect();
    }

    public static CompositeConfiguration getCompositeConfiguration() {
        CompositeConfiguration config = new CompositeConfiguration();
        config.addConfiguration(new SystemConfiguration());
        config.addConfiguration(new PropertiesConfiguration());
        try {
            config.addConfiguration(new PropertiesConfiguration("application.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return config;
    }
}
