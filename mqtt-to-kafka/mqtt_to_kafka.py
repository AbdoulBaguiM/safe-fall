import paho.mqtt.client as mqtt
from kafka import KafkaProducer

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe("acceleration")

def on_message(client, userdata, msg):
    print(f"Received message on topic {msg.topic}: {msg.payload}")
    producer.send(msg.topic, key=msg.topic.encode(), value=msg.payload)

producer = KafkaProducer(bootstrap_servers=['kafka:9092'], api_version=(0, 10, 1))
client = mqtt.Client()
client.connect("mosquitto", 1883, 60)

client.on_connect = on_connect
client.on_message = on_message

client.loop_forever()
