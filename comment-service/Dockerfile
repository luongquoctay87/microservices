FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/comment-service

COPY ${JAR_FILE} /opt/comment-service/comment-service.jar

ENTRYPOINT ["java","-jar","/opt/comment-service/comment-service.jar"]