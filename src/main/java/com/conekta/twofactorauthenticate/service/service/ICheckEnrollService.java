package com.conekta.twofactorauthenticate.service.service;

import com.conekta.twofactorauthenticate.service.model.CheckEnrollRequestModel;
import com.conekta.twofactorauthenticate.service.model.Response;

public interface ICheckEnrollService {
	public Response processCheckEnroll(CheckEnrollRequestModel checkEnrollResponse);
}
