FROM openjdk:8-jdk-alpine
COPY "./target/TwoFactorAuthentication.Service-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 9898
ENTRYPOINT ["java","-jar","app.jar"]