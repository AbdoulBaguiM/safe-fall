FROM python:3.7-alpine

RUN apk update && \
    apk add --no-cache --virtual .build-deps build-base && \
    pip install paho-mqtt kafka-python && \
    apk del .build-deps

COPY mqtt_to_kafka.py /app/

WORKDIR /app

CMD ["python", "mqtt_to_kafka.py"]
