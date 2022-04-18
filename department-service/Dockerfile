FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/department-service

COPY ${JAR_FILE} /opt/department-service/department-service.jar

ENTRYPOINT ["java","-jar","/opt/department-service/department-service.jar"]