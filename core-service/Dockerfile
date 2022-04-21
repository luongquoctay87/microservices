FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/core-service

COPY ${JAR_FILE} /opt/core-service/core-service.jar

ENTRYPOINT ["java","-jar","/opt/core-service/core-service.jar"]