package com.conekta.tfa.service.model;

public class CheckEnrollRequestModel extends AdditionalParameterModel{
	public String merchantReferenceCode;
	public String enrollServiceRun;
	public String referenceID;
	
	public String firstName;
	public String lastName;
	public String street;
	public String city;
	public String postalCode;
	public String state;
	public String country;
	public String email;
	public String phoneNumber;
	
	public CardModel cardModel;
	public String totalsCurrency;
	public String price;
	public String giftCategory;
}
