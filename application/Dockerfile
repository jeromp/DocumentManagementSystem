FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-D spring.profiles.active=docker,dev","-jar","/app.jar"]