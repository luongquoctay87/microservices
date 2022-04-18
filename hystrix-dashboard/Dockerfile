FROM openjdk:8-jdk-alpine

# EXPOSE 9295

ARG JAR_FILE=target/*.jar

RUN mkdir /opt/hystrix-dashboard

COPY ${JAR_FILE} /opt/hystrix-dashboard/hystrix-dashboard.jar

ENTRYPOINT ["java","-jar","/opt/hystrix-dashboard/hystrix-dashboard.jar"]
