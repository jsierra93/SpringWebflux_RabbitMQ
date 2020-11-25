FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER Jorge Sierra "jsierra93@hotmail.com"
VOLUME  /tmp
EXPOSE 8081
ARG JAR_FILE=./build/libs/webflux-rabbitmq-jms-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]