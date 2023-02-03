package com.jonas.mqttKafka.entities;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Sensor {
    private UUID guid = UUID.randomUUID();
    private String name;
    private String type;
    private String location;
    private Date timestamp = new Date();
    private String data;
}
