# Use python:3.7-alpine as the base image
FROM python:3.7-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . .

# Install the required packages
RUN pip install paho-mqtt requests

# Run the script
CMD ["python", "prediction.py"]
