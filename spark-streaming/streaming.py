from pyspark.sql import SparkSession
from pyspark.sql.functions import *
from pyspark.sql.types import *
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

def process_rdd(df, epoch_id):
    if not df.rdd.isEmpty():
        # Initialize Firebase
        if not firebase_admin._apps:
            cred = credentials.Certificate("/app/serviceAccountKey.json")
            firebase_admin.initialize_app(cred, name='safe-fall')
        
        db = firestore.client(app=firebase_admin.get_app(name='safe-fall'))

        # Write data to Firebase Realtime Database
        rows = df.collect()
        for row in rows:
            data = {}
            for i in range(len(df.columns)):
                value = row[i]
                if isinstance(value, bytearray):
                    value = value.decode('utf-8')
                data[df.columns[i]] = value
            db.collection("fall_detection_data").document().set(data)

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
