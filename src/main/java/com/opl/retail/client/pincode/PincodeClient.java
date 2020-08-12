package com.opl.retail.client.pincode;

import com.opl.retail.api.model.pincode.PincodeMasterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @author sanket
 *
 */
public class PincodeClient {
	private final Logger logger = LoggerFactory.getLogger(PincodeClient.class);
	private static final String PINCODE = "/pincode";
	private static final String PINCODE_CITY = "/pincodeCity";
	private static final String PINCODE_STATE = "/pincodeState";
	private static final String PINCODE_COUNTRY = "/pincodeCountry";
	

	 private String pincodeClientBaseUrl;
	 private RestTemplate restTemplate;
	 
	 public PincodeClient(String pincodeClientBaseUrl) {
	  this.pincodeClientBaseUrl = pincodeClientBaseUrl;
	  this.restTemplate = new RestTemplate();
	  
	 }
	 
	 public PincodeMasterVO findDetailsByPincode(String pincode) throws Exception{
		  String url = pincodeClientBaseUrl.concat(PINCODE).concat("/"+pincode);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  HttpEntity<?> entity = new HttpEntity<>( headers);
			  return restTemplate.exchange(url, HttpMethod.GET, entity, PincodeMasterVO.class).getBody();
		  } catch (Exception e) {
		   logger.error("Notification Service Error", e);
			  e.printStackTrace();
		   throw new Exception("Pincode Service is not available");
		  }
		 }
	 
	 public PincodeMasterVO findCityByPincode(String pincode) throws Exception{
		  String url = pincodeClientBaseUrl.concat(PINCODE_CITY).concat("/"+pincode);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  HttpEntity<?> entity = new HttpEntity<>( headers);
			  return restTemplate.exchange(url, HttpMethod.GET, entity, PincodeMasterVO.class).getBody();
		  } catch (Exception e) {
		   logger.error("Notification Service Error", e);
			  e.printStackTrace();
		   throw new Exception("Pincode Service is not available");
		  }
		 }
	 
	 public PincodeMasterVO findStateByPincode(String pincode) throws Exception{
		  String url = pincodeClientBaseUrl.concat(PINCODE_STATE).concat("/"+pincode);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  HttpEntity<?> entity = new HttpEntity<>( headers);
			  return restTemplate.exchange(url, HttpMethod.GET, entity, PincodeMasterVO.class).getBody();
		  } catch (Exception e) {
		   logger.error("Notification Service Error", e);
			  e.printStackTrace();
		   throw new Exception("Pincode Service is not available");
		  }
		 }
	 
	 public PincodeMasterVO findCountryByPincode(String pincode) throws Exception{
		  String url = pincodeClientBaseUrl.concat(PINCODE_COUNTRY).concat("/"+pincode);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  HttpEntity<?> entity = new HttpEntity<>( headers);
			  return restTemplate.exchange(url, HttpMethod.GET, entity, PincodeMasterVO.class).getBody();
		  } catch (Exception e) {
		   logger.error("Notification Service Error", e);
			  e.printStackTrace();
		   throw new Exception("Pincode Service is not available");
		  }
		 }
}
