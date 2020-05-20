package com.conekta.twofactorauthenticate.service.model;

public class ValidateAuthenticationRequestModel {
	public String payerAuthValidateServiceRun;
	public String merchantID;
	public String merchantReferenceCode;
	
	public CardModel cardModel;
	public String unitPrice;
	
	public String authenticationTransactionID;
}
