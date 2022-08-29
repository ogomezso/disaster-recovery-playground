ARG BUILD_HOME=/app

# Builder ############################################
FROM maven:3.6.0-jdk-11-slim AS builder

RUN  apt-get update \
  && apt-get install -y wget \
  && rm -rf /var/lib/apt/lists/*

ARG OTEL_VERSION=1.15.0 
ARG PROMETHEUS_AGENT_VERSION=0.17.0

ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME  

COPY src $APP_HOME/src
COPY pom.xml $APP_HOME
RUN mvn -f $APP_HOME/pom.xml clean package  

# App ############################################
FROM adoptopenjdk/openjdk11:ubi  

COPY --from=builder $APP_HOME/target/java-client-jar-with-dependencies.jar app.jar

EXPOSE 38080

ENV APP_NAME="java-producer"  

CMD ["java", "-jar", "app.jar","/config/config.properties"]
