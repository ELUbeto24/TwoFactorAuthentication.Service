package com.conekta.tfa.service.service;

import com.conekta.tfa.service.model.OrderModel;

public interface IGenerateJwtService {
	public String createJwt(OrderModel order);
}
