FROM openjdk:14-alpine

# Set environment variables
ENV SPARK_HOME /spark

# Install wget
RUN apk update \
    && apk add wget

# Install Spark
RUN wget https://downloads.apache.org/spark/spark-3.3.1/spark-3.3.1-bin-hadoop3.tgz \
    && tar xzf spark-3.3.1-bin-hadoop3.tgz \
    && mv spark-3.3.1-bin-hadoop3 $SPARK_HOME \
    && rm spark-3.3.1-bin-hadoop3.tgz

# Set Spark environment variables
ENV PATH=$PATH:$SPARK_HOME/bin

# Install Python and pyspark
RUN apk add python3 \
    && apk add py3-pip \
    && pip3 install pyspark

# Copy the script into the container
COPY streaming.py .

# Run the script
CMD [ "spark-submit", "streaming.py" ]

