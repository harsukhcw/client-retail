package com.opl.retail.client.loans;

import com.capitaworld.service.loans.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.capitaworld.service.loans.exceptions.ExcelException;
import com.capitaworld.service.loans.model.CMADetailResponse;
import com.capitaworld.service.loans.model.FrameRequest;
import com.capitaworld.service.loans.model.LoansResponse;

public class LoansClient1 {

	private static final Logger logger = LoggerFactory.getLogger(LoansClient1.class);

	private String loansBaseUrl;
	private RestTemplate restTemplate;
	
	
	private static final String GET_CMA_DETAIL = "/loan_eligibility/getCMADetailForEligibility/";
	private static final String CORPORATE_APPLICATION_DETAILS_GET = "/fs_profile/getApplicationClientForEligibility";
	
	public LoansClient1(String loansBaseUrl) {
		
		logger.info("In Client 1");
		this.loansBaseUrl = loansBaseUrl;
		restTemplate = new RestTemplate();
	}

	public CMADetailResponse getCMADetils(Long appId) throws ExcelException {
		
		String url = loansBaseUrl.concat(GET_CMA_DETAIL).concat("/" + appId);
		logger.info("Getting CMA DEtails========>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("req_auth", "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<FrameRequest>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, CMADetailResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION,e);
			throw new ExcelException("Loans service is not available");
		}

	}

	
	public LoansResponse getCorporateApplicant(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(CORPORATE_APPLICATION_DETAILS_GET).concat("/" + applicationId);
		logger.info("url for Getting Corporate Details From Client=================>" + url + " and For Application Id====>" + applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("req_auth", "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION,e);
			throw new ExcelException("Loans service is not available");
		}
	}
}
