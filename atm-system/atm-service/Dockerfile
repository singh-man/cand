FROM openjdk:latest

ADD target/atm-service-0.0.1-SNAPSHOT.jar atmservice.jar

ENTRYPOINT ["java", "-jar","atmservice.jar"]

EXPOSE 9002