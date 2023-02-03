package com.jonas.mqttKafka.mqtt;

import com.jonas.mqttKafka.kafka.SensorKafkaProducer;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.Timestamp;

public class OnMessageCallback implements MqttCallback {

    private final SensorKafkaProducer sensorKafkaProducer;

    OnMessageCallback(SensorKafkaProducer sensorKafkaProducer) {
        this.sensorKafkaProducer = sensorKafkaProducer;
    }


    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection to Broker messaging lost! " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println("Time:\t" + time +
                "  Topic:\t" + topic +
                "  Message:\t" + message.toString() +
                "  QoS:\t" + message.getQos());
        sensorKafkaProducer.publishMessages(message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("DeliveryComplete --------- " + token.isComplete());
    }
}
