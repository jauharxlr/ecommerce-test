FROM openjdk:8
EXPOSE 8083
ADD target/user-service.jar user-service.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/user-service.jar"]
