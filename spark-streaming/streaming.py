from pyspark.sql import SparkSession
from pyspark.sql.functions import *
from pyspark.sql.types import *
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import requests
import math

def process_rdd(df, epoch_id):
    data = df.rdd.map(lambda x: x[1]).collect()
    for value in data:
        value = value.decode('utf-8')
        x, y, z = value.split(", ")
        x = math.ceil(float(x.split(":")[1]))
        y = math.ceil(float(y.split(":")[1]))
        z = math.ceil(float(z.split(":")[1]))
        
        data = {
            "ADXL345_x": x,
            "ADXL345_y": y,
            "ADXL345_z": z
        }

        headers = {
            'content-type': 'application/json',
        }
        url = "BIGML_URL"
        response = requests.post(url, headers=headers, json={'model': "MODEL_ID", 'input_data': data})
        prediction = response.json()
        
        # Save prediction result to the document
        result = prediction["output"]
        
        # Check if prediction is "Fall"
        if result == "Fall":
            # Save prediction result to Firestore
            data = {
                "result": result,
		"values": data
            }
            db.collection("fall_detection_data").document().set(data)

# Initialize Firebase App
cred = credentials.Certificate("/app/serviceAccountKey.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

spark = SparkSession.builder.appName("Spark Streaming with MongoDB").getOrCreate()

df = spark \
  .readStream \
  .format("kafka") \
  .option("kafka.bootstrap.servers", "kafka:9092") \
  .option("subscribe", "acceleration") \
  .load()

df.writeStream\
  .foreachBatch(process_rdd)\
  .start()\
  .awaitTermination()
