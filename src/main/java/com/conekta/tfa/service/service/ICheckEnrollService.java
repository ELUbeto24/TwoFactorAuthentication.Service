package com.conekta.tfa.service.service;

import com.conekta.tfa.service.model.CheckEnrollRequestModel;
import com.conekta.tfa.service.model.Response;

public interface ICheckEnrollService {
	public Response processCheckEnroll(CheckEnrollRequestModel checkEnrollResponse);
}
