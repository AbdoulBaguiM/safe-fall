from pyspark.sql import SparkSession
from pyspark.sql.functions import *
from pyspark.sql.types import *
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

def process_rdd(df, epoch_id):
    if not df.rdd.isEmpty():
        # Initialize Firebase
        cred = credentials.Certificate("/app/serviceAccountKey.json")
        firebase_admin.initialize_app(cred)
        db = firestore.client()

        # Write data to Firebase Realtime Database
        data = df.toPandas().to_dict("records")
        for item in data:
            db.collection("fall_detection_data").document().set(item)

spark = SparkSession.builder.appName("Spark Streaming with MongoDB").getOrCreate()

df = spark \
  .readStream \
  .format("kafka") \
  .option("kafka.bootstrap.servers", "localhost:9092") \
  .option("subscribe", "acceleration") \
  .load()

df.writeStream\
  .foreachBatch(process_rdd)\
  .start()\
  .awaitTermination()

