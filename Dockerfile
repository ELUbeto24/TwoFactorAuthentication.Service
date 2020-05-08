# our base build image
FROM openjdk:8-jdk-alpine
LABEL "com.conekta.vendor"="Conekta"
LABEL "com.conekta.product"="Conekta Two Factor Authenticate Service"
LABEL "com.conekta.maintainer"="Edilberto Lopez <edilberto.lopez@conekta.com>"
LABEL "version"="0.1"
LABEL "description"="Description Comprehensive fraud management service in addition, Interacts with CyberSource Payer uses JavaScript and ICS services to provide authentication."

# build for release

ENV JAVA_OPTS="$JAVA_OPTS -javaagent:./newrelic/newrelic.jar"

ENV NEW_RELIC_APP_NAME = "TFA Service (Development)"

COPY "./target/TwoFactorAuthentication.Service-0.0.1-SNAPSHOT.jar" "2fa-service.jar"
COPY ./tfa.properties .
COPY ./conekta_mx.p12 .

EXPOSE 9595

ENTRYPOINT ["java","-jar","2fa-service.jar"]