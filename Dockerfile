# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="famibica.com"

# Add a volume pointing to /tmp
VOLUME /tmp

ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} challenge.jar
#ENTRYPOINT java -DRABBITMQ_HOST=${RABBITMQ_HOST} -DRABBITMQ_VHOST=${RABBITMQ_VHOST} -DRABBITMQ_USERNAME=${RABBITMQ_USERNAME} -DRABBITMQ_PASSWORD=${RABBITMQ_PASSWORD} -Djava.security.egd=file:/dev/./urandom  -jar challenge.jar