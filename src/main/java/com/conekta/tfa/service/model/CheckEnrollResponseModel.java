package com.conekta.tfa.service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "CheckenrollResponse")
@JsonIgnoreProperties( value = {"createDate"}, allowGetters = true)
public class CheckEnrollResponseModel {
	
	@Id
	@Column(name = "id_checkenroll", nullable = false)
	public String idCheckenroll;
	
	@Column(name = "referenceId", length = 50, nullable = false)
	public String referenceId;
	
	@Column(name = "commerceIndicator", length = 50, nullable = true)
	public String commerceIndicator; 
	
	@Column(name = "eciRaw", length = 20, nullable = true)
	public String eciRaw; 
	
	@Column(name = "cavv", length = 100, nullable = true)
	public String cavv; 
	
	@Column(name = "avv", length = 100, nullable = true)
	public String avv; 
	
	@Column(name = "authenticationResult", length = 50, nullable = true)
	public String authenticationResult; 
	
	@Column(name = "eci", length = 20, nullable = true)
	public String eci; 
	
	@Column(name = "authenticationStatusMessage", length = 50, nullable = true)
	public String authenticationStatusMessage;
	
	@Column(name = "paresStatus", length = 50, nullable = true)
	public String paresStatus;
	
	@Column(name = "proxyPAN", length = 50, nullable = true)
	public String proxyPan; 
	
	@Column(name = "decision", length = 50, nullable = true)
	public String decision;        
	
	@Column(name = "authenticationTransactionID", length = 100, nullable = true)
	public String authenticationTransactionId;
	
	@Column(name = "merchantReferenceCode", length = 100, nullable = true)
	public String merchantReferenceCode; 
	
	@Column(name = "authenticationPath", length = 50, nullable = true)
	public String authenticationPath; 
	
	@Column(name = "veresEnrolled", length = 10, nullable = true)
	public String veresEnrolled; 
	
	@Column(name = "reasonCode", length = 10, nullable = true)
	public String reasonCode; 
	
	@Column(name = "paReq", length = 1000, nullable = true)
	public String paReq;
	
	@Column(name = "requestId", length = 100, nullable = true)
	public String requestId;
	
	@Column(name = "acsUrl", length = 3000, nullable = true)
	public String acsURL;
	
	@Column(name = "requestToken", length = 200, nullable = true)
	public String requestToken;
	
	@Column(name = "specificationVersion", length = 10, nullable = true)
	public String specificationVersion;
	
	@Column(name = "xID", length = 100, nullable = true)
	public String xID;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate = new Date();
}
