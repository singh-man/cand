FROM openjdk:latest

ADD target/service-registry-0.0.1-SNAPSHOT.jar serviceregistry.jar

ENTRYPOINT ["java", "-jar","serviceregistry.jar"]

EXPOSE 8761