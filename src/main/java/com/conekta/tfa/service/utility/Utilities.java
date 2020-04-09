package com.conekta.tfa.service.utility;

import java.util.Map;
import java.util.UUID;

import com.conekta.tfa.service.model.CheckEnrollRequestModel;
import com.conekta.tfa.service.model.CheckEnrollResponseModel;
import com.conekta.tfa.service.model.ValidateAuthenticationRequestModel;
import com.conekta.tfa.service.model.ValidateResponseModel;

/**
* <h1>Utilities Class</h1>
* This class contains methods that 
* are useful for other classes.
*
* @author  Edilberto López Ubaldo
* @version 1.0
* @since   2020-04-02
*/

@UtilitiesAnnotation
public class Utilities {
	
	//private static Logger LOG = LoggerFactory.getLogger(TwoFactorAuthenticationApplication.class);
	
	/** Convert the response from cybersource to CheckEnroll model.
	 * @param String referenceID.
	 * @param Map cybersource response.
	 * @return return CheckEnrollResponseModel.
	 */
	public CheckEnrollResponseModel convertCheckEnrollResponseToModel(String referenceID, Map<?, ?> cybersourceResponse) {
		CheckEnrollResponseModel checkEnrollResponse = new CheckEnrollResponseModel();
		
		if (cybersourceResponse != null && !referenceID.isEmpty()) {
			checkEnrollResponse.idCheckenroll = UUID.randomUUID().toString();
			checkEnrollResponse.referenceId = referenceID;
			checkEnrollResponse.proxyPan = (String) cybersourceResponse.get("payerAuthEnrollReply_proxyPAN");
			checkEnrollResponse.decisio = (String) cybersourceResponse.get("decision");
			checkEnrollResponse.authenticationTransactionId = (String) cybersourceResponse.get("payerAuthEnrollReply_authenticationTransactionID");
			checkEnrollResponse.merchantReferenceCode = (String) cybersourceResponse.get("merchantReferenceCode");
			checkEnrollResponse.authenticationPath = (String) cybersourceResponse.get("payerAuthEnrollReply_authenticationPath");
			checkEnrollResponse.veresEnrolled = (String) cybersourceResponse.get("payerAuthEnrollReply_veresEnrolled");
			checkEnrollResponse.reasonCode = (String ) cybersourceResponse.get("payerAuthEnrollReply_reasonCode");
			checkEnrollResponse.paReq = (String) cybersourceResponse.get("payerAuthEnrollReply_paReq");
			checkEnrollResponse.proofXML = (String) cybersourceResponse.get("payerAuthEnrollReply_proofXML");
			checkEnrollResponse.requestId = (String) cybersourceResponse.get("requestID");
			checkEnrollResponse.acsURL = (String) cybersourceResponse.get("payerAuthEnrollReply_acsURL");
			checkEnrollResponse.requestToken = (String) cybersourceResponse.get("requestToken");
			checkEnrollResponse.specificationVersion = (String) cybersourceResponse.get("payerAuthEnrollReply_specificationVersion");
			checkEnrollResponse.xID = (String) cybersourceResponse.get("payerAuthEnrollReply_xid");
		}
		
		return checkEnrollResponse;
	}
	
	/** Convert the response from cybersource to Validate model.
	 * @param String transactionID.
	 * @param Map cybersource response.
	 * @return return CheckEnrollResponseModel.
	 */
	public ValidateResponseModel convertValidateResponseToModel(String transactionId, Map<?, ?> cybersourceResponse) {
		ValidateResponseModel validateResponse = new ValidateResponseModel();
		
		if(cybersourceResponse != null && !transactionId.isEmpty()) {
			validateResponse.idValidate = UUID.randomUUID().toString();
			validateResponse.authenticationTransactionID = transactionId;
			validateResponse.statusMessage = (String) cybersourceResponse.get("payerAuthValidateReply_authenticationStatusMessage");
			validateResponse.merchantReferenceCode = (String) cybersourceResponse.get("merchantReferenceCode");
			validateResponse.commerceIndicator = (String) cybersourceResponse.get("payerAuthValidateReply_commerceIndicator");
			validateResponse.reasonCode = (String) cybersourceResponse.get("payerAuthValidateReply_reasonCode");
			validateResponse.cavvAlgorithm = (String) cybersourceResponse.get("payerAuthValidateReply_cavvAlgorithm");
			validateResponse.eciRaw = (String) cybersourceResponse.get("payerAuthValidateReply_eciRaw");
			validateResponse.eci = (String) cybersourceResponse.get("payerAuthValidateReply_eci");
			validateResponse.authenticationResult = (String) cybersourceResponse.get("payerAuthValidateReply_authenticationResult");
			validateResponse.requestID = (String) cybersourceResponse.get("requestID");
			validateResponse.paresStatus = (String) cybersourceResponse.get("payerAuthValidateReply_paresStatus");
			validateResponse.requestToken = (String) cybersourceResponse.get("requestToken");
			validateResponse.xID = (String) cybersourceResponse.get("payerAuthValidateReply_xid");
			validateResponse.cavv = (String) cybersourceResponse.get("payerAuthValidateReply_cavv");
			validateResponse.avv = (String) cybersourceResponse.get("payerAuthValidateReply_avv");
			validateResponse.specificationVersion = (String) cybersourceResponse.get("payerAuthValidateReply_specificationVersion");
		}
		
		return validateResponse;
	}
	
	/** The parameters that are required are validated.
	 * @param Object objectRequest.
	 * @param Integer typeValidate
	 * @return return Boolean Response.
	 */
	public Boolean validateRequiredParams(Object objectRequest, Integer typeValidate) {
		// 
		// CheckEnrollRequest validation.
		if(typeValidate == 1) {
			CheckEnrollRequestModel checkEnrollRequest = new CheckEnrollRequestModel();
			checkEnrollRequest = (CheckEnrollRequestModel)objectRequest;
			
			boolean statusMerchantRefCode = checkEnrollRequest.merchantReferenceCode.isBlank(); // Mandatory parameter.
			boolean statusReferenceID = checkEnrollRequest.referenceID.isBlank(); // Mandatory parameter.
			boolean statusPrice = checkEnrollRequest.price.isBlank(); // Mandatory parameter.
			boolean statusCurrency = checkEnrollRequest.totalsCurrency.isBlank(); // Mandatory parameter.
			boolean statusCardNumber = checkEnrollRequest.cardAccountNumber.isBlank(); // Mandatory parameter.
			boolean statusExpMonth = checkEnrollRequest.cardExpirationMonth.isBlank(); // Mandatory parameter.  
			boolean statusExpYear = checkEnrollRequest.cardExpirationYear.isBlank(); // Mandatory parameter.
			boolean statusGiftCategory = checkEnrollRequest.giftCategory.isBlank(); // Mandatory parameter.
			
			if(statusMerchantRefCode || statusReferenceID ||
					statusPrice || statusCurrency ||
						statusCardNumber || statusExpMonth ||
							statusExpYear || statusGiftCategory) {
				return false;
			}
		}else {

			// ValidateRequest validation.
			ValidateAuthenticationRequestModel validateRequest = new ValidateAuthenticationRequestModel();
			validateRequest = (ValidateAuthenticationRequestModel)objectRequest;
			
			boolean statusCardNumber = validateRequest.cardAccountNumber.isBlank(); // Mandatory parameter.
			boolean statusExpMonth = validateRequest.cardExpirationMonth.isBlank(); // Mandatory parameter.  
			boolean statusExpYear = validateRequest.cardExpirationYear.isBlank(); // Mandatory parameter.
			boolean statusRefCode = validateRequest.merchantReferenceCode.isBlank(); // Mandatory parameter.
			boolean statusPrice = validateRequest.unitPrice.isBlank(); // Mandatory parameter.
			boolean statusCurrency = validateRequest.currency.isBlank(); // Mandatory parameter.
			boolean statusCardType = validateRequest.cardCardType.isBlank(); // Mandatory parameter.
			boolean statusTranId = validateRequest.authenticationTransactionID.isBlank(); // Mandatory parameter.
			
			// If one of the parameters is blank, it will not be able 
			// to continue the request and will return a false value.
			if(statusCardNumber || statusExpMonth || 
					statusExpYear || statusRefCode ||
						statusPrice || statusCurrency ||
							statusCardType || statusTranId) {
				return false;
			}
		}
		return true;
	}
}
