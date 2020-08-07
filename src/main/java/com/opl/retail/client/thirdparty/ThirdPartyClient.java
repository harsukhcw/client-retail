package com.opl.retail.client.thirdparty;

import com.opl.retail.api.exceptions.thirdparty.ThirdpartyException;
import com.opl.retail.api.model.thirdparty.CGTMSEDataResponse;
import com.opl.retail.api.model.thirdparty.ThirdPartyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Ankit gupta on 1-Apr-18.
 *
 */

public class ThirdPartyClient {

	/*
	 * private static final Logger logger =
	 * LoggerFactory.getLogger(ThirdPartyClient.class);
	 */
	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyClient.class);
	private static final String CGTMSE = "/checkPanNumber/CGTMSE";
	private static final String WILFULDEFAULTER = "/checkPanNumber/WILFULDEFAULTER";
	private static final String GET_CALCULATION = "/getCalculation";
	private static final String CGTMSE_ELI = "/checkPanNumberNew/CGTMSE";
	
	private static final String GET_CALCULATION_ELI = "/getCalculationForEli";
	private static final String  REQ_AUTH = "req_auth";
	private static final String CGTMSE_SERVICE_NOT_AVAILABLE = "CGTMSE service not available";
	private static final String EXCEPTION = "Exception";
	
	private String thirdPartyBaseUrl;
	private RestTemplate restTemplate;

	public ThirdPartyClient(String thirdPartyBaseUrl) {
		this.thirdPartyBaseUrl = thirdPartyBaseUrl;
		restTemplate = new RestTemplate();
	}

	public CGTMSEDataResponse checkByCGTMSE(ThirdPartyRequest requestModel) throws ThirdpartyException {
		String url = thirdPartyBaseUrl.concat(CGTMSE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ThirdPartyRequest> entity = new HttpEntity<>(requestModel, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, CGTMSEDataResponse.class).getBody();

		} catch (NullPointerException e) {
			logger.error(EXCEPTION,e);
			throw new ThirdpartyException(CGTMSE_SERVICE_NOT_AVAILABLE);
		}
	}
	public CGTMSEDataResponse checkByCGTMSEForEli(List<ThirdPartyRequest> thirdPartyRequests) throws ThirdpartyException {
		String url = thirdPartyBaseUrl.concat(CGTMSE_ELI);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ThirdPartyRequest>> entity = new HttpEntity<>(thirdPartyRequests, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, CGTMSEDataResponse.class).getBody();
			
		} catch (NullPointerException e) {
			logger.error(EXCEPTION,e);
			throw new ThirdpartyException(CGTMSE_SERVICE_NOT_AVAILABLE);
		}
	}

	public CGTMSEDataResponse checkByWilFulDefaulter(ThirdPartyRequest requestModel) throws ThirdpartyException {
		String url = thirdPartyBaseUrl.concat(WILFULDEFAULTER);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ThirdPartyRequest> entity = new HttpEntity<>(requestModel, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, CGTMSEDataResponse.class).getBody();

		} catch (NullPointerException e) {
			logger.error(EXCEPTION,e);
			throw new ThirdpartyException(CGTMSE_SERVICE_NOT_AVAILABLE);

		}
	}
	
	public CGTMSEDataResponse getCalulation(Long applicationId) throws ThirdpartyException {
		String url = thirdPartyBaseUrl.concat(GET_CALCULATION).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<ThirdPartyRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, CGTMSEDataResponse.class).getBody();

		} catch (NullPointerException e) {
			logger.error(EXCEPTION,e);
			throw new ThirdpartyException(CGTMSE_SERVICE_NOT_AVAILABLE);
		}
	}
	
	
	public CGTMSEDataResponse getCalulation(Long applicationId, Long productId) throws ThirdpartyException {
		String url = thirdPartyBaseUrl.concat(GET_CALCULATION_ELI).concat("/"+applicationId).concat("/"+productId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<ThirdPartyRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, CGTMSEDataResponse.class).getBody();

		} catch (NullPointerException e) {
			logger.error(EXCEPTION,e);
			throw new ThirdpartyException(CGTMSE_SERVICE_NOT_AVAILABLE);
		}
	}

}
