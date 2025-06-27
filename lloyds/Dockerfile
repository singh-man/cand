# For Build
FROM maven:3.8.5-eclipse-temurin-8-alpine AS mani
ADD ./ /opt/
WORKDIR /opt/
RUN mvn clean package

# Use the build jar from above to make final image
FROM openjdk:11-jre-slim-bullseye
WORKDIR /root/
COPY --from=0 /opt/target/time-1.0-SNAPSHOT.jar /opt/time.jar
# or use below
# COPY --from=mani /opt/target/time-1.0-SNAPSHOT.jar /opt/pay.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/pay.jar"]
# CMD ["java", "-jar", "/opt/a.jar"]
# docker build --rm -t pay .
# docker run --rm -v /home/manish/dev/mani-projects/payments/data:/root/data -p 8083:8080 pay