FROM openjdk:8-jdk-alpine

RUN apk --no-cache add curl
ADD target/*jar /app.jar

ENTRYPOINT ["java", "-Duser.timezone=Europe/Madrid", "-jar", "/app.jar"]

EXPOSE 8080