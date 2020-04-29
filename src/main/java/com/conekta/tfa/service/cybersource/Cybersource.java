package com.conekta.tfa.service.cybersource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.conekta.tfa.service.configuration.PropertySettings;
import com.conekta.tfa.service.model.CheckEnrollRequestModel;
import com.conekta.tfa.service.model.OrderModel;
import com.conekta.tfa.service.model.Response;
import com.conekta.tfa.service.model.ValidateAuthenticationRequestModel;
import com.conekta.tfa.service.utility.Utilities;
import com.cybersource.ws.client.*;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
 
import javax.crypto.spec.SecretKeySpec;

/**
* <h1>Cybersource class</h1>
* This class is in charge of invoking 
* the necessary methods for TFA, 
* CreateJWT 
* CheckEnroll 
* Validate.
*
* @author  Edilberto López Ubaldo
* @version 1.0
* @since   2020-04-02
*/

@CybersourceAnnotation
public class Cybersource {
	//private static Logger log = LoggerFactory.getLogger(TwoFactorAuthenticationApplication.class);
	
	@Autowired
	private PropertySettings propertySettings;
	
	@Autowired
	private Utilities utilities;
	
	/** JWT creation to start with requests to cybersource.
	 * @param Request the Order model.
	 * @return return object Response.
	 */
	public String createJWT(OrderModel order) {
		
		// Initialize variable we will return.
		String requestJwt = null;
		
		try {
			
			// Extract the keys that are in the configuration and are assigned to the variables.
			String apiKey = propertySettings.getProperty("apiKey"); // Mandatory parameter.
		    String apiIdentifier = propertySettings.getProperty("apiIdentifier"); // Mandatory parameter.
		    String orgUnitId = propertySettings.getProperty("orgUnitId"); // Mandatory parameter.
		    long ttlMillis = Long.parseLong(propertySettings.getProperty("ttlMillis")); // Mandatory parameter.
		    
		    // The JWT signature algorithm we will be using to sign the token
		    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		    long nowMillis = System.currentTimeMillis();
		    Date now = new Date(nowMillis);

		    // We will sign our JWT with our API Key
		    byte[] apiKeySecretBytes = apiKey.getBytes();
		    Key signingKey = new SecretKeySpec(apiKeySecretBytes, 
		        signatureAlgorithm.getJcaName());

		    // Let's set the JWT Claims
		    JwtBuilder builder = Jwts.builder()
		            .setId(UUID.randomUUID().toString())     
		            .setIssuedAt(now)
		            .setIssuer(apiIdentifier)
		            .claim("OrgUnitId", orgUnitId)
		            .claim("ReferenceId", order.referenceID)
		            .claim("Payload", order.orderDetails)
		            .signWith(signatureAlgorithm, signingKey);

		    // Add the expiration or TTL (Time To Live) for this JWT
		    if (ttlMillis > 0) {
		        long expMillis = nowMillis + ttlMillis;
		        Date exp = new Date(expMillis);
		        builder.setExpiration(exp);
		    }
		    
		    // Builds the JWT and serializes it to a compact, URL-safe string    
		    requestJwt = builder.compact();
		    
		}catch(Exception e) {
			// TODO: handle exception
			requestJwt = "Ocurrio un error al intentar generar token";
		}
		
	    return requestJwt;
	}
	
	/** Invoke cybersource to validate if the card is rolled to 3D-S.
	 * @param Request the CheckEnroll model.
	 * @return return object Response.
	 */
	public Response CheckEnroll(CheckEnrollRequestModel checkEnrollRequest) {
		// Declaration of variables to use within the CheckEnroll method.
		HashMap<String, String> request = new HashMap<String, String>();
		Response response = new Response(); 
		
		try {
			// HashMap is created with the values ​​of CheckEnroll request.
			request.put( "merchantReferenceCode", checkEnrollRequest.merchantReferenceCode); // Mandatory parameter.
			request.put( "payerAuthEnrollService_run", checkEnrollRequest.enrollServiceRun); // Mandatory parameter.
			request.put( "payerAuthEnrollService_referenceID", checkEnrollRequest.referenceID); // Mandatory parameter.
			
			request.put( "billTo_firstName", checkEnrollRequest.firstName ); // Optional parameter.
			request.put( "billTo_lastName", checkEnrollRequest.lastName ); // Optional parameter.
			request.put( "billTo_street1", checkEnrollRequest.street ); // Optional parameter.
			request.put( "billTo_city", checkEnrollRequest.city ); // Optional parameter.
			request.put( "billTo_postalCode", checkEnrollRequest.postalCode ); // Optional parameter.
			request.put( "billTo_state", checkEnrollRequest.state ); // Optional parameter.
			request.put( "billTo_country", checkEnrollRequest.country ); // Optional parameter.
			request.put( "billTo_email", checkEnrollRequest.email ); // Optional parameter.
			request.put( "billTo_phoneNumber", checkEnrollRequest.phoneNumber ); // Optional parameter.
			
		    request.put( "card_accountNumber", checkEnrollRequest.cardModel.cardAccountNumber ); // Mandatory parameter.
			request.put( "card_expirationMonth", checkEnrollRequest.cardModel.cardExpirationMonth ); // Mandatory parameter.
			request.put( "card_expirationYear", checkEnrollRequest.cardModel.cardExpirationYear ); // Mandatory parameter.
			request.put( "card_cardType", checkEnrollRequest.cardModel.cardType ); // Mandatory parameter.
			request.put( "purchaseTotals_currency", checkEnrollRequest.cardModel.currency ); // Mandatory parameter.

			request.put( "item_0_unitPrice", checkEnrollRequest.price ); // Mandatory parameter.
			request.put( "item_0_giftCategory", checkEnrollRequest.giftCategory ); // Mandatory parameter.
			
			// Additional Mandatory Fields
			
			request.put( "payerAuthEnrollService_MCC", 
					checkEnrollRequest.additionalParameterModel.serviceMcc ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_acquirerBin", 
					checkEnrollRequest.additionalParameterModel.serviceAcquirerBin ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_loginID", 
					checkEnrollRequest.additionalParameterModel.serviceLoginID ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_countryCode", 
					checkEnrollRequest.additionalParameterModel.serviceCountryCode ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_merchantName", 
					checkEnrollRequest.additionalParameterModel.serviceMerchantName ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_merchantID", 
					checkEnrollRequest.additionalParameterModel.serviceMerchantID ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_merchantURL", 
					checkEnrollRequest.additionalParameterModel.serviceMerchantURL ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_requestorID", 
					checkEnrollRequest.additionalParameterModel.serviceRequestorID ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_requestorName", 
					checkEnrollRequest.additionalParameterModel.serviceRequestorName ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_mobilePhone", 
					checkEnrollRequest.additionalParameterModel.serviceMobilePhone ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_productCode", 
					checkEnrollRequest.additionalParameterModel.serviceProductCode ); // Mandatory parameter.
			request.put( "payerAuthEnrollService_overridePaymentMethod", 
					checkEnrollRequest.additionalParameterModel.overridePaymentMethod ); // Mandatory parameter.
			
			// The request is issued to cybersource by passing Map request and required properties.
			Map<?, ?> responseCybersource = Client.runTransaction(request, propertySettings.readProperties());
			
			response.codeStatus = 200;
			response.message = "OK";

			// Converts the cybersource response to the CheckEnroll model.
			response.objectResponse = utilities.convertCheckEnrollResponseToModel(
					checkEnrollRequest.referenceID, responseCybersource);
			
		}catch(Exception e) {
			// TODO: handle exception
			response.codeStatus = -600;
			response.message = e.getLocalizedMessage();
			response.objectResponse = null;
		}
		
		return response;
	}
	
	/** Invokes cybersource to validate client authentication.
	 * @param Request the Validate model.
	 * @return return object Response.
	 */
	public Response Validate(ValidateAuthenticationRequestModel validateRequest) {
		// Declaration of variables to use within the Validate method.
		Response response = new Response();
		HashMap<String, String> request = new HashMap<String, String>();
		
		try {
			// HashMap is created with the values ​​of Validate request.
			request.put( "payerAuthValidateService_run", validateRequest.payerAuthValidateServiceRun ); // Mandatory parameter.
			request.put( "merchantID", propertySettings.getProperty("merchantID") ); // Mandatory parameter.
			request.put( "merchantReferenceCode", validateRequest.merchantReferenceCode ); // Mandatory parameter.
			request.put( "purchaseTotals_grandTotalAmount", validateRequest.unitPrice ); // Mandatory parameter.
			request.put( "purchaseTotals_currency", validateRequest.cardModel.currency ); // Mandatory parameter.
			request.put( "card_accountNumber", validateRequest.cardModel.cardAccountNumber ); // Mandatory parameter.
			request.put( "card_expirationMonth", validateRequest.cardModel.cardExpirationMonth ); // Mandatory parameter.
			request.put( "card_expirationYear", validateRequest.cardModel.cardExpirationYear ); // Mandatory parameter.
			request.put( "card_cardType", validateRequest.cardModel.cardType ); // Mandatory parameter.
			request.put( "payerAuthValidateService_authenticationTransactionID",validateRequest.authenticationTransactionID ); // Mandatory parameter.
			
			// The request is issued to cybersource by passing Map request and required properties.
			Map<?, ?> responseValidate  = Client.runTransaction(request, propertySettings.readProperties());
			
			response.codeStatus = 200;
			response.message = "OK";
			
			// Converts the cybersource response to the Validate model.
			response.objectResponse = utilities.convertValidateResponseToModel(
					validateRequest.authenticationTransactionID, responseValidate);
			
		} catch (Exception e) {
			// TODO: handle exception
			response.codeStatus = -600;
			response.message = e.getLocalizedMessage();
			response.objectResponse = null;
		}
		
		return response;
	}
}
