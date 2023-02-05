FROM openjdk:8-jdk-alpine

RUN apk add --no-cache curl tar bash

# install Spark
ARG SPARK_VERSION=3.3.1
ARG HADOOP_VERSION=3
RUN curl -s https://archive.apache.org/dist/spark/spark-3.3.1/spark-3.3.1-bin-hadoop3.tgz | tar -xz -C /opt/
RUN ln -s /opt/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION} /opt/spark

# install Python and dependencies for pyspark
RUN apk add --no-cache python3
RUN python3 -m ensurepip
RUN pip3 install --upgrade pip
RUN pip3 install pyspark

# set environment variables
ENV SPARK_HOME /opt/spark
ENV PYSPARK_PYTHON python3

RUN echo "spark.jars.packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.3.1" >> $SPARK_HOME/conf/spark-defaults.conf

# copy the Spark Streaming code to the Docker image
COPY streaming.py /app/
WORKDIR /app

# run the Spark Streaming code
CMD ["spark-submit", "streaming.py"]
