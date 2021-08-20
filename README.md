# Document Management System

The Document Management System is a RESTful Service for managing documents and their metadata. 
It's built with Spring Boot.

### Requirements

- Java 11
- Maven
- PostgreSQL

## Run Application

Add in application.yaml your database and credentials. You can also specify the root path of your file server.

```
spring:
  datasource:
    url: YOUR_URL
    username: YOUR_USERNAME
    password: YOUR_PASSWORD
...    
storage:
  files:
    rootPath: YOUR_PATH
```



### 1.1 Hello World
[http://localhost:8080/](http://localhost:8080/)



