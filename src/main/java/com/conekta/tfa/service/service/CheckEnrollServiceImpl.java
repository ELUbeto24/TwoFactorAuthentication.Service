package com.conekta.tfa.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conekta.tfa.service.cybersource.Cybersource;
import com.conekta.tfa.service.model.CheckEnrollRequestModel;
import com.conekta.tfa.service.model.CheckEnrollResponseModel;
import com.conekta.tfa.service.model.Response;
import com.conekta.tfa.service.repository.ICheckEnrollRepository;
import com.conekta.tfa.service.utility.Utilities;
import com.newrelic.api.agent.NewRelic;

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
					response.objectResponse = utilities.authorizationProcess(response.objectResponse, 1);
				} catch (Exception e) {
					// TODO: handle exception
					response.codeStatus = -600;
					response.message = e.getMessage();
					
					NewRelic.noticeError("Exception in processCheckEnroll: " + e.getMessage());
				}
			}
		}else {
			response.codeStatus = -500;
			response.message = "Parameters are missing";
		}
		
		return response;
	}
}
