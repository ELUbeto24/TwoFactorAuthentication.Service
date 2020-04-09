package com.conekta.tfa.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conekta.tfa.service.cybersource.Cybersource;
import com.conekta.tfa.service.model.Response;
import com.conekta.tfa.service.model.ValidateAuthenticationRequestModel;
import com.conekta.tfa.service.model.ValidateResponseModel;
import com.conekta.tfa.service.repository.IValidateRespository;
import com.conekta.tfa.service.utility.Utilities;

/**
* <h1>Validate Service Class</h1>
* This class is responsible for applying 
* the relevant validation rules and invoking 
* the save to the database.
*
* @author  Edilberto López Ubaldo
* @version 1.0
* @since   2020-04-02
*/

@Service
public class ValidateServiceImpl implements IValidateService{
	@Autowired
	private Cybersource cybersource;
	
	@Autowired
	private IValidateRespository validateRepository;
	
	@Autowired
	private Utilities utilities;
	
	/** Invokes cybersource to validate client authentication.
	 * @param Request the Validate model.
	 * @return return object Response.
	 */
	@Override
	public Response processValidate(ValidateAuthenticationRequestModel validateRequest) {
		
		Response response = new Response();
		
		/** The parameters that are required are validated.
		 *  TypeValidate:
		 *  1 = CheckEnroll
		 *  2 = Validate 
		 */
		if(utilities.validateRequiredParams(validateRequest, 2)) {
			
			// The Validate method of cybersource is invoked to validate the user's token.
			response = cybersource.Validate(validateRequest);
			
			// Validate that the dodeStatus is equal to 200.
			if(response.codeStatus == 200) {
				try {

					// The save event is invoked to store the validate response.
					validateRepository.save((ValidateResponseModel) response.objectResponse);
					
				} catch (Exception e) {
					// TODO: handle exception
					response.codeStatus = -600;
					response.message = e.getMessage();
					
				}
			}
		} else {
			response.codeStatus = -500;
			response.message = "Parameters are missing";
		}
		
		return response;
	}
}
