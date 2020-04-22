# TwoFactorAuthentication.Service

Description
Comprehensive fraud management service in addition, Interacts with CyberSource Payer uses JavaScript and ICS services to provide authentication.

Spring Boot 4

Getting stared

Prerequisites:

    - Java 9
    
    - Maven
    
    - PostgreSQL 12
    

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

docker build -t "2fa-service" . 

docker run --name 2fa-service-docker -p 9000:8080 -e AWS_ACCESS_KEY_ID=AKIAYHLOT24NNIN45D7J -e AWS_SECRET_ACCESS_KEY=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 2fa-service:latest


