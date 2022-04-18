FROM openjdk:8-jdk-alpine

# EXPOSE 9296

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/cloud-config-server

COPY ${JAR_FILE} /opt/cloud-config-server/cloud-config-server.jar

ENTRYPOINT ["java","-jar","/opt/cloud-config-server/cloud-config-server.jar"]
