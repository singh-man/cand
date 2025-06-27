# Atm Machine
Project is built on JDK 11 and spring boot.
## CI
- When developer commit code
- Code get build automatically
- Docker image is prepared and tagged properly
- Docker Image is pushed to private AWS ECR
- [Pipeline](https://gitlab.com/at98/atm-machine/-/pipelines)

## API Details

| API Type| API | URL |Body|
| ------| ------ | ------ |------|
| POST| Initilaise ATM with Currency Notes | http://localhost:8080/atm | {"id":1,"note5":20,"note10":30,"note20":30,"note50":10} |
| POST| User can withdraw money from his account | http://localhost:8080/atm/withdraw | {"accountId":123456789,"pin":1234,"amount":400} |
| GET| User can check balance | http://localhost:8080/atm/getBalance/accountId/123456789/pin/1234 | |
| GET| Check ATM status to see money status | http://localhost:8080/atm/find/1 | |

## PostMan Collection location
https://gitlab.com/at98/atm-machine/-/blob/main/atm-machine.postman_collection.json 

## Getting started

### Running project with command line
- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Windows PowerShell and Change directory (cd) to folder atm-Machine
- RUN [build-run.ps1](https://gitlab.com/at98/atm-machine/-/blob/main/build-run.ps1)
- Application will be available on http://localhost:8080/atm
- [build-run.ps1](https://gitlab.com/at98/atm-machine/-/blob/main/build-run.ps1) build a jar and run test coverge using jacoco, find files in /target folder

### Running project in Eclipse or your favorite IDE
- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse or any IDE
- File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip (May differ depending IDE)
- Select the right project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application
- You are all Set

### Swagger-UI

<host>/swagger-ui.html

### Unit test
[ATMServiceTest](https://gitlab.com/at98/atm-machine/-/blob/main/src/test/java/com/zinkworks/atmmachine/service/ATMServiceTest.java)

### Code Covergae
Code coverage seperately can be run by 
- mvnw clean jacoco:prepare-agent test jacoco:report

