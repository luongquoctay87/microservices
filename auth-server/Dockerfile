FROM openjdk:8-jdk-alpine

# EXPOSE 6650

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/auth-server

COPY ${JAR_FILE} /opt/auth-server/auth-server.jar

ENTRYPOINT ["java","-jar","/opt/auth-server/auth-server.jar"]
