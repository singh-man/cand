FROM openjdk:latest

Volume /tmp

ADD /target/*.jar atm-machine.jar

ENTRYPOINT ["java", "-jar","atm-machine.jar"]

EXPOSE 8080
