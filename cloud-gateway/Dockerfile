FROM openjdk:8-jdk-alpine

# EXPOSE 8080

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/cloud-gateway

COPY ${JAR_FILE} /opt/cloud-gateway/cloud-gateway.jar

ENTRYPOINT ["java","-jar","/opt/cloud-gateway/cloud-gateway.jar"]
