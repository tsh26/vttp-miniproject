FROM maven:eclipse-temurin AS builder

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /src/target/mini-project-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080
ENV SPRING_REDIS_HOST=localhost
ENV SPRING_REDIS_PORT=6379
ENV SPRING_REDIS_DATABASE=0
ENV SPRING_REDIS_USERNAME=
ENV SPRING_REDIS_PASSWORD=
ENV LTA_APIKEY=abc123

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar