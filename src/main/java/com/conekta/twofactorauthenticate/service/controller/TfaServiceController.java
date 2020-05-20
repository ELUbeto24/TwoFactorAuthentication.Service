package com.conekta.twofactorauthenticate.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conekta.twofactorauthenticate.service.model.CheckEnrollRequestModel;
import com.conekta.twofactorauthenticate.service.model.OrderModel;
import com.conekta.twofactorauthenticate.service.model.Response;
import com.conekta.twofactorauthenticate.service.model.ValidateAuthenticationRequestModel;
import com.conekta.twofactorauthenticate.service.service.ICheckEnrollService;
import com.conekta.twofactorauthenticate.service.service.IGenerateJwtService;
import com.conekta.twofactorauthenticate.service.service.IValidateService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/V1/TwoFactorAuthenticateService")
public class TfaServiceController {

	@Autowired
	private IGenerateJwtService generateJwt;
	
	@PostMapping("generatejwt")
	public ResponseEntity<String> createJwt(@RequestBody OrderModel order) {
		return new ResponseEntity<String>(generateJwt.createJwt(order), HttpStatus.ACCEPTED);
	}
	
	@Autowired
	private ICheckEnrollService checkEnrollService;
	
	@PostMapping("checkenroll")
	public ResponseEntity<Response> CheckEnroll(@RequestBody 
												@Validated CheckEnrollRequestModel checkEnrollRequest) {
		return new ResponseEntity<Response>(checkEnrollService.processCheckEnroll(checkEnrollRequest), 
											HttpStatus.ACCEPTED);
	}
	
	@Autowired
	private IValidateService validateService;
	
	@PostMapping("validate")
	public ResponseEntity<Response> Validate(@RequestBody 
											 @Validated ValidateAuthenticationRequestModel validateRequest){
		return new ResponseEntity<Response>(validateService.processValidate(validateRequest), 
											HttpStatus.ACCEPTED);
	}
}
