
import paho.mqtt.client as mqtt
import requests

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe("acceleration")

def on_message(client, userdata, msg):
    payload = msg.payload.decode()
    x, y, z = payload.split(", ")
    x_accel = float(x.split(":")[1])
    y_accel = float(y.split(":")[1])
    z_accel = float(z.split(":")[1])
    # Convert the data to the format required by the BigML prediction API
    data = {
        "ADXL345_x": x_accel,
        "ADXL345_y": y_accel,
        "ADXL345_z": z_accel
    }

    print("Data", data)
    # Make a REST call to the BigML prediction API
    headers = {
        'content-type': 'application/json',
    }
    url = f"https://bigml.io/andromeda/prediction?username=AbdoulBaguiM;api_key=97960277d127a8afd22b0d927dda3cac00acb56e"
    response = requests.post(url, headers=headers, json={'model': "model/6389010eaba2df2a5c00031a", 'input_data': data})
    prediction = response.json()


client = mqtt.Client()
client.connect("178.128.46.160", 1883, 60)
client.on_connect = on_connect
client.on_message = on_message

client.loop_forever()
