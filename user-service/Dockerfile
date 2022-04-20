FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/user-service

COPY ${JAR_FILE} /opt/user-service/user-service.jar

ENTRYPOINT ["java","-jar","/opt/user-service/user-service.jar"]