package com.conekta.tfa.service.service;

import com.conekta.tfa.service.model.Response;
import com.conekta.tfa.service.model.ValidateAuthenticationRequestModel;

public interface IValidateService {
	public Response processValidate(ValidateAuthenticationRequestModel validateRequest);
}
