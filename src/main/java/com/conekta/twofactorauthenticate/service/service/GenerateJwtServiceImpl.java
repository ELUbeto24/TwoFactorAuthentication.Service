package com.conekta.twofactorauthenticate.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conekta.twofactorauthenticate.service.cybersource.Cybersource;
import com.conekta.twofactorauthenticate.service.model.OrderModel;
import com.conekta.twofactorauthenticate.service.utility.Utilities;

/**
* <h1>Generate JWT Service Class</h1>
* This class is in charge of applying validation 
* rules and invoking the JWT creation method.
*
* @author  Edilberto López Ubaldo
* @version 1.0
* @since   2020-04-02
*/

@Service
public class GenerateJwtServiceImpl implements IGenerateJwtService{
	@Autowired
	Utilities utilities;
	
	@Autowired
	private Cybersource cybersource;

	/** JWT creation to start with requests to cybersource.
	 * @param Request the Order model.
	 * @return return object Response.
	 */
	@Override
	public String createJwt(OrderModel order) {
		String responseJwt = null;
		/** The parameters that are required are validated.
		 *  TypeValidate:
		 *  1 = GenerateJwt
		 *  2 = CheckEnroll
		 *  3 = Validate 
		 */
		if (utilities.validateRequiredParams(order.orderDetails, 1)) {
			// The Validate method of cybersource is invoked to generate JWT that will allow requests to cybersource.
			responseJwt = cybersource.createJWT(order);
		}else {
			responseJwt = "Parameters are missing";
		}
		
		return responseJwt;
	}
	
}
