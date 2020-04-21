FROM openjdk:8-jdk-alpine
COPY "./target/TwoFactorAuthentication.Service-0.0.1-SNAPSHOT.jar" "2fa-service.jar"
EXPOSE 9000
ENTRYPOINT ["java","-jar","2fa-service.jar"]