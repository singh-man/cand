FROM openjdk:latest

ADD target/account-service-0.0.1-SNAPSHOT.jar accountservice.jar

ENTRYPOINT ["java", "-jar","accountservice.jar"]

EXPOSE 9001