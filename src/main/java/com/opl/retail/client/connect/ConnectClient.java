package com.opl.retail.client.connect;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.exceptions.connect.ConnectException;
import com.opl.retail.api.model.connect.ConnectCoAppRequest;
import com.opl.retail.api.model.connect.ConnectLogAuditRequest;
import com.opl.retail.api.model.connect.ConnectLogRequest;
import com.opl.retail.api.model.connect.ConnectRequest;
import com.opl.retail.api.model.connect.ConnectResponse;
import com.opl.retail.api.model.connect.ConnectStage;

public class ConnectClient {

	private static final String CONNECT_UPDATE_LOG = "/connect/update";
    private static final String CONNECT_UPDATE_STAGE = "/connect/updateStage";
	private static final String CONNECT_POST_GST = "/post/gst";
	private static final String CONNECT_POST_ITR = "/post/itr";
	private static final String CONNECT_GET_BY_APP_STAGE_BUSINESS_TYPE = "/get_by_app_stage_business_type";
	private static final String CONNECT_POST_STATEMENT = "/post/statement";
	private static final String CONNECT_POST_BUSINESS_TYPE_SELECTION = "/post/businessSelection";
	private static final String CONNECT_POST_NTB_MCQ = "/post/ntbMcq";
	private static final String CONNECT_POST_NTB_ONE_FORM_OTHER_DETAIL= "/post/oneFormOtherDetailNTB";
	private static final String CONNECT_POST_DIRECTOR_BACKGORUND = "/post/directorBackground";
	private static final String CONNECT_POST_DIR_BACK_CHANGE_STAGE= "/post/dirBackChangeStage";
	private static final String CONNECT_POST_ONE_FORM_BASIC_LOAN_DETAILS_PL = "/post/oneFormBasicLoanDetailsPL";
	private static final String CONNECT_POST_ONE_FORM_BASIC_DETAILS_PL = "/post/oneFormBasicDetailsPL";
	private static final String CONNECT_POST_CIBIL_AUTHENTICATION = "/post/oneCibilAuthentication";
	private static final String CONNECT_POST_ONE_FORM = "/post/oneform";
	private static final String CONNECT_POST_PAYMENT = "/post/payment";
	private static final String CONNECT_POST_PAYMENT_TO_MATCHES = "/post/paymentToMatches";
	private static final String CONNECT_POST_MATCHES = "/post/matches";
	private static final String CONNECT_MOBILE_STAGE = "/mobile/stage";
	private static final String CONNECT_CREATE_APP = "/mobile/create";
	private static final String CONNECT_POST_EXISTING_MCQ = "/post/existing/mcq";
	private static final String CONNECT_POST_IN_PRINCIPLE = "/post/inPrinciple";
	private static final String CONNECT_ONE_PAGER_CIBIL = "/post/one_pager_cibil";
	private static final String CHECK_IN_PRINCIPLE_FOR_GST = "/existingInprinciple/GST";
	private static final String CHECK_IN_PRINCIPLE_FOR_PAN = "/existingInprinciplePL/PAN";
	private static final String SAVE_AUDIT_LOG = "/logs/save";
	private static final String GET_AUDIT_LOG_FROM_TO_DATE = "/logs/all_from_to_date";
	private static final String GET_BUSINESS_ID_BY_APP_ID = "/get_business_type_by_app_id";
    private static final String CONNECT_CLIENT_URL = "Connect Client URL===>{}";
    private static final String GET_APPLICATION_LIST = "/getApplicationList";
    private static final String CREATE_FOR_MULTIPLE_BANK = "/createForMultipleBank";
    private static final String CONNECT_CLIENT_NOT_AVAILABLE = "Connect Client not available";
    private static final String EXCEPTION = "Exception : ";
    private static final String REQ_AUTH = "req_auth";
    private static final String CONNECT_GET_VENDER_DETAILS = "/requestForVender/";
	private static final String GET_INPRINCIPLE_DATE_BY_APP_ID = "/get_inprinciple_date_by_app_id";
	private static final String GET_INPRINCIPLE_APP_LIST = "/getInPrincipleApplicationList";
	private static final String GET_APPLICATION_LIST_BY_USER_ID = "/getApplicationListByUserId";
	private static final String SAVE_RELATED_PARTY_FLAG = "/saveAndGetRelatedPartyFlag";
	private static final String CONNECT_POST_CHECK_STAGE = "/post/checkStage";
	private static final String SAVE_AUTO_APPLICATION = "/saveAutoApplication";
	private static final String SAVE_CO_APPLICANTS_AUTO_DEALER = "/saveCoApplicantsAutoDealer";

	private String connectBaseUrl;
	private RestTemplate restTemplate;
	
	private final Logger log = LoggerFactory.getLogger(ConnectClient.class);
	
	public ConnectClient(String connectBaseUrl) {
		this.connectBaseUrl = connectBaseUrl;
		restTemplate = new RestTemplate();
	}

	public ConnectResponse getApplicationList(Long appId) throws ConnectException {
		String url = connectBaseUrl.concat(GET_APPLICATION_LIST).concat("/" + appId);
		log.info(CONNECT_CLIENT_URL , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ConnectResponse.class).getBody();
		} catch (Exception e) {
			log.error("{}",e);
			throw new ConnectException("error while calling getApplicationList()" + e.getMessage());
		}

	}

	public ConnectResponse createForMultipleBank(ConnectRequest connectRequest) throws ConnectException {
		String url = connectBaseUrl.concat(CREATE_FOR_MULTIPLE_BANK);
		log.info(CONNECT_CLIENT_URL , url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(connectRequest), ConnectResponse.class).getBody();
		} catch (Exception e) {
			throw new ConnectException("error while calling createForMultipleBank() client");
		}
	}
	/**
	 * Getting Cibil score for Company like Private Limited or Public Limited or
	 * Partnership.
	 * 
	 * @param cibilRequest
	 * @return
	 * @throws ConnectException
	 */

	public ConnectResponse logRequest(Long applicationId, Long userId, ConnectStage nextStage, Integer stepId,
			Integer status, String gstin, String gstnUserName,Integer businessTypeId) throws ConnectException {

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setStageId(nextStage.ordinal());
		request.setStepId(stepId);
		request.setBusinessTypeId(businessTypeId);
		if (!StringUtils.isEmpty(gstin))
			request.setGstIn(gstin);

		if (!StringUtils.isEmpty(gstnUserName))
			request.setGstnUserName(gstnUserName);

		request.setStatus(status);

		String url = connectBaseUrl.concat(CONNECT_UPDATE_LOG);
		log.info(CONNECT_CLIENT_URL , url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);

			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}

	/**
	 * Update Information
	 * @param applicationId
	 * @param userId
	 * @param nextStage
	 * @param stepId
	 * @param status
	 * @param gstin
	 * @param gstnUserName
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse update(ConnectResponse connectResponse) throws ConnectException {
		ConnectResponse response = null;
		String url = connectBaseUrl.concat(CONNECT_UPDATE_STAGE);
		log.info(CONNECT_CLIENT_URL , url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(connectResponse), ConnectResponse.class).getBody();
			return response;
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}
	}

	/**
	 * Check Matching Eligibility post GST Data
	 * @param applicationId
	 * @param userId
	 * @param gstin
	 * @param gstnUserName
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postGST(ConnectRequest request) throws ConnectException {
		String url = connectBaseUrl.concat(CONNECT_POST_GST);
		log.info(CONNECT_CLIENT_URL , url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class).getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}
	}

	/**
	 * Check Matching Eligibility post ITR Data
	 * @param applicationId
	 * @param userId
	 * @param businessTypeIdapplicationId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postITR(ConnectRequest request) throws ConnectException {

		log.info("Connecting postITR");

		ConnectResponse response = null;
		String url = connectBaseUrl.concat(CONNECT_POST_ITR);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}
	
	/**
	 * Getting Data By Application, Stage and BusinessType Id
	 * @param applicationId
	 * @param stageId
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	
	public ConnectResponse getByAppStageBusinessTypeId(Long applicationId, Integer stageId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting getByAppStageBusinessTypeId");
		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setStageId(stageId);
		request.setBusinessTypeId(businessTypeId);
		String url = connectBaseUrl.concat(CONNECT_GET_BY_APP_STAGE_BUSINESS_TYPE);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}
		return response;
	}

	/**
	 * Check Matching Eligibility post Bank Statement Data
	 * @param applicationId
	 * @param userId
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postStatement(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postStatement");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_STATEMENT);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}
	
	/**
	 * Check Matching Eligibility and Cibil of Company and it's Director Post Director Background Data
	 * @param applicationId
	 * @param userId
	 * @param businessTypeId
	 * * @param directorId
	 * @return
	 * @throws ConnectException
	 */
	
	public ConnectResponse postDirectorBackground(Long applicationId, Long userId,Integer businessTypeId,Long directorId) throws ConnectException {

		log.info("Connecting postDirectorBackground");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);
		request.setDirectorId(directorId);
		String url = connectBaseUrl.concat(CONNECT_POST_DIRECTOR_BACKGORUND);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Client not available, postDirectorBackground");
		}

		return response;
	}

	
	public ConnectResponse postDirBackChangeStageNTB(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postDirBackChangeStageNTB");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);
		String url = connectBaseUrl.concat(CONNECT_POST_DIR_BACK_CHANGE_STAGE);
		log.info("Connect Client URL CONNECT_POST_DIR_BACK_CHANGE_STAGE===>{}" , url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Client not available, postDirBackChangeStageNTB");
		}

		return response;
	}

	public ConnectResponse postOneForm(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postOneForm");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_ONE_FORM);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}
	
	public ConnectResponse postOneForm(Long applicationId, Long userId,Integer businessTypeId,Boolean isNbfcUser) throws ConnectException {

		log.info("Connecting postOneForm");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setIsNbfcUser(isNbfcUser);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_ONE_FORM);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}
	
	public ConnectResponse postOneForm(ConnectRequest request) throws ConnectException {
		log.info("Connecting postOneForm");
		ConnectResponse response = null;
		/*ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);*/
		String url = connectBaseUrl.concat(CONNECT_POST_ONE_FORM);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}

    public ConnectResponse postOnePagerCIBIL(Long applicationId, Long userId,Integer businessTypeId,String pan) throws ConnectException {

        log.info("Connecting postCIBILOnePager");
        ConnectResponse response = null;
        ConnectRequest request = new ConnectRequest();
        request.setApplicationId(applicationId);
        request.setUserId(userId);
        request.setBusinessTypeId(businessTypeId);
        request.setPan(pan);
        String url = connectBaseUrl.concat(CONNECT_ONE_PAGER_CIBIL);
        log.info(CONNECT_CLIENT_URL ,  url);
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
                    .getBody();
        } catch (Exception e) {
            log.error(EXCEPTION,e);
            throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
        }

        return response;
    }
	
	public ConnectResponse postOneFormBasicLoanDetails(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {
		log.info("Connecting postOneFormBasicLoanDetails");
		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);
		String url = connectBaseUrl.concat(CONNECT_POST_ONE_FORM_BASIC_LOAN_DETAILS_PL);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}
		return response;
	}
	
	public ConnectResponse postOneFormBasicDetails(Long applicationId, Long userId,Integer businessTypeId,String pan) throws ConnectException {

		log.info("Connecting postOneFormBasicDetails");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);
		request.setPan(pan);
		String url = connectBaseUrl.concat(CONNECT_POST_ONE_FORM_BASIC_DETAILS_PL);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}
		return response;
	}
	
	
	public ConnectResponse postOneFormBasicDetails(ConnectRequest request) throws ConnectException {
		log.info("Connecting postOneFormBasicDetails");
		ConnectResponse response = null;
		String url = connectBaseUrl.concat(CONNECT_POST_ONE_FORM_BASIC_DETAILS_PL);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}
		return response;
	}
	
	public ConnectResponse postCibilAuthentication(Long applicationId, Long userId,Integer businessTypeId,String pan) throws ConnectException {

		log.info("Connecting postCibilAuthentication");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);
		request.setPan(pan);
		String url = connectBaseUrl.concat(CONNECT_POST_CIBIL_AUTHENTICATION);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}
		return response;
	}
	public ConnectResponse postCibilAuthentication(ConnectRequest request) throws ConnectException {

		log.info("Connecting postCibilAuthentication");

		ConnectResponse response = null;
		String url = connectBaseUrl.concat(CONNECT_POST_CIBIL_AUTHENTICATION);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}
		return response;
	}
	
	

	/**
	 * Updating Stage to Payment
	 * @param applicationId
	 * @param userId
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postPayment(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postPayment");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_PAYMENT);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}
	
	/**
	 * Updating Stage to Matches From Payment Page
	 * @param applicationId
	 * @param userId
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postPaymentToMatches(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postPaymentToMatches");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_PAYMENT_TO_MATCHES);
		log.info(CONNECT_CLIENT_URL , url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}
		return response;
	}
	
	/**
	 * Updating Stage to for Existing MCQ
	 * @param applicationId
	 * @param userId
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postExistingMCQ(ConnectRequest request) throws ConnectException {
		String url = connectBaseUrl.concat(CONNECT_POST_EXISTING_MCQ);

		log.info("Connect Client URL for post Existing MCQ===>{}" , url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Client not available while post existing mcq");
		}
	}
	
	/**
	 * Set Stage to Business Type Selection Page
	 * @param applicationId
	 * @param userId
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postBusinessTypeSelection(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postBusinessTypeSelection");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_BUSINESS_TYPE_SELECTION);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}
	
	/**
	 * Update Stage to MCQ
	 * @param applicationId
	 * @param userId
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postMCQNTB(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postMCQNTB");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_NTB_MCQ);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}
	
	/**
	 * Update Stage to Oneform Other Detail Post One Form.
	 * @param applicationId
	 * @param userId
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postNTBOneFormOtherDetails(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postNTBOneFormOtherDetails");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_NTB_ONE_FORM_OTHER_DETAIL);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}
	
	/**
	 * Update Stage to Next relevent stage after Matches
	 * @param applicationId
	 * @param userId
	 * @param businessTypeId
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse postMatches(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postMatches applicationId : {} : userId : {}" ,applicationId, userId);

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_MATCHES);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}
	
	public ConnectResponse postInPrinciple(Long applicationId, Long userId,Integer businessTypeId) throws ConnectException {

		log.info("Connecting postInPrinciple applicationId : {} : userId : {}",applicationId, userId);

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_POST_IN_PRINCIPLE);
		log.info("Connect Client CONNECT_POST_IN_PRINCIPLE URL===>{}" , url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("CONNECT_POST_IN_PRINCIPLE Connect Client not available");
		}

		return response;
	}

	private HttpEntity<Object> getHttpHeader(Object request) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(request, headers);
	}
	
	
	public ConnectResponse getMobileStage(Long userId,Integer businessTypeId) throws ConnectException {


		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_MOBILE_STAGE);
		log.info("Mobile Stage Service Connect Client URL===>{}" , url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Client not available while get current mobile stage");
		}

		return response;
	}
	
	public ConnectResponse mobileCreateApplication(Long userId,Integer businessTypeId) throws ConnectException {


		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);

		String url = connectBaseUrl.concat(CONNECT_CREATE_APP);
		log.info("Mobile Stage Service Connect Client URL===>{}" , url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Client not available While Create Application");
		}

		return response;
	}

	public ConnectResponse checkInPrincipleForGST(ConnectRequest request) throws ConnectException {


		ConnectResponse response = null;
		
		String url = connectBaseUrl.concat(CHECK_IN_PRINCIPLE_FOR_GST);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Client not available While checking In-Principle for GST");
		}

		return response;
	}
	
	public ConnectResponse checkInPrincipleForPAN(ConnectRequest request) throws ConnectException {


		ConnectResponse response = null;
		
		String url = connectBaseUrl.concat(CHECK_IN_PRINCIPLE_FOR_PAN);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Client not available While checking In-Principle for PAN");
		}

		return response;
	}
	
	/**
	 * 
	 * @param 
	 * {
  			"applicationId" : 10002,
			"stageId" : ConnectStage.ITR.getId(),
			"userId" : 4523,
			"reason" : "SuccessFully Saved",
			"code" : ConnectAuditErrorCode.ITR_UPLOAD,
			"businessTypeId" : 1
		}
	 * @return
	 * @throws ConnectException
	 */
	public ConnectResponse saveAuditLog(ConnectLogAuditRequest auditRequest) throws ConnectException {
		ConnectResponse response = null;
		String url = connectBaseUrl.concat(SAVE_AUDIT_LOG);
		log.info("URL:------------------------------------->{}" , url);
		log.info("Connect Log Audit ===>{}" , auditRequest);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<ConnectLogAuditRequest>(auditRequest, headers), ConnectResponse.class).getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Error While Save Audit Log");
		}

		return response;
	}
    public ConnectResponse getVerndorDetails(String moduleName) throws ConnectException {
        if(moduleName == null) {
            throw new ConnectException("Module Name Must not be null");
        }

        ConnectResponse response = null;
        String url = connectBaseUrl.concat(CONNECT_GET_VENDER_DETAILS).concat(moduleName);
        log.info("URL:------------------------------------->{}", url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            response = restTemplate.exchange(url, HttpMethod.GET, getHttpHeader(null), ConnectResponse.class).getBody();
        } catch (Exception e) {
            log.error(EXCEPTION,e);
            throw new ConnectException("Connect Error While getting vender details");
        }

        return response;
    }
	public ConnectResponse getAllFromAndToDate(ConnectLogRequest auditRequest) throws ConnectException {
		String url = connectBaseUrl.concat(GET_AUDIT_LOG_FROM_TO_DATE);
		log.info("Connect GET Log Audit ===>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<ConnectLogRequest>(auditRequest, headers), ConnectResponse.class).getBody();
		} catch (Exception e) {
			log.error("{}",e);
			throw new ConnectException("Connect Error While GET Audit Log");
		}
	}
	
	public Long getBusinessTypeByAppId(Long applicationId) throws ConnectException {
		Long response = null;
		String url = connectBaseUrl.concat(GET_BUSINESS_ID_BY_APP_ID);
		log.info("getBusinessTypeByAppId ===>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Long>(applicationId, headers), Long.class).getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Error While getBusinessTypeByAppId");
		}

		return response;
	}


	public Date getInprincipleDateByAppId(Long applicationId) throws ConnectException {
		Date response = null;
		String url = connectBaseUrl.concat(GET_INPRINCIPLE_DATE_BY_APP_ID);
		log.info("getInprincipleDateByAppId ===>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Long>(applicationId, headers), Date.class).getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Error While getInprincipleDateByAppId");
		}

		return response;
	}


	public ConnectResponse getInPrincipleApplicationList() throws ConnectException {
		String url = connectBaseUrl.concat(GET_INPRINCIPLE_APP_LIST);
		log.info(CONNECT_CLIENT_URL ,url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ConnectResponse.class).getBody();
		} catch (Exception e) {
			throw new ConnectException("error while calling getInPrincipleApplicationList()");
		}

	}


	public ConnectResponse getApplicationListByUserId(Long userId) throws ConnectException {
		ConnectResponse response = null;
		String url = connectBaseUrl.concat(GET_APPLICATION_LIST_BY_USER_ID);
		log.info("getApplicationListByUserId ===>{}" , url);
		try {
			ConnectRequest request = new ConnectRequest();
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			request.setUserId(userId);
			response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(request,headers), ConnectResponse.class).getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Error While getApplicationListByUserId");
		}
		return response;
	}
	public ConnectResponse saveRelatedPartyFlag(Long applicationId) throws ConnectException {
		ConnectResponse response = null;
		String url = connectBaseUrl.concat(SAVE_RELATED_PARTY_FLAG);
		log.info("saveRelatedPartyFlag ===>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(applicationId,headers), ConnectResponse.class).getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Error While getApplicationListByUserId");
		}
		return response;
	}
	
	public ConnectResponse postCheckStage(Long applicationId, Long userId,Integer businessTypeId,Integer stageId) throws ConnectException {

		log.info("Connecting postCheckStage");

		ConnectResponse response = null;
		ConnectRequest request = new ConnectRequest();
		request.setApplicationId(applicationId);
		request.setUserId(userId);
		request.setBusinessTypeId(businessTypeId);
		request.setStageId(stageId);

		String url = connectBaseUrl.concat(CONNECT_POST_CHECK_STAGE);
		log.info(CONNECT_CLIENT_URL ,  url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(request), ConnectResponse.class)
					.getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException(CONNECT_CLIENT_NOT_AVAILABLE);
		}

		return response;
	}

	public ConnectResponse saveAutoApplication(ConnectRequest request) throws ConnectException {
		ConnectResponse response = null;
		String url = connectBaseUrl.concat(SAVE_AUTO_APPLICATION);
		log.info("saveAutoApplication ===>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(request,headers), ConnectResponse.class).getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Error While saveAutoApplication");
		}
		return response;
	}

	public ConnectResponse saveCoApplicantsAutoDealer(ConnectCoAppRequest request) throws ConnectException {
		ConnectResponse response = null;
		String url = connectBaseUrl.concat(SAVE_CO_APPLICANTS_AUTO_DEALER);
		log.info("saveCoApplicantsAutoDealer ===>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(request,headers), ConnectResponse.class).getBody();
		} catch (Exception e) {
			log.error(EXCEPTION,e);
			throw new ConnectException("Connect Error While saveCoApplicantsAutoDealer");
		}
		return response;
	}

}
