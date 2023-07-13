FROM openjdk:8
EXPOSE 8080
ADD target/discovery-service.jar discovery-service.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/discovery-service.jar"]
