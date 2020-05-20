package com.conekta.twofactorauthenticate.service.service;

import com.conekta.twofactorauthenticate.service.model.Response;
import com.conekta.twofactorauthenticate.service.model.ValidateAuthenticationRequestModel;

public interface IValidateService {
	public Response processValidate(ValidateAuthenticationRequestModel validateRequest);
}
