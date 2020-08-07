package com.opl.retail.client.mca;

import com.opl.retail.api.exceptions.mca.McaException;
import com.opl.retail.api.model.mca.McaRequest;
import com.opl.retail.api.model.mca.McaResponse;
import com.opl.retail.api.model.mca.cubictree.api.CubictreeJobRegistrationRequest;
import com.opl.retail.api.model.mca.verifyApi.VerifyAPIRequest;
import com.opl.retail.api.utils.mca.McaCommonUtils;
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
public class McaClient {
	
	private final Logger logger = LoggerFactory.getLogger(McaClient.class);
	private static final String GET_COMPANY_HISTORY = "/getCompanyHistory";
	private static final String SEARCH_COMPANY_HISTORY ="/searchCompanies";
	private static final String SEARCH_DIRECTOR_DETAILS = "/searchDirectors";
	private static final String GET_DIRECTOR_DETAILS = "/getDirectorsDetails";
	private static final String REQ_AUTH = "req_auth";
	private static final String MCA_SERVICE_ERROR = "MCA Service Error";
	private static final String INITIATE_MCACALL = "/initiateMCaCall";
	private static final String PLACE_ORDER_MCA = "/placeOrderMca";
	
	private static final String MCA_STATUS_CHECK = "/mcaStatusCheck";
	
	private static final String GET_COMPANY_DETAILED_DATA = "/getCompanyDetailedData";
	
	private static final String GET_COMPANY_CALCULATED_FINANCIAL_AND_DEATILS = "/getMcaFinancialAndDetails";
	
	/**	 Verify */
    private static final String REQUEST_VERIFY_API = "/verifyDIRPAN";
    private static final String GET_VERIFY_API_DATA = "/getVerifyApiData";

    /** Cubictree Api*/
    private static final String REQUEST_CUBICTREE_JOB_REGISTRATION = "/callJobRegistrationApi";
    private static final String GET_CUBICTREE_REPORT = "/getCubictreeReport";
    private static final String GET_COMPANY_FILENAME = "/getCompanyFileName";
    
	 private String mcaBaseUrl;
	 private RestTemplate restTemplate;
	 
	 public McaClient(String mcaBaseUrl) {
	  this.mcaBaseUrl = mcaBaseUrl;
	  restTemplate = new RestTemplate();
	 }
	 public McaResponse getCompanyHistory(McaRequest request) throws McaException {
		  String url = mcaBaseUrl.concat(GET_COMPANY_HISTORY);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<McaRequest> entity = new HttpEntity<McaRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MCA_SERVICE_ERROR, e);
			  throw new McaException(e);
		  }
		 }
		 public McaResponse mcaStatusCheck(String applicationId, String companyId) throws McaException {
		  String url = mcaBaseUrl.concat(MCA_STATUS_CHECK).concat("/"+applicationId).concat("/"+companyId);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  HttpEntity<McaRequest> entity = new HttpEntity<>(null, headers);
			  return restTemplate.exchange(url, HttpMethod.GET, entity, McaResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MCA_SERVICE_ERROR, e);
			  throw new McaException(e);
		  }
		 }
	 
	 public McaResponse searchCompanies(McaRequest request) throws McaException {
		  String url = mcaBaseUrl.concat(SEARCH_COMPANY_HISTORY);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<McaRequest> entity = new HttpEntity<McaRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MCA_SERVICE_ERROR, e);
			  throw new McaException(e);
		  }
		 }
	 
	 public McaResponse searchDirectors(McaRequest request) throws McaException {
		  String url = mcaBaseUrl.concat(SEARCH_DIRECTOR_DETAILS);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<McaRequest> entity = new HttpEntity<McaRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MCA_SERVICE_ERROR, e);
			  throw new McaException(e);
		  }
		 }
	 
	 public McaResponse getDirectorsDetails(McaRequest request) throws McaException {
		  String url = mcaBaseUrl.concat(GET_DIRECTOR_DETAILS);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<McaRequest> entity = new HttpEntity<McaRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MCA_SERVICE_ERROR, e);
			  throw new McaException(e);
		  }
		 }
	 
	 public McaResponse initiateMCaCall(Long applicationId) throws McaException {
		  String url = mcaBaseUrl.concat(INITIATE_MCACALL );
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  McaRequest request = new McaRequest();
			  request.setApplicationId(applicationId);
			  
			  HttpEntity<McaRequest> entity = new HttpEntity<McaRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MCA_SERVICE_ERROR, e);
			  throw new McaException(e);
		  }
		 }
	 
	 
	 public McaResponse placeOrderMca(McaRequest request) throws McaException {
		  String url = mcaBaseUrl.concat(PLACE_ORDER_MCA );
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<McaRequest> entity = new HttpEntity<McaRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MCA_SERVICE_ERROR, e);
			  throw new McaException(e);
		  }
		 }
	 
	 public McaResponse getCompanyDetailedData(String companyId) throws McaException {
			String url = mcaBaseUrl.concat(GET_COMPANY_DETAILED_DATA ).concat("/"+companyId);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(REQ_AUTH, "true");
				HttpEntity<McaRequest> entity = new HttpEntity<>(null, headers);
				return restTemplate.exchange(url, HttpMethod.GET, entity, McaResponse.class).getBody();

			} catch (NullPointerException e) {
				logger.error(MCA_SERVICE_ERROR, e);
				throw new McaException("MCA service not available");
			}
		}


	 public McaResponse getCompanyFinancialCalcAndDetails(Long applicationId,String companyId) throws McaException {
			 String url= mcaBaseUrl.concat(GET_COMPANY_CALCULATED_FINANCIAL_AND_DEATILS).concat("/"+applicationId+"/"+companyId);
			 logger.info("in getCompanyFinancialCalculationAndDetails client..."+" applicationId==>"+applicationId+" companyId==>"+companyId);
			 try {
				HttpHeaders headers=new HttpHeaders();
				headers.set(REQ_AUTH, "true");
				HttpEntity<McaRequest> entity=new HttpEntity<McaRequest>(null,headers);
				return restTemplate.exchange(url, HttpMethod.GET, entity, McaResponse.class).getBody();
			} catch (Exception e) {
				logger.error(McaCommonUtils.EXCEPTION, e);
				throw new McaException("MCA service not available");
			}
		 }	
	 public McaResponse requestVerifyApi(VerifyAPIRequest request) throws McaException {
         String url= mcaBaseUrl.concat(REQUEST_VERIFY_API);
         logger.info("requestVerifyApi :{}",request);
         logger.info("requestVerifyApi URL:{}",url);
         try {
            HttpHeaders headers=new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<VerifyAPIRequest> entity=new HttpEntity<VerifyAPIRequest>(request,headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();
        } catch (Exception e) {
            logger.error(McaCommonUtils.EXCEPTION, e);
            throw new McaException("MCA service not available");
        }
     }
	 public McaResponse getVerifyApiData(VerifyAPIRequest request) throws McaException {
			String url = mcaBaseUrl.concat(GET_VERIFY_API_DATA );
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(REQ_AUTH, "true");
				 headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<VerifyAPIRequest> entity = new HttpEntity<>(request, headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();

			} catch (NullPointerException e) {
				logger.error(MCA_SERVICE_ERROR, e);
				throw new McaException("MCA service not available");
			}
		}
	 public McaResponse callForjobRegistrationApi(CubictreeJobRegistrationRequest request) throws McaException {
			String url = mcaBaseUrl.concat(REQUEST_CUBICTREE_JOB_REGISTRATION);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(REQ_AUTH, "true");
				 headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<CubictreeJobRegistrationRequest> entity = new HttpEntity<>(request, headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();

			} catch (NullPointerException e) {
				logger.error(MCA_SERVICE_ERROR, e);
				throw new McaException("MCA service not available");
			}
		}
	 public McaResponse getCubictreeReport(Long applicationId) throws McaException {
		 String url = mcaBaseUrl.concat(GET_CUBICTREE_REPORT);
		 try {
			 HttpHeaders headers = new HttpHeaders();
			 headers.set(REQ_AUTH, "true");
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 HttpEntity<Long> entity = new HttpEntity<>(applicationId, headers);
			 return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();
			 
		 } catch (NullPointerException e) {
			 logger.error(MCA_SERVICE_ERROR, e);
			 throw new McaException("MCA service not available");
		 }
	 }
	 
	 public McaResponse getCompanyFileName(String companyId) throws McaException {
			String url = mcaBaseUrl.concat(GET_COMPANY_FILENAME ).concat("/"+companyId);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(REQ_AUTH, "true");
				HttpEntity<McaRequest> entity = new HttpEntity<>(null, headers);
				return restTemplate.exchange(url, HttpMethod.GET, entity, McaResponse.class).getBody();

			} catch (NullPointerException e) {
				logger.error(MCA_SERVICE_ERROR, e);
				throw new McaException("MCA service not available");
			}
		}
	 
	/* public McaResponse getVerifyApiData(VerifyAPIRequest request) throws McaException {
         String url= mcaBaseUrl.concat(GET_VERIFY_API_DATA);
         try {
            HttpHeaders headers=new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<VerifyAPIRequest> entity=new HttpEntity<VerifyAPIRequest>(null,headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, McaResponse.class).getBody();
        } catch (Exception e) {
            logger.error(McaCommonUtils.EXCEPTION, e);
            throw new McaException("MCA service not available");
        }
     }  */
	/* public McaResponse verifyApiAsync(VerifyAPIRequest request) throws McaException {
		  String url = mcaBaseUrl.concat(REQUEST_VERIFY_API);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<VerifyAPIRequest> entity = new HttpEntity<VerifyAPIRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, McaResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MCA_SERVICE_ERROR, e);
			  throw new McaException(e);
		  }
		 }*/
}
