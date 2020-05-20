package com.conekta.twofactorauthenticate.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conekta.twofactorauthenticate.service.cybersource.Cybersource;
import com.conekta.twofactorauthenticate.service.model.CheckEnrollRequestModel;
import com.conekta.twofactorauthenticate.service.model.CheckEnrollResponseModel;
import com.conekta.twofactorauthenticate.service.model.Response;
import com.conekta.twofactorauthenticate.service.repository.ICheckEnrollRepository;
import com.conekta.twofactorauthenticate.service.utility.Utilities;

/**
* <h1>CheckEnroll Service Class</h1>
* This class is responsible for applying 
* the relevant validation rules and invoking 
* the save to the database.
*
* @author  Edilberto López Ubaldo
* @version 1.0
* @since   2020-04-02
*/

@Service
public class CheckEnrollServiceImpl implements ICheckEnrollService {
	
	@Autowired
	private ICheckEnrollRepository checkEnrollRepository;
	
	@Autowired
	private Cybersource cybersource;
	
	@Autowired
	private Utilities utilities;
	
	/** Invoke cybersource to validate if the card is rolled to 3D-S.
	 * @param Request the CheckEnroll model.
	 * @return return object Response.
	 */
	@Override
	public Response processCheckEnroll(CheckEnrollRequestModel checkEnrollRequest) {
		// A response type variable is initialized that should return the method.
		Response response = new Response();
		
		/** The parameters that are required are validated.
		 *  TypeValidate:
		 *  1 = GenerateJwt
		 *  2 = CheckEnroll
		 *  3 = Validate 
		 */
		if(utilities.validateRequiredParams(checkEnrollRequest, 2)) {
			
			// card type evaluation
			checkEnrollRequest.cardModel.cardType = 
					utilities.evaluateCardType(checkEnrollRequest.cardModel.cardAccountNumber);
			
			// The cybersource CheckEnroll method is invoked to validate that the card is rolled with 3D-S.
			response = cybersource.CheckEnroll(checkEnrollRequest);
			
			// Validate that the dodeStatus is equal to 200.
			if (response.codeStatus == 200) {
				try {
					checkEnrollRepository.save( (CheckEnrollResponseModel) response.objectResponse);	
				} catch (Exception e) {
					// TODO: handle exception
					response.codeStatus = -600;
					response.message = e.getMessage();
				}
			}
		}else {
			response.codeStatus = -500;
			response.message = "Parameters are missing";
		}
		
		return response;
	}
}
