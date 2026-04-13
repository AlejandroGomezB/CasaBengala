FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /home/app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-ea-1-jdk
WORKDIR /app
COPY --from=builder /home/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]