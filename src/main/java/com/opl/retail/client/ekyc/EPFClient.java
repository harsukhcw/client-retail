package com.opl.retail.client.ekyc;

import com.opl.retail.api.exceptions.ekyc.ExcelException;
import com.opl.retail.api.model.ekyc.ekycAadharMfi.MfiEkycAadharRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.exceptions.ekyc.EKYCException;
import com.opl.retail.api.model.ekyc.EkycResponse;
import com.opl.retail.api.model.ekyc.epf.request.EmployerRequest;

public class EPFClient {
		
		private static final String GET_EMPLOYMENT_DETAIL = "/employment-detail";
		private static final String GET_EPF_DATA ="/getEpfData";
		private static final String REQUEST_AADHAR_OTP = "/aadharekyc/requestOTP";
		private static final String REQUEST_AADHAR_DATA = "/aadharekyc/requestEKYC";


		//	private final Log log = LogFactory.getLog(getClass());
		private String gstBaseUrl;
		private RestTemplate restTemplate = new RestTemplate();

		public EPFClient(String gstBaseUrl) {
			this.gstBaseUrl = gstBaseUrl;
		}
		
		
		public EkycResponse getVerifyEmployment(EmployerRequest request) {
			String url = gstBaseUrl.concat(GET_EMPLOYMENT_DETAIL);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set("req_auth", "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<EmployerRequest> entity = new HttpEntity<>(request, headers);

				ResponseEntity<EkycResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, EkycResponse.class);
				return responseEntity.getBody();
			} catch (RestClientException e) {
				throw new EKYCException("Can not get employee details", e);
			}
		}
		
		/*client for getEpfData for teaser and cam*/
		public EkycResponse getEpfData(EmployerRequest request) {
			String url = gstBaseUrl.concat(GET_EPF_DATA)+ "/" + request.getApplicationId();
			System.out.println(url);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set("req_auth", "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<EmployerRequest> entity = new HttpEntity<>(request, headers);

				ResponseEntity<EkycResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, EkycResponse.class);
				return responseEntity.getBody();
			} catch (RestClientException e) {
				throw new EKYCException("Error while geting epf data", e);
			}
		}

		/**
		 * This method for MFI ekyc of aadhar data
		 * @param mfiEkycAadharRequest
		 * @param type
		 * @return
		 * @throws EKYCException
		 */
		public EkycResponse requestAadharDetails(MfiEkycAadharRequest mfiEkycAadharRequest, Integer type) {
			String url;
			if(type == 1) {
				url = gstBaseUrl.concat(REQUEST_AADHAR_OTP);
			} else {
				url = gstBaseUrl.concat(REQUEST_AADHAR_DATA);
			}
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set("req_auth", "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<MfiEkycAadharRequest> entity = new HttpEntity<>(mfiEkycAadharRequest, headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, EkycResponse.class).getBody();
			} catch (Exception e) {
				throw new EKYCException("Error while geting Aadhar Details", e);
			}
		}

	}