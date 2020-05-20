# TwoFactorAuthentication.Service

Description
Comprehensive fraud management service in addition, Interacts with CyberSource Payer uses JavaScript and ICS services to provide authentication.

Spring Boot 4

Getting stared

Prerequisites:

    - Java 9
    
    - Maven
    
    - PostgreSQL 12
    
    - Docker
    
    - AWS Credentials / Configure

Structure of the code

Package:

    - Api 
    
        - controller
        
        - repository (REPOSITORY)
        
        - model (MODEL, ENTITY, CUSTOM VALIDATE & UTILS)
        
        - service (LOGIC BUSSINES)
        
        - configuration (CONFIG FILES & GlobalException)
        
        - utility (PARSE CYBERSOURCE RESPONSE TO MODEL)
        
        - cybersource (INOCATION OF CYBERSOURCE PROCESSES)
        
#Docker

        - docker build -t "twofactorauthenticate-service" . 

        - docker run --name twofactorauthenticate-service-docker -p 9595:9000 -e AWS_ACCESS_KEY_ID=xxx -e AWS_SECRET_ACCESS_KEY=xxx twofactorauthenticate-service:latest


