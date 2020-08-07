package com.opl.retail.client.fitchengine;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.utils.fitchengine.FitchRequest;



public class FitchEngineClient {

	

	private static final String CALCULATE_MANUFACTURING_FITCH_RATING_CW_INPUT = "/calculate_manufacturing_rating/cw_input";
	private static final String CALCULATE_SERVICE_FITCH_RATING_CW_INPUT = "/calculate_service_rating/cw_input";
	private static final String CALCULATE_TRADING_FITCH_RATING_CW_INPUT = "/calculate_trading_rating/cw_input";
	
	private String fitchEngineBaseUrl;
	private RestTemplate restTemplate;
	
	private String username;
	private String password;
	
	private final Logger log = LoggerFactory.getLogger(FitchEngineClient.class);

	public FitchEngineClient(String fitchEngineBaseUrl, String usrname, String password) {
		this.fitchEngineBaseUrl = fitchEngineBaseUrl;
		this.username = usrname;
		this.password = password;
		restTemplate = new RestTemplate();
	}

	public FitchEngineClient(String fitchEngineBaseUrl) {
		this.fitchEngineBaseUrl = fitchEngineBaseUrl;
		restTemplate = new RestTemplate();
	}
	
	
	public FitchResponse calculateManufacturingFitchRatingCwInput(FitchRequest fitchRequest) throws FitchEngineException {
		String url = fitchEngineBaseUrl.concat(CALCULATE_MANUFACTURING_FITCH_RATING_CW_INPUT);
		try {
			
			log.info("manufacturing calculation called");
			
			
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Basic Zml0Y2hhZG1pbjphZG1pbmZpdGNoQDMyMzU=");
			HttpEntity<FitchRequest> entity = new HttpEntity<FitchRequest>(fitchRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, FitchResponse.class).getBody();
			
			//return restTemplate.postForObject(url, fitchRequest, FitchResponse.class);
		} catch (Exception e) {
			log.error("error while manufacturing calculation called");
			e.printStackTrace();
			throw new FitchEngineException("Fitch Engine Service is not available");
		}
	}
	
	public FitchResponse calculateServiceFitchRatingCwInput(FitchRequest fitchRequest) throws FitchEngineException {
		String url = fitchEngineBaseUrl.concat(CALCULATE_SERVICE_FITCH_RATING_CW_INPUT);
		try {
			log.info("service calculation called");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Basic " + getAuthString());
			HttpEntity<FitchRequest> entity = new HttpEntity<FitchRequest>(fitchRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, FitchResponse.class).getBody();
			
			//return restTemplate.postForObject(url, fitchRequest, FitchResponse.class);
		} catch (Exception e) {
			log.info("error while service calculation called");
			e.printStackTrace();
			throw new FitchEngineException("Fitch Engine Service is not available");
		}
	}
	
	public FitchResponse calculateTradingFitchRatingCwInput(FitchRequest fitchRequest) throws FitchEngineException {
		String url = fitchEngineBaseUrl.concat(CALCULATE_TRADING_FITCH_RATING_CW_INPUT);
		try {
			
			log.info("trading calculation called");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Basic " + getAuthString());
			HttpEntity<FitchRequest> entity = new HttpEntity<FitchRequest>(fitchRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, FitchResponse.class).getBody();
			
			//return restTemplate.postForObject(url, fitchRequest, FitchResponse.class);
		} catch (Exception e) {
			log.error("error while trading calculation called");
			e.printStackTrace();
			throw new FitchEngineException("Fitch Engine Service is not available");
		}
	}
	
	private String getAuthString(){
		
		String authString = username + ":" + password;
		System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		
		return authStringEnc;
		
	}
	
	private HttpEntity<Object> setHttpHeader(Object request){
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
	    return new HttpEntity<Object>(request, headers);
	}
}