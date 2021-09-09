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
## 1 API

### 1.1 Documents
#### 1.1.1 Post Document
[http://localhost:8080/documents/](http://localhost:8080/documents/)

Multipart Form:

```
file: FILE
title: STRING
document_created: STRING (ISO DateTime, optional)
description: STRING (optional)
```

#### 1.1.2 Get Document by ID

[http://localhost:8080/documents/{id}](http://localhost:8080/documents/)

## 2 Swagger UI

[http://localhost:8080/swagger-ui.html/](http://localhost:8080/swagger-ui.html)