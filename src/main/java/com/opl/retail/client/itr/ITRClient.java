package com.opl.retail.client.itr;

import com.opl.retail.api.common.CommonResponse;
import com.opl.retail.api.model.itr.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ITRClient {
	
	private static final Logger logger = LoggerFactory.getLogger(ITRClient.class);
	
	public static final String START_ITR_API = "/start_itr_connection";
	public static final String UPDATE_ITR_RESPONSE = "/update_itr_response";
	public static final String UPLOAD_ITR_XML = "/upload_itr_xml";
	public static final String IS_READ_COMPLETE = "/is_read_complete";
	public static final String GET_ITR_BASIC_DETAILS = "/getITRBasicDetails";
	public static final String UPDATE_NTB_RESPONSE = "/update_ntb_directors_response";
	public static final String UPDATE_PL_RESPONSE = "/update_pl_response";
	
	public static final String GET_IS_UPLOAD_AND_LAST_YEAR = "/getIsUploadAndYearDetails";
	public static final String GET_APP_OR_COAPP_BASIC_DETAILS = "/getAppOrCoAppBasicDetails";
	
	
	public static final String MOBILE_START_ITR_API = "/mobile/start_itr_connection";
	public static final String MOBILE_UPDATE_ITR_RESPONSE = "/mobile/update_itr_response";
	public static final String MOBILE_UPLOAD_ITR_XML = "/mobile/upload_itr_xml";
	public static final String MOBILE_ONLINE_ITR_DETAILS = "/mobile/online_itr_details";
	
	public static final String GET_ONEFORM_STATEID_FROM_ITRSTATEID = "/getOneFormStateIdFromITRStateId";
	public static final String IS_MOVE_AHEAD_FOR_MATCHES = "/isMoveAheadForMatches";
	public static final String GET_ADDITIONAL_DATE_FIELDS = "/getAdditionalDataFields";
	public static final String GET_BANK_LIST = "/getBankDetails";
	public static final String SAVE_DATA_IN_LOANS = "/saveITRDetailsInLoans";
	public static final String SAVE_FINAL_DATA_IN_LOANS = "/saveFinalITRDataInLoans";
	public static final String CHECK_ITR_UPDATED = "/checkIsITRUpdated";


	
	private String BASE_URL;
	private RestTemplate restTemplate;
	private static final String EXCEPTION_CALLING_ITR_START_API = "Throw Exception While Calling ITR Start API";
	private static final String REQ_AUTH = "req_auth";
	private static final String EXCEPTION_UPDATING_ITR_API = "Throw Exception While Updating ITR API";

	public ITRClient(String baseUrl) {
		this.BASE_URL = baseUrl;
		restTemplate = new RestTemplate();
	}
	
	public ITRConnectionResponse startITRAPI(ITRRequest itrRequest) {
		String url = BASE_URL.concat(START_ITR_API);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ITRRequest> entity = new HttpEntity<ITRRequest>(itrRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION_CALLING_ITR_START_API, e);
			return null;
		}
	}
	
	public ITRConnectionResponse isITRReadComplete(Long applicationId) {
		String url = BASE_URL.concat(IS_READ_COMPLETE) + "/" + applicationId;
		try {
			return restTemplate.getForObject(url, ITRConnectionResponse.class);
		} catch (Exception e) {
			logger.error(EXCEPTION_CALLING_ITR_START_API, e);
			return null;
		}
	}
	
	public ITRConnectionResponse updateITRResponse(ITRRedirectRequest itrRedirectRequest) {
		String url = BASE_URL.concat(UPDATE_ITR_RESPONSE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ITRRedirectRequest> entity = new HttpEntity<ITRRedirectRequest>(itrRedirectRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION_UPDATING_ITR_API, e);
			return null;
		}
	}
	
	
	public ITRConnectionResponse updateNTBITRResponse(ITRRedirectRequest itrRedirectRequest) {
		String url = BASE_URL.concat(UPDATE_NTB_RESPONSE);
		logger.info("Enter in Update NTB ITR Response ----------------->" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ITRRedirectRequest> entity = new HttpEntity<ITRRedirectRequest>(itrRedirectRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception While Updating NTB ITR API", e);
			return null;
		}
	}
	
	public ITRConnectionResponse updatePLITRResponse(ITRRedirectRequest itrRedirectRequest) {
		String url = BASE_URL.concat(UPDATE_PL_RESPONSE);
		logger.info("Enter in Update PL ITR Response ----------------->" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ITRRedirectRequest> entity = new HttpEntity<ITRRedirectRequest>(itrRedirectRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception While Updating PL ITR API", e);
			return null;
		}
	}
	
	public ITRConnectionResponse uploadITRXML(String itrRequest, MultipartFile[] multipartFiles) {
        String url = BASE_URL.concat(UPLOAD_ITR_XML);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set(REQ_AUTH, "true");
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            
            ByteArrayResource[] byteArrayResource = new ByteArrayResource[3];
            int i = 0;
            for(final MultipartFile multipartfile : multipartFiles) {
            	ByteArrayResource contentsAsResource = new ByteArrayResource(multipartfile.getBytes()){
                    @Override
                    public String getFilename(){
                        return multipartfile.getOriginalFilename();
                    }
                };
                byteArrayResource[i] = contentsAsResource;
                i = i + 1;
            }
            
            map.add("uploadingFiles", byteArrayResource);
            map.add("itrRequest", itrRequest);

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
			return restTemplate.postForObject(url, request, ITRConnectionResponse.class);

        } catch (Exception e) {
        	logger.error("Throw Exception While ", e);
            return null;
        }
    }
	
	public ITRConnectionResponse mobileStartITRAPI(ITRRequest itrRequest) {
		String url = BASE_URL.concat(MOBILE_START_ITR_API);
		logger.info("Enter in Mobile Start ITR API ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ITRRequest> entity = new HttpEntity<ITRRequest>(itrRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION_CALLING_ITR_START_API, e);
			return null;
		}
	}
	
	public ITRConnectionResponse mobileUpdateITRResponse(ITRRedirectRequest itrRedirectRequest) {
		String url = BASE_URL.concat(MOBILE_UPDATE_ITR_RESPONSE);
		logger.info("Enter in Mobile Update ITR Response ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ITRRedirectRequest> entity = new HttpEntity<ITRRedirectRequest>(itrRedirectRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION_UPDATING_ITR_API, e);
			return null;
		}
	}
	
	public ITRConnectionResponse getITRBasicDetails(Long applicationId) {
		String url = BASE_URL.concat(GET_ITR_BASIC_DETAILS) + "/" + applicationId;
		logger.info("Enter in ITR CLIENT FOR GET ITR BASIC DETAILS  ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception While Get ITR Basic Details", e );
			return null;
		}
	}
	
	public ITRConnectionResponse getOnlineITRDetails(String txnId) {
		String url = BASE_URL.concat(MOBILE_ONLINE_ITR_DETAILS) + "/" + txnId;
		logger.info("Enter in ITR CLIENT FOR GET ONLINE ITR DETAILS  ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception While Get ONline ITR Details", e);
			return null;
		}
	}
	
	
	public ITRConnectionResponse getOneFormStateIdFromITRStateId(Long itrStateId) {
		String url = BASE_URL.concat(GET_ONEFORM_STATEID_FROM_ITRSTATEID ) + "/" + itrStateId;
		logger.info("Enter in ITR CLIENT FOR GET ONLINE ITR DETAILS  ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception While Get ONline ITR Details", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param applicationId
	 * @return ITRConnectionResponse  :- If status is 200 then data return in data key(Object)(cast in ITRBasicDetailsResponse class) 
	 *  data(ITRBasicDetailsResponse) :- isUpload :- true me current Application is upload ITR otherwise online
	 *  								 year :- ITR last year
	 *  								 itrForm :- ITR Form Type
	 */ 
	public ITRConnectionResponse getIsUploadAndYearDetails(Long applicationId) {
		String url = BASE_URL.concat(GET_IS_UPLOAD_AND_LAST_YEAR) + "/" + applicationId;
		logger.info("Enter in ITR CLIENT FOR GET ISUPLOAD AND LAST YEAR DETAILS  ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error("THROW EXCEPTION WHILE GET ISUPLOAD AND LAST YEAR DETAILS", e);
			return null;
		}
	}
	
	public ITRBasicDetailsResponse getAppOrCoAppBasicDetails(ITRBasicDetailsResponse res) {
		String url = BASE_URL.concat(GET_APP_OR_COAPP_BASIC_DETAILS);
		logger.info("Enter in ITR CLIENT FOR GET APP OR COAPP ISUPLOAD AND LAST YEAR DETAILS  ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ITRBasicDetailsResponse> entity = new HttpEntity<ITRBasicDetailsResponse>(res, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ITRBasicDetailsResponse.class).getBody();
		} catch (Exception e) {
			logger.error("THROW EXCEPTION WHILE GET APP OR COAPP ISUPLOAD AND LAST YEAR DETAILS", e);
			return null;
		}
	}
	
	
	public ITRConnectionResponse isMoveAheadForMatches(Long applicationId) {
		String url = BASE_URL.concat(IS_MOVE_AHEAD_FOR_MATCHES) + "/" + applicationId;
		logger.info("Enter in ITR CLIENT FOR IS MOVE AHEAD FOR MATCHES ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error("THROW EXCEPTION WHILE GET IS MOVE AHEAD FOR MATCHES", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ITRAdditionalFieldsRequest> getAdditionalDataFields(ITRAdditionalFieldsRequest itrRequest) {
		String url = BASE_URL.concat(GET_ADDITIONAL_DATE_FIELDS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ITRAdditionalFieldsRequest> entity = new HttpEntity<ITRAdditionalFieldsRequest>(itrRequest, headers);
			//return restTemplate.exchange(url, HttpMethod.POST, entity, List.class).getBody();
			return restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<ITRAdditionalFieldsRequest>>(){}).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION_CALLING_ITR_START_API, e);
			return null;
		}
	}

	public ITRConnectionResponse getBankList(Long applicationId) {
		String url = BASE_URL.concat(GET_BANK_LIST) + "/" + applicationId;
		logger.info("Enter in ITR CLIENT FOR GET BANK LIST ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error("THROW EXCEPTION WHILE GET BANK LIST ", e);
			return null;
		}
	}

	/**
	 * Add Loan Application Id (Not MasterId)
	 * @param applicationId
	 * @param profileId // current Profile id
	 * @param userId // User Id
	 * @return
	 */
	public ITRConnectionResponse saveDataInLoans(Long applicationId,Long profileId, Long userId) {
		String url = BASE_URL.concat(SAVE_DATA_IN_LOANS) + "/" + applicationId + "/" + profileId+ "/" + userId;
		logger.info("Enter in ITR CLIENT FOR SAVE ITR DETAILS IN LAONS  ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ITRConnectionResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception While SAVE ITR DETAILS IN LOANS", e );
			return null;
		}
	}

    /**
     * Add Loan Application Id (Not MasterId)
     * @param applicationId
     * @param profileId // current Profile id
     * @param userId // User Id
     * @return
     */
    public ITRConnectionResponse saveFinalITRDataInLoans(Long applicationId,Long profileId, Long userId) {
        String url = BASE_URL.concat(SAVE_FINAL_DATA_IN_LOANS) + "/" + applicationId + "/" + profileId+ "/" + userId;
        logger.info("Enter in ITR CLIENT FOR SAVE FINAL ITR DETAILS IN LAONS  ------------>" + url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            HttpEntity<?> entity = new HttpEntity<Object>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, ITRConnectionResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While SAVE FINAL ITR DETAILS IN LOANS", e );
            return null;
        }
    }
    public CommonResponse checkIsITRUpdated(Long profileId, Long masterId) {
        String url;
        if(masterId == null){
            url = BASE_URL.concat(CHECK_ITR_UPDATED)+ "/" + profileId;
        } else {
            url = BASE_URL.concat(CHECK_ITR_UPDATED) + "/" + profileId + "?masterId=" + masterId;
        }
        logger.info("Enter in ITR CLIENT FOR CHECK ITR UPDATED  ------------>" + url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            HttpEntity<?> entity = new HttpEntity<Object>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, CommonResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While CHECK ITR UPDATED", e );
            return null;
        }
    }
	
}
