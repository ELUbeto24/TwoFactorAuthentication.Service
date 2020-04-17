package com.conekta.tfa.service;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InternalServiceErrorException;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;

@SpringBootApplication
public class TwoFactorAuthenticationApplication {
	private static Logger log = LoggerFactory.getLogger(TwoFactorAuthenticationApplication.class);

	public static void main(String[] args) {
		
		SpringApplication.run(TwoFactorAuthenticationApplication.class, args);
		getSecret();
	}
	
	public static void getSecret() {
		log.error("ENTRO A ESTA MADRE");
		
		String secretName = "2FAServiceSMTEST";
	    String region = "us-east-1";
		    
		
		    
		// Create a Secrets Manager client
		AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
		                                    .withRegion(region)
		                                    .build();
		    // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
		    // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
		    // We rethrow the exception by default.
		    @SuppressWarnings("unused")
			String secret, decodedBinarySecret;
		    
		    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
		                    .withSecretId(secretName);
		    
		    GetSecretValueResult getSecretValueResult = null;
		    
		    try {
		    	
		    	log.warn("ENTRO A getSecretValueResult");
		        getSecretValueResult = client.getSecretValue(getSecretValueRequest);
		        log.warn("SEGUIMOS EN LA FIESTA DE getSecretValueResult");
		        
		    } catch (DecryptionFailureException e) {
		        // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
		        // Deal with the exception here, and/or rethrow at your discretion.
		    	log.error("TRONO ESTA MADRE 1: " + e.getErrorMessage());
		        throw e;
		    } catch (InternalServiceErrorException e) {
		        // An error occurred on the server side.
		        // Deal with the exception here, and/or rethrow at your discretion.
		    	log.error("TRONO ESTA MADRE 2: " + e.getErrorMessage());
		        throw e;
		    } catch (InvalidParameterException e) {
		        // You provided an invalid value for a parameter.
		        // Deal with the exception here, and/or rethrow at your discretion.
		    	log.error("TRONO ESTA MADRE 3: " + e.getErrorMessage());
		        throw e;
		    } catch (InvalidRequestException e) {
		        // You provided a parameter value that is not valid for the current state of the resource.
		        // Deal with the exception here, and/or rethrow at your discretion.
		    	log.error("TRONO ESTA MADRE 4: " + e.getErrorMessage());
		        throw e;
		    } catch (ResourceNotFoundException e) {
		        // We can't find the resource that you asked for.
		        // Deal with the exception here, and/or rethrow at your discretion.
		    	log.error("TRONO ESTA MADRE 5: " + e.getErrorMessage());
		        throw e;
		    }
		    // Decrypts secret using the associated KMS CMK.
		    // Depending on whether the secret is a string or binary, one of these fields will be populated.
		    if (getSecretValueResult.getSecretString() != null) {
		    	log.info("NO ESTA VACIO");
		        secret = getSecretValueResult.getSecretString();
		        log.info("ESTE ES EL SECRETO:  " + secret);
		    }
		    else {
		        decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
		    }
		    
	}


}
