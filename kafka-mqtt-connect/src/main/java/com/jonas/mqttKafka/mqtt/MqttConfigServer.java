package com.jonas.mqttKafka.mqtt;

import lombok.Data;
import org.apache.commons.configuration.CompositeConfiguration;

import static com.jonas.mqttKafka.MqttToKafkaMainTest.getCompositeConfiguration;

@Data
public class MqttConfigServer {
    private String host;
    private String port;
    private String clientId;
    private Integer qos;
    private String username;
    private String password;

    public MqttConfigServer() {
        this.host = getConfig().getString("mqtt.host");
        this.port = getConfig().getString("mqtt.port");
        this.clientId = getConfig().getString("mqtt.client.id");
        this.qos = getConfig().getInt("mqtt.qos");
        this.username = getConfig().getString("mqtt.username");
        this.password = getConfig().getString("mqtt.password");
    }

    public CompositeConfiguration getConfig() {
        return getCompositeConfiguration();
    }
}
