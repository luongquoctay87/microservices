FROM openjdk:8-jdk-alpine

# EXPOSE 8761

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/service-registry

COPY ${JAR_FILE} /opt/service-registry/service-registry.jar

ENTRYPOINT ["java","-jar","/opt/service-registry/service-registry.jar"]
