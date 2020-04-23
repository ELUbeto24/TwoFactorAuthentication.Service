package com.conekta.tfa.service.model;

public class ValidateAuthenticationRequestModel extends AdditionalParameterModel{
	public String payerAuthValidateServiceRun;
	public String merchantID;
	public String merchantReferenceCode;
	
	public CardModel cardModel;
	public String unitPrice;
	public String currency;
	
	public String authenticationTransactionID;
	
}
