from pyspark.sql import SparkSession
from pyspark.sql.functions import *
from pyspark.sql.types import *

def process_rdd(df, epoch_id):
    if not df.rdd.isEmpty():
        df.write\
            .format("com.mongodb.spark.sql.DefaultSource")\
            .option("uri", "mongodb://localhost:27017/")\
            .option("database", "fall_detection_db")\
            .option("collection", "fall_detection_data")\
            .mode("append")\
            .save()

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

