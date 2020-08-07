package com.opl.retail.client.cibil_integration;

import com.opl.retail.api.exceptions.cibil_integration.CibilException;
import com.opl.retail.api.model.cibil_integration.BureauResponse;
import com.opl.retail.api.model.cibil_integration.CibilResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class CibilIntegrationClient {

	
	private static final Logger logger = LoggerFactory.getLogger(CibilIntegrationClient.class);
	
//	private String bankUrl;

	private RestTemplate restTemplate = null;
	
	private static final String TEST_API = "/test";
	public static final String GENERATE_TOKEN = "/generateToken";
	public static final String GET_BUREAU_REPORT = "/msme/bureauReport";
	public static final String GET_MATCHES_RESULT = "/msme/calculateBureauMatches";
	public static final String GET_SCORING_RESULT = "/msme/getScoringData";

	public CibilIntegrationClient() {
		restTemplate = new RestTemplate();
	}
	
	/**
	 * TEST API
	 * @param cibilRequest
	 * @return
	 * @throws CibilException
	 */
	/*public CibilResponse testApi(String req, String token) throws CibilException {
		try {
			String url = bankUrl.concat(TEST_API);
	    	return  restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(req, token), CibilResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception while Test API ApplicationId " ,e);
			throw new CibilException("Exception while call Test API ",e);
		}
	}*/
	
	/*public BureauResponse getBureauReport(String req, String token) throws CibilException {
		try {
			String url = bankUrl.concat(GET_BUREAU_REPORT);
	    	return  restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(req, token), BureauResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception while Getting Bureau Report" ,e);
			throw new CibilException("Exception while Getting Bureau Report = >" + e.getMessage());
		}
	}*/
	
	/**
	 * SET HEADERS
	 * @param req
	 * @param token
	 * @return
	 */
	private HttpEntity<String> getHttpHeader(String req, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		if(token != null) {
			headers.set("token", token);	
		}
		return new HttpEntity<String>((req), headers);
	}
	

	/**
	 * GENERATE TOKEN FOR ALL BANKS
	 * @param reqString
	 * @param url
	 * @return
	 * @throws CibilException
	 */
	public String generateToken(String reqString, String url) throws CibilException {
		try {
			logger.info("URL for Token generate : [{}]", url);
//			RestTemplate restTemplate = new RestTemplate();
//			String url = bankUrl.concat(GENERATE_TOKEN);
			CibilResponse res = restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(reqString, null), CibilResponse.class).getBody();
			if(res != null && res.getStatus() == HttpStatus.OK.value() && res.getData() != null) {
				return (String) res.getData();
			}
		} catch (Exception e) {
			logger.error("Exception while call Generate Token For Bank ",e);
			throw new CibilException("Exception while call Generate Token",e);
		}
		return null;
	}
	
	/**
	 * Get MSME bureau report
	 * @param req
	 * @param token
	 * @param apiUrl
	 * @return
	 * @throws CibilException
	 */
	/*public BureauResponse getBureauReport(String req, String token, String apiUrl) throws CibilException {
		try {
			String url = apiUrl.concat(GET_BUREAU_REPORT);
	    	return  restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(req, token), BureauResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception while Test API ApplicationId " ,e);
			throw new CibilException("Exception while calling getBureauReport() API ",e);
		}
	}
	
	public BureauResponse calculateBureauMatches(String req, String token, String apiUrl) throws CibilException {
		try {
			String url = apiUrl.concat(GET_MATCHES_RESULT);
	    	return  restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(req, token), BureauResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception while Test API ApplicationId " ,e);
			throw new CibilException("Exception while calling calculateBureauMatches() API ",e);
		}
	}
	
	public BureauResponse getScoringData(String req, String token, String apiUrl) throws CibilException {
		try {
			String url = apiUrl.concat(GET_SCORING_RESULT);
			return  restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(req, token), BureauResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception while Test API ApplicationId " ,e);
			throw new CibilException("Exception while calling getScoringData() API ",e);
		}
	}*/
	
	/**
	 * Common client API
	 * @param req
	 * @param token
	 * @param apiUrl
	 * @return
	 * @throws CibilException
	 */
	public BureauResponse executeApi(String req, String token, String apiUrl) throws CibilException {
		try {
			logger.info("Exicuting API for URL : [{}]", apiUrl);
			return  restTemplate.exchange(apiUrl, HttpMethod.POST, getHttpHeader(req, token), BureauResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception while executig API [{}] : ERROR ==> " , apiUrl, e);
			throw new CibilException("Exception while executig API " + apiUrl, e);
		}
	}
	
}
