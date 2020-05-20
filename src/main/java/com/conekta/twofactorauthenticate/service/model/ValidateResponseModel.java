package com.conekta.twofactorauthenticate.service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ValidateResponse")
@JsonIgnoreProperties( value = {"createDate"}, allowGetters = true)
public class ValidateResponseModel {
	@Id
	@Column(name = "idValidate", nullable = false)
	public String idValidate;
	
	@Column(name = "authenticationTransactionID", length = 100, nullable = false)
	public String authenticationTransactionID;
	
	@Column(name = "authenticationStatusMessage", nullable = true)
	public String statusMessage;
	
	@Column(name = "merchantReferenceCode", length = 50, nullable = true)
    public String merchantReferenceCode;
	
	@Column(name = "commerceIndicator", length = 50, nullable = true)
    public String commerceIndicator;
	
	@Column(name = "reasonCode", length = 10, nullable = true)
    public String reasonCode;
	
	@Column(name = "cavvAlgorithm", length = 10, nullable = true)
    public String cavvAlgorithm;
	
	@Column(name = "eciRaw", length = 10, nullable = true)
    public String eciRaw;
	
	@Column(name = "eci", length = 10, nullable = true)
    public String eci;
	
	@Column(name = "authenticationResult", length = 10, nullable = true)
    public String authenticationResult;
	
	@Column(name = "requestId", length = 100, nullable = true)
    public String requestID;
	
	@Column(name = "paresStatus", length = 10, nullable = true)
    public String paresStatus;
	
	@Column(name = "requestToken", length = 200, nullable = true)
    public String requestToken;
	
	@Column(name = "xid", length = 100, nullable = true)
    public String xID;
	
	@Column(name = "cavv", length = 100, nullable = true)
    public String cavv;
	
	@Column(name = "avv", length = 100, nullable = true)
    public String avv;

	@Column(name = "specificationVersion", length = 10, nullable = true)
    public String specificationVersion;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate = new Date();
}
