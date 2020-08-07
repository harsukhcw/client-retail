/**
 * 
 */
package com.opl.retail.client.fraudanalytics;

import com.opl.retail.api.exceptions.fraudanalytics.FraudAnalyticsException;
import com.opl.retail.api.model.fraudanalytics.AnalyticsRequest;
import com.opl.retail.api.model.fraudanalytics.AnalyticsResponse;
import com.opl.retail.api.model.fraudanalytics.FraudDetectionRequest;
import com.opl.retail.api.model.fraudanalytics.McaFraudAnalysisRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @author sanket
 *
 */
public class FraudAnalyticsClient {
	

	private final Logger logger = LoggerFactory.getLogger(FraudAnalyticsClient.class);
	private static final String CALL_HUNTERII_API = "/callHunterIIAPI";
	private static final String GET_RULE_ANALYSIS_DATA = "/getRuleAnalysisData";
	
	private static final String RBI_FRAUD_ANALYSIS= "/rbiFraudAnalysis";
	private static final String GET_RBI_FRAUD_ANALYSIS_DATA = "/getRbiFraudAnalysisData";
	
	private static final String MCA_FRAUD_ANALYSIS= "/mcaFraudAnalysis";
	private static final String GET_MCA_FRAUD_ANALYSIS_DATA = "/getMcaFraudAnalysisData";
	private static final String REQ_AUTH = "req_auth";
	private static final String FRAUD_ANALYTICS_SERVICE_ERROR ="Fraud Analytics Service Error";
	private static final String  EXCEPTION = "Exception : ";
	private String gstBaseUrl;
	private RestTemplate restTemplate;

	public FraudAnalyticsClient(String gstBaseUrl) {
		this.gstBaseUrl = gstBaseUrl;
		restTemplate = new RestTemplate();
	}
	

	public AnalyticsResponse rbiFraudAnalysis(FraudDetectionRequest request) throws FraudAnalyticsException {
		String url = gstBaseUrl.concat(RBI_FRAUD_ANALYSIS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FraudDetectionRequest> entity = new HttpEntity<FraudDetectionRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyticsResponse.class).getBody();
		} catch (Exception e) {
			logger.error("RBI Fraud Analytics Service Error", e);
			throw new FraudAnalyticsException("RBI Fraud Analytics Service Error");
		}
	}
	
	public AnalyticsResponse getRbiFraudAnalysisData(Long applicationId) throws FraudAnalyticsException {
		String url = gstBaseUrl.concat(GET_RBI_FRAUD_ANALYSIS_DATA ).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<AnalyticsRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, AnalyticsResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Get RBI Fraud Analytics Data Service Error", e);
			logger.error(EXCEPTION,e);
			throw new FraudAnalyticsException("Get RBI Fraud Analytics Data Service Error");
		}
	}
	
	public AnalyticsResponse mcaFraudAnalysis(McaFraudAnalysisRequest request) throws FraudAnalyticsException {
		String url = gstBaseUrl.concat(MCA_FRAUD_ANALYSIS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<McaFraudAnalysisRequest> entity = new HttpEntity<McaFraudAnalysisRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyticsResponse.class).getBody();
		} catch (Exception e) {
			logger.error("MCA Fraud Analytics Service Error", e);
			logger.error(EXCEPTION,e);
			throw new FraudAnalyticsException("MCA Fraud Analytics Service Error");
		}
	}
	
	public AnalyticsResponse getMcaFraudAnalysisData(Long applicationId) throws FraudAnalyticsException {
		String url = gstBaseUrl.concat(GET_MCA_FRAUD_ANALYSIS_DATA).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<McaFraudAnalysisRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, AnalyticsResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Get MCA Fraud Analytics Data Service Error", e);
			logger.error(EXCEPTION,e);
			throw new FraudAnalyticsException("Get MCA Fraud Analytics Data Service Error");
		}
	}
	
	public AnalyticsResponse callHunterIIAPI(AnalyticsRequest request) throws FraudAnalyticsException {
		String url = gstBaseUrl.concat(CALL_HUNTERII_API);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<AnalyticsRequest> entity = new HttpEntity<AnalyticsRequest>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyticsResponse.class).getBody();
		} catch (Exception e) {
			logger.error(FRAUD_ANALYTICS_SERVICE_ERROR, e);
			throw new FraudAnalyticsException(FRAUD_ANALYTICS_SERVICE_ERROR);
		}
	}
	
	public AnalyticsResponse getRuleAnalysisData(Long applicationId) throws FraudAnalyticsException {
		String url = gstBaseUrl.concat(GET_RULE_ANALYSIS_DATA ).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<AnalyticsRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, AnalyticsResponse.class).getBody();

		} catch (Exception e) {
			logger.error(FRAUD_ANALYTICS_SERVICE_ERROR, e);
			throw new FraudAnalyticsException(FRAUD_ANALYTICS_SERVICE_ERROR);
		}
	}
	
	
}
