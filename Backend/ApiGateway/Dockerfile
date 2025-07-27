# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/ApiGateway-0.0.1-SNAPSHOT.jar ApiGateway.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "ApiGateway.jar"]
