package com.conekta.tfa.service.utility;

import java.util.Map;
import java.util.UUID;

import com.conekta.tfa.service.model.CheckEnrollRequestModel;
import com.conekta.tfa.service.model.CheckEnrollResponseModel;
import com.conekta.tfa.service.model.OrderDetailsModel;
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
			
			checkEnrollResponse.commerceIndicator = (String) cybersourceResponse.get("payerAuthEnrollReply_commerceIndicator");
			checkEnrollResponse.eciRaw = (String) cybersourceResponse.get("payerAuthEnrollReply_eciRaw");
			checkEnrollResponse.cavv = (String) cybersourceResponse.get("payerAuthEnrollReply_cavv");
			checkEnrollResponse.avv = (String) cybersourceResponse.get("payerAuthEnrollReply_ucafAuthenticationData");
			checkEnrollResponse.authenticationResult = (String) cybersourceResponse.get("payerAuthEnrollReply_authenticationResult");
			checkEnrollResponse.eci = (String) cybersourceResponse.get("payerAuthEnrollReply_eci");
			checkEnrollResponse.authenticationStatusMessage = (String) cybersourceResponse.get("payerAuthEnrollReply_authenticationStatusMessage");
			checkEnrollResponse.paresStatus = (String) cybersourceResponse.get("payerAuthEnrollReply_paresStatus");
			checkEnrollResponse.proxyPan = (String) cybersourceResponse.get("payerAuthEnrollReply_proxyPAN");
			checkEnrollResponse.decision = (String) cybersourceResponse.get("decision");
			checkEnrollResponse.authenticationTransactionId = (String) cybersourceResponse.get("payerAuthEnrollReply_authenticationTransactionID");
			checkEnrollResponse.merchantReferenceCode = (String) cybersourceResponse.get("merchantReferenceCode");
			checkEnrollResponse.authenticationPath = (String) cybersourceResponse.get("payerAuthEnrollReply_authenticationPath");
			checkEnrollResponse.veresEnrolled = (String) cybersourceResponse.get("payerAuthEnrollReply_veresEnrolled");
			checkEnrollResponse.reasonCode = (String ) cybersourceResponse.get("payerAuthEnrollReply_reasonCode");
			checkEnrollResponse.paReq = (String) cybersourceResponse.get("payerAuthEnrollReply_paReq");
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
			validateResponse.avv = (String) cybersourceResponse.get("payerAuthValidateReply_ucafAuthenticationData");
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
		int statusCardNumber = 0;
		int statusExpMonth = 0;
		int statusExpYear = 0;
		int statusPrice = 0;
		int statusCurrency = 0;
		
		switch (typeValidate) {
		case 1:
			// Generate JWT validation.
			OrderDetailsModel validateOrderRequest = new OrderDetailsModel();
			validateOrderRequest = (OrderDetailsModel)objectRequest;
			
			int statusOrderNumber = validateOrderRequest.OrderNumber.length();
			int statusAmount = validateOrderRequest.Amount.length();
			
			if (statusOrderNumber == 0 || statusAmount == 0) {
				return false;
			}
			
			break;
			
		case 2:
			
			// CheckEnrollRequest validation.
			CheckEnrollRequestModel checkEnrollRequest = new CheckEnrollRequestModel();
			checkEnrollRequest = (CheckEnrollRequestModel)objectRequest;
			
			int statusMerchantRefCode = checkEnrollRequest.merchantReferenceCode.length(); // Mandatory parameter.
			int statusReferenceID = checkEnrollRequest.referenceID.length(); // Mandatory parameter.
			
			statusPrice = checkEnrollRequest.price.length(); // Mandatory parameter.
			statusCurrency = checkEnrollRequest.totalsCurrency.length(); // Mandatory parameter.
			statusCardNumber = checkEnrollRequest.cardModel.cardAccountNumber.length(); // Mandatory parameter.
			statusExpMonth = checkEnrollRequest.cardModel.cardExpirationMonth.length(); // Mandatory parameter.  
			statusExpYear = checkEnrollRequest.cardModel.cardExpirationYear.length(); // Mandatory parameter.
			
			int statusGiftCategory = checkEnrollRequest.giftCategory.length(); // Mandatory parameter.
			
			if(statusMerchantRefCode == 0 || statusReferenceID == 0 ||
					statusPrice == 0 || statusCurrency == 0 ||
						statusCardNumber == 0  || statusExpMonth == 0  ||
							statusExpYear == 0  || statusGiftCategory == 0 ) {
				return false;
			}
			
			break;
			
		case 3:

			// ValidateRequest validation.
			ValidateAuthenticationRequestModel validateRequest = new ValidateAuthenticationRequestModel();
			validateRequest = (ValidateAuthenticationRequestModel)objectRequest;
			
		    statusCardNumber = validateRequest.cardModel.cardAccountNumber.length(); // Mandatory parameter.
			statusExpMonth = validateRequest.cardModel.cardExpirationMonth.length(); // Mandatory parameter.  
			statusExpYear = validateRequest.cardModel.cardExpirationYear.length(); // Mandatory parameter.
			statusPrice = validateRequest.unitPrice.length(); // Mandatory parameter.
			statusCurrency = validateRequest.currency.length(); // Mandatory parameter.
			
			int statusRefCode = validateRequest.merchantReferenceCode.length(); // Mandatory parameter.
			int statusTranId = validateRequest.authenticationTransactionID.length(); // Mandatory parameter.
			
			// If one of the parameters is blank, it will not be able 
			// to continue the request and will return a false value.
			if(statusCardNumber == 0 || statusExpMonth == 0 || 
					statusExpYear == 0 || statusRefCode == 0 ||
						statusPrice == 0 || statusCurrency == 0 ||
							statusTranId == 0) {
				return false;
			}
			
			break;
			
		default:
			break;
		}
		
		return true;
	}
	
	public String evaluateCardType(String cardNumber) {
		cardNumber = cardNumber.substring( 0, 1 );
		String cardType = null;
		
		switch (cardNumber) {
		case "4":
			cardType = "001";
			break;
		case "5":	
			cardType = "002";
			break;
		case "3":
			cardType = "003";
			break;
		default:
			break;
		}
		
		return cardType;
	}
}
