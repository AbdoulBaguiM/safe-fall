# Fall Detection System
## Introduction

This project involves building a fall detection device using an Arduino MKR1010 and MPU6050 sensor, and a machine learning model built and trained on BigML analysis tool with the Sysfall dataset downloaded from Kaggle. 

## Architecture
![Fall Detection System Architecture](<https://github.com/AbdoulBaguiM/safe-fall/blob/main/architecture.png>)

The project consists of several services that work together to detect falls. The data flow is as follows:
1. The fall detection device sends data to the Mosquitto MQTT broker.
2. The MQTT-to-Kafka service listens to the Mosquitto broker, reads incoming MQTT data, and sends it to the Kafka broker.
3. The Spark Streaming service listens to the Kafka broker, processes the incoming data in real-time, and detects falls using the machine learning model.
4. When a fall is detected, the relevant data is sent to Firebase, and notifications are sent to the caregivers' or family members' phones.

## Docker Compose Configuration

This project uses Docker Compose to create and manage the containers for the various services involved in the fall detection system.

## Services

1. **Zookeeper**: Zookeeper is used for coordination between Kafka brokers.

2. **Kafka**: Kafka is a message broker that is used to manage incoming MQTT data and make it available to other services.

3. **Mosquitto**: Mosquitto is an MQTT broker that is used to receive incoming data from the fall detection device.

4. **MQTT-to-Kafka**: A Python script that listens to the Mosquitto broker, reads incoming MQTT data, and sends it to the Kafka broker for further processing.

5. **Spark Streaming**: Spark Streaming is a real-time data processing framework that is used to analyze the incoming data and detect falls.
