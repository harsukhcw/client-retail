package com.opl.retail.client.payment;


import com.opl.retail.api.exceptions.payment.GatewayException;
import com.opl.retail.api.model.payment.GatewayRequest;
import com.opl.retail.api.model.payment.GatewayResponse;
import com.opl.retail.api.model.payment.PaymentTypeRequest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class GatewayClient {

	private static Logger logger = LoggerFactory.getLogger(GatewayClient.class);

	/** private static final String PAYOUT = "/save_payment_info";*/
	private static final String UPDATE = "/update_payment_status";
	private static final String SKIP_PAYMENT = "/skip_payment";
	private static final String PERSONAL_LOAN_INPRINCIPLE = "/personal_loan_inPrinciple";
	private static final String SEND_INPRINCIPLE_FOR_SIDBI= "/sendInPrincipleForSidbi";
	private static final String SEND_MAIL_TO_ALL_FUND_PROVIDER= "/sendMailToAllFundProvider";
	private static final String GET_PAYMENT_STATUS = "/get_payment_by_status";
	private static final String GET_PAYMENT_BY_APPID = "/get_payment_by_appId";
	private static final String GET_PAYMENT_DETAILS = "/get_payment_details";
	private static final String GET_INPRINCIPLE_BY_APPLICATIONID = "/getInPrincipleByApplicationId";
	private static final String GET_BANK_LOGO_URL_BY_ORG_ID= "/getBankLogoUrl";
	
	private static final String REQ_AUTH = "req_auth";
	private static final String GATEWAY_ERROR ="Gateway Service is not available";
	private static final String SBI_SPECIFIC = "SbiSpecific";
	private static final String SIDBI_SPECIFIC = "SidbiSpecific";
	
	
	private static final String SAVE_PAYMENT_DETAIL_WITH_PENDING_STATUS = "/payment/savePaymentDetailWithPendingStatus"; 
	private static final String CALL_STATUS_API_AND_UPDATE_PAYMENT = "/payment/callStatusAPIAndUpdatePaymentStatus"; 
	private static final String UPDATE_PAYMENT_AND_INVOICE = "/payment/updatePaymentAndInvoice"; 
	private static final String GET_INVOICE_BY_APPLICATION_ID = "/payment/getInvoiceDetailsByApplicationId"; 
	 
	
	
	/**
	//not used till now 
	/*private static final String CASH_CHEQUE = "/save_payment_details";
	private static final String UPDATE_PAYMENT_DETAILS = "/update_payment_details";
	
    //not used API - admin panel
	/*private static final String GET_PAYMENT_LIST = "/get_payment_list";
    private static final String UPDATE_CREDIT_DATE = "/update_credit_date";
	private static final String CONFIRM_PAYMENT = "/confirm_payment";
	private static final String SAVE_REMARKS = "/save_remarks";*/
	private String gatewayBaseUrl;
	private RestTemplate restTemplate;

	public GatewayClient(String gatewayBaseUrl) {
		this.gatewayBaseUrl = gatewayBaseUrl;
		restTemplate = new RestTemplate();

	}

	/**
	 * TO integrate to Payment Gateway interface for making payments
	 * 
	 * @param request
	 *            hold the Required information like mobileNo,email
	 * @return
	 * @throws GatewayException
	 */
	/**public Map<String,Object> payout(PaymentRequest  request) throws GatewayException {
		String url = gatewayBaseUrl.concat(PAYOUT);
		logger.info("Payout URL==>", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("req_auth", "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<PaymentRequest>(request, headers), Map.class)
					.getBody();
			return restTemplate.postForObject(url, request, Map.class);
		} catch (Exception e) {
			logger.info("Throw Exception while calling gateway client for payment ------------>");
			e.printStackTrace();
			throw new GatewayException("Gateway Service is not available");
		}
	}*/
	

	/**
	 * TO update Payment status inside database
	 * 
	 * @param request
	 *            hold the Required information like mobileNo,email
	 * @return
	 * @throws GatewayException
	 */
	public GatewayResponse updatePayment(GatewayRequest gatewayRequest) throws GatewayException {
		String url = gatewayBaseUrl.concat(UPDATE);
		logger.info("update Payment URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<GatewayRequest> entity = new HttpEntity<GatewayRequest>(gatewayRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for updatepayment ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	/**
	 * TO skip Payment for White Label
	 * 
	 * @param request
	 *            
	 * @return
	 * @throws GatewayException
	 */
	public GatewayResponse skipPayment(GatewayRequest request) throws GatewayException {
		Long appId = null;
		String skipPayment = null;
		logger.info("Gateway Data==>{}",request);
		if(request != null && request.getApplicationId() != null) {
			appId = request.getApplicationId();
			if(request.getIsSbiSpecific() != null && request.getIsSbiSpecific()) {
				skipPayment = SBI_SPECIFIC;
			}
			
			if(request.getSpecificSkipType() != null) {
				skipPayment = request.getSpecificSkipType();   /** .contains(SBI_SPECIFIC) ? SBI_SPECIFIC : SIDBI_SPECIFIC; */ 
			}
		}
		logger.info("Into Skip Payment Client with ApplicationId==>{}",appId);
		String url = gatewayBaseUrl.concat(SKIP_PAYMENT+"/"+appId+"/"+skipPayment);
		logger.info("skip Payment URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<GatewayRequest> entity = new HttpEntity<GatewayRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for skippayment ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	/**
	 * TO send InPrinciple for SIDBI Specific
	 * 
	 * @param request
	 *            
	 * @return
	 * @throws GatewayException
	 */
	public Boolean sendInprincipleForSidbi(Long applicationId , Long userId) throws GatewayException {
		String url = gatewayBaseUrl.concat(SEND_INPRINCIPLE_FOR_SIDBI).concat("/"+applicationId).concat("/"+userId);
		logger.info("Send InPrinciple For Sidbi URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<GatewayRequest> entity = new HttpEntity<GatewayRequest>(null, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, Boolean.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for Send InPrinciple For Sidbi------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	/**
	 * TO send InPrinciple for All FP Providers
	 * 
	 * @param request
	 *            
	 * @return
	 * @throws GatewayException
	 */
	public GatewayResponse sendMailToAllFundProvider(Long applicationId) throws GatewayException {
		String url = gatewayBaseUrl.concat(SEND_MAIL_TO_ALL_FUND_PROVIDER).concat("/"+applicationId);
		logger.info("Send Mail To All Fund Provider From MCA URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<GatewayRequest> entity = new HttpEntity<GatewayRequest>(null, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for Send InPrinciple For Sidbi------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	/**
	 * TO send InPrinciple for Personal Loan
	 * 
	 * @param request
	 *            
	 * @return
	 * @throws GatewayException
	 */
	public Boolean personalLoanInPrinciple(GatewayRequest request) throws GatewayException {
		String url = gatewayBaseUrl.concat(PERSONAL_LOAN_INPRINCIPLE);
		logger.info("personal loan InPrinciple URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<GatewayRequest> entity = new HttpEntity<GatewayRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, Boolean.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for Personal Loan InPrinciple------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	
	/**
	 * Getting Payment Status whether it is exists or not.
	 * @param request
	 * @return
	 * @throws GatewayException
	 */
	
	public GatewayRequest getPaymentStatus(GatewayRequest request) throws GatewayException {
		String url = gatewayBaseUrl.concat(GET_PAYMENT_STATUS);
		logger.info("Get Payment by Status URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<GatewayRequest> entity = new HttpEntity<GatewayRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayRequest.class).getBody();
		} catch (Exception e) {
			logger.info("Throw Exception while calling gateway client for get payment status ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	/**
	 * Getting Payment By app Id whether it is exists or not.
	 * @param request
	 * @return
	 * @throws GatewayException
	 */
	
	public GatewayRequest getPaymentByappId(GatewayRequest request) throws GatewayException {
		String url = gatewayBaseUrl.concat(GET_PAYMENT_BY_APPID);
		logger.info("Get Payment By AppId URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<GatewayRequest> entity = new HttpEntity<GatewayRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayRequest.class).getBody();
		} catch (Exception e) {
			logger.info("Throw Exception while calling gateway client for get payment by application Id------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	/**
	 * Saving Payment Details for CASH/CHEQUE.
	 * @param request
	 * @return
	 * @throws GatewayException
	 */
	
	public GatewayResponse getPaymentDetails(PaymentTypeRequest request) throws GatewayException {
		String url = gatewayBaseUrl.concat(GET_PAYMENT_DETAILS);
		logger.info("CASH/CHEQUE URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<PaymentTypeRequest> entity = new HttpEntity<PaymentTypeRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for get payment details ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	
	/**
	 * Getting bank logo url on Org Id 
	 * @param OrganisationId
	 * @return
	 * @throws GatewayException
	 */
	
	public String getBankLogoUrlByOrgId(Long orgId) throws GatewayException {
		String url = gatewayBaseUrl.concat(GET_BANK_LOGO_URL_BY_ORG_ID).concat("/"+orgId);
		logger.info("Get bank log URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<PaymentTypeRequest> entity = new HttpEntity<PaymentTypeRequest>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for get bank logo ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	//for mobile use
	public GatewayResponse getInPrincipleByApplicationId(Long applicationId) throws GatewayException {
		String url = gatewayBaseUrl.concat(GET_INPRINCIPLE_BY_APPLICATIONID);
		logger.info("Get Inprinciple By ApplicationId URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<Long> entity = new HttpEntity<Long>(applicationId, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for get payment details ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	public GatewayResponse savePaymentDetailWithPendingStatus(GatewayRequest gatewayRequest) throws GatewayException {
		String url = gatewayBaseUrl.concat(SAVE_PAYMENT_DETAIL_WITH_PENDING_STATUS);
		logger.info("savePaymentDetailWithPendingStatus URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(REQ_AUTH, "true");
		try {
			HttpEntity<Object> entity = new HttpEntity<Object>(gatewayRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for savePaymentDetailWithPendingStatus  ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	public Object callStatusApiAndUpdatePayment(Long applicationId) throws GatewayException {
		String url = gatewayBaseUrl.concat(CALL_STATUS_API_AND_UPDATE_PAYMENT);
		logger.info("callStatusApiAndUpdatePayment URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(REQ_AUTH, "true");
		try {
			HttpEntity<Object> entity = new HttpEntity<Object>(new GatewayRequest(applicationId), headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody().getData();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for callStatusApiAndUpdatePayment  ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	
	public Object updatePaymentAndCreateInvoice(Long applicationId) throws GatewayException {
		String url = gatewayBaseUrl.concat(UPDATE_PAYMENT_AND_INVOICE);
		logger.info("updatePaymentAndCreateInvoice URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(REQ_AUTH, "true");
		try {
			HttpEntity<Object> entity = new HttpEntity<Object>(new GatewayRequest(applicationId), headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody().getData();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for updatePaymentAndCreateInvoice  ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	public Map<String, Object> getInvoiceDetailByApplicationId(Long applicationId) throws GatewayException {
		String url = gatewayBaseUrl.concat(GET_INVOICE_BY_APPLICATION_ID);
		logger.info("updatePaymentAndCreateInvoice URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(REQ_AUTH, "true");
		try {
			HttpEntity<Object> entity = new HttpEntity<Object>(new GatewayRequest(applicationId), headers);
			return (Map<String, Object>) restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody().getData();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for updatePaymentAndCreateInvoice  ------------>",e);
			throw new GatewayException(GATEWAY_ERROR);
		}
	}
	/**public GatewayResponse savePaymentDetails(PaymentTypeRequest request) throws GatewayException {
		String url = gatewayBaseUrl.concat(CASH_CHEQUE);
		logger.info("CASH/CHEQUE URL==>", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<PaymentTypeRequest> entity = new HttpEntity<PaymentTypeRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.info("Throw Exception while calling gateway client for save payment details ------------>");
			e.printStackTrace();
			throw new GatewayException("Gateway Service is not available");
		}
	}*/
	
	/**
	 * Update Payment Details for CASH/CHEQUE.
	 * @param request
	 * @return
	 * @throws GatewayException
	 */
	
	
	/**public GatewayResponse updatePaymentDetails(PaymentTypeRequest request) throws GatewayException {
		String url = gatewayBaseUrl.concat(UPDATE_PAYMENT_DETAILS);
		logger.info("CASH/CHEQUE URL==>", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<PaymentTypeRequest> entity = new HttpEntity<PaymentTypeRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.info("Throw Exception while calling gateway client for update payment details ------------>");
			e.printStackTrace();
			throw new GatewayException("Gateway Service is not available");
		}
	}*/
	
	/**
	 * Get Payment Details for CASH/CHEQUE.
	 * @param request
	 * @return
	 * @throws GatewayException
	 */

	/**public GatewayResponse getPaymentList(PaymentTypeRequest paymentTypeRequest) throws GatewayException {
		String url = gatewayBaseUrl.concat(GET_PAYMENT_LIST);
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<PaymentTypeRequest> entity = new HttpEntity<PaymentTypeRequest>(paymentTypeRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.info("Throw Exception while calling gateway client for get payment list------------>");
			e.printStackTrace();
			throw new GatewayException("Error while getting payment list");
		}
	}*/

    /**public GatewayResponse updateCreditDate(PaymentTypeRequest paymentTypeRequest) throws GatewayException {
        String url = gatewayBaseUrl.concat(UPDATE_CREDIT_DATE);
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
        try {
			HttpEntity<PaymentTypeRequest> entity = new HttpEntity<PaymentTypeRequest>(paymentTypeRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
        } catch (Exception e) {
        	logger.info("Throw Exception while calling gateway client for update credit date ------------>");
            e.printStackTrace();
            throw new GatewayException("Error while updating credit date");
        }
    }*/

	/**public GatewayResponse confirmPayment(PaymentTypeRequest paymentTypeRequest) throws GatewayException {
		String url = gatewayBaseUrl.concat(CONFIRM_PAYMENT);
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<PaymentTypeRequest> entity = new HttpEntity<PaymentTypeRequest>(paymentTypeRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.info("Throw Exception while calling gateway client for confirm payment ------------>");
			e.printStackTrace();
			throw new GatewayException("Error while confirming payment");
		}
	}*/

	/**public GatewayResponse saveRemarks(PaymentTypeRequest paymentTypeRequest) throws GatewayException {
		String url = gatewayBaseUrl.concat(SAVE_REMARKS);
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<PaymentTypeRequest> entity = new HttpEntity<PaymentTypeRequest>(paymentTypeRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GatewayResponse.class).getBody();
		} catch (Exception e) {
			logger.info("Throw Exception while calling gateway client for save remarks ------------>");
			e.printStackTrace();
			throw new GatewayException("Error while saving remarks");
		}
	}*/
}
