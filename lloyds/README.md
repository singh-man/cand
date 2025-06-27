### For detailed requirements go through

### Pre Requisite

JDK 11 environment.

### 1. To Build
Open a terminal in the directory cc-lloyds and run 

```
mvn clean install
```

### 2. To RUN
To get current time as Human Readable
```
java -cp target/time-1.0-SNAPSHOT.jar.original com.lloyds.time.service.TimeService
```

OR

To get custom time as Human Readable like 16:30 to human-readable
```
java -cp target/time-1.0-SNAPSHOT.jar.original com.lloyds.time.service.TimeService 16:30
```

OR

To run as REST API
```
java -jar target/time-1.0-SNAPSHOT.jar
```

### Self Documented APIs
On any browser go to 
```
http://localhost:8080/swagger-ui.html
```


> Note: Dockerfile is work in progress