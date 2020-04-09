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
	
	@Column(name = "proxyPAN", length = 10, nullable = true)
	public String proxyPan; 
	
	@Column(name = "decisio", length = 10, nullable = true)
	public String decisio;        
	
	@Column(name = "authenticationTransactionID", length = 50, nullable = true)
	public String authenticationTransactionId;
	
	@Column(name = "merchantReferenceCode", length = 50, nullable = true)
	public String merchantReferenceCode; 
	
	@Column(name = "authenticationPath", length = 10, nullable = true)
	public String authenticationPath; 
	
	@Column(name = "veresEnrolled", length = 5, nullable = true)
	public String veresEnrolled; 
	
	@Column(name = "reasonCode", length = 5, nullable = true)
	public String reasonCode; 
	
	@Column(name = "paReq", length = 600, nullable = true)
	public String paReq;
	
	@Column(name = "requestId", length = 50, nullable = true)
	public String requestId;
	
	@Column(name = "acsUrl", length = 2000, nullable = true)
	public String acsURL;
	
	@Column(name = "proofXml", length = 2000, nullable = true)
	public String proofXML;
	
	@Column(name = "requestToken", length = 100, nullable = true)
	public String requestToken;
	
	@Column(name = "specificationVersion", length = 5, nullable = true)
	public String specificationVersion;
	
	@Column(name = "xID", length = 50, nullable = true)
	public String xID;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate = new Date();
}
