# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/WalletService-0.0.1-SNAPSHOT.jar WalletService.jar
EXPOSE 8600
ENTRYPOINT ["java", "-jar", "WalletService.jar"]
