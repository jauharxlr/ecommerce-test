FROM openjdk:8
EXPOSE 8082
ADD target/bookstore-service.jar bookstore-service.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/bookstore-service.jar"]
