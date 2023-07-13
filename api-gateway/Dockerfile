FROM openjdk:8
EXPOSE 8080
ADD target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/api-gateway.jar"]
