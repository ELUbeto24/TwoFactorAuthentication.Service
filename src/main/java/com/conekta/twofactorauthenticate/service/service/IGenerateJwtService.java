package com.conekta.twofactorauthenticate.service.service;

import com.conekta.twofactorauthenticate.service.model.OrderModel;

public interface IGenerateJwtService {
	public String createJwt(OrderModel order);
}
