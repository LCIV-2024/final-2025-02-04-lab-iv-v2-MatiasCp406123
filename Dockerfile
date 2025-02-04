FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar final-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/final-service.jar"]