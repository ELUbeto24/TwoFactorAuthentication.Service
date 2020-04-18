package com.conekta.tfa.service.configuration;

import java.nio.ByteBuffer;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.conekta.tfa.service.TwoFactorAuthenticationApplication;

@Configuration
public class DataSourceConfiguration {
	@Resource
    private Environment env;
	
	private static Logger log = LoggerFactory.getLogger(TwoFactorAuthenticationApplication.class);
	
	@Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties appDataSourceProperties() {
        return new DataSourceProperties();
    }
	
	@Bean
    @Primary
    public DataSource appDataSource() {
    	log.info("ENTRO A SECRETS MANAGER");
		
    	String secretName = env.getProperty("spring.aws.secretsmanager.secretName");
		String endpoint = env.getProperty("spring.aws.secretsmanager.endpoint");
		String region = env.getProperty("spring.aws.secretsmanager.region");

		AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
		AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
		clientBuilder.setEndpointConfiguration(config);
		AWSSecretsManager client = clientBuilder.build();

		@SuppressWarnings("unused")
		ByteBuffer binarySecretData;
		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
				.withSecretId(secretName);
		GetSecretValueResult getSecretValueResponse = null;
		
		try {
			
			getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

		} catch(ResourceNotFoundException e) {
			log.error("The requested secret " + secretName + " was not found");
		} catch (InvalidRequestException e) {
			log.error("The request was invalid due to: " + e.getMessage());
		} catch (InvalidParameterException e) {
			log.error("The request had invalid params: " + e.getMessage());
		}

		if(getSecretValueResponse == null) {
			return null;
		}

		// Decrypted secret using the associated KMS CMK
		// Depending on whether the secret was a string or binary, one of these fields will be populated
		String secret = getSecretValueResponse.getSecretString();
		
		if(secret == null) {
			return null;
		}
			
			try {
				
				JSONObject jsonSecret = new JSONObject(secret);
				String dataBaseUrl = jsonSecret.getString("Connection_bd_2fa_test");
				
				JSONObject configDataBase = new JSONObject("{" + dataBaseUrl.replace("http://", "").replace("=", ":") + "}");
				
				String host = configDataBase.getString("Host"); 
				String port = configDataBase.getString("Port"); 
				String dbname = configDataBase.getString("Database"); 
				String username = configDataBase.getString("Username"); 
				String password = configDataBase.getString("Password"); 
				
				appDataSourceProperties().setUrl("jdbc:postgresql://" + host + ":" + port + "/" + dbname);
				appDataSourceProperties().setUsername(username);
				appDataSourceProperties().setPassword(password);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        return appDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
