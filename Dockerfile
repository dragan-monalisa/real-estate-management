FROM maven:3-eclipse-temurin-21-alpine AS BUILD

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=BUILD /app/target/real-estate-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]