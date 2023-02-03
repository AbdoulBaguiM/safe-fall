# Build stage
FROM maven:3.6-jdk-11 as build

# Set the working directory
WORKDIR /app

# Copy the project files into the container
COPY . .

# Run the build command
RUN mvn clean package

# Production stage
FROM openjdk:11 as Production

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/ ./target/

RUN mv target/k*.jar target/app.jar

# Run the jar file
CMD ["java", "-jar", "target/app.jar"]