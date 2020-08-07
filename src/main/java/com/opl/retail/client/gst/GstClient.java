/**
 * 
 */
package com.opl.retail.client.gst;

import com.opl.retail.api.exceptions.gst.GstException;
import com.opl.retail.api.model.gst.GSTMobileResponse;
import com.opl.retail.api.model.gst.GstResponse;
import com.opl.retail.api.model.gst.util.CommonUtils;
import com.opl.retail.api.model.gst.util.CommonUtils.Step;
import com.opl.retail.api.model.gst.yuva.request.GSTR1Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author sanket
 *
 */

public class GstClient {

	private final Logger logger = LoggerFactory.getLogger(GstClient.class);

	private static final String REQ_AUTH = "req_auth";

	private static final String GET_CALCULATIONS = "/getCalculations";

	private static final String CHECK_GSTIN = "/checkGstin";
	private static final String REQUEST_OTP_URI = "/requestOTP";
	private static final String GETOTPFORAUTHTOKEN = "/getOTPForAuthToken";
	private static final String GETGSTR1SUMMARY = "/getGstr1Summary";
	private static final String GETGSTR1SUMMARYDETAILSKARZA = "/getGstr1SummaryDetailsKarza";

	private static final String SEARCHTAXPAYERS = "/searchTaxpayers";
	
	
	// Mobile
	
	private static final String GET_KARZA_GST_DATA_MOBILE = "/mobile/getKarzaGstData";
	private static final String REQUEST_OTP_MOBILE  = "/mobile/requestOTP";
	private static final String GET_OTP_FOR_AUTH_TOKEN_MOBILE = "/mobile/getOTPForAuthToken";
	
	private static final String GET_ALL_GST_DATA_BY_GSTIN = "/getAllGstDataByGstin";
	
	private static final String GET_CALCULATION_FOR_SCORING = "/getCalculationForScoring";
	
	private static final String DETAIL_CALCULATION = "/detailCalculation";
	
	private static final String GET_COMPANY_TYPE_FOR_GSTIN = "getCompanyTypeForGSTIN";
	
	private static final String GET_GST_SEARCH_DATA = "/getGSTSearchData";
	
	private static final String GET_RELATED_PARTY_FLAG = "/getRelatedPartyFlag";
	
	private static final String GET_GSTR3B_AVG_TOTAL = "/getGstr3bTotalAvg";
	private static final String GET_GSTR3B_SALES_REQ_MONTHS = "/getGstr3bSalesReqMonths";
	
	private static final String GET_MONTH_LIST_FOR_SALES = "/getMonthListForSales";
	private static final String SAVE_SOMPOSITE_DATA = "/mobile/saveCompositeData";
	private static final String SAVE_GROWTH_SALES_DATA = "/mobile/saveDataForProjectedSalesGrowthData";
	private static final String GST_DATA_FOR_COMAPRE_BS = "/getGSTDataForBankStatmentComaparison";
	private static final String GET_BANK_COMPARISION_DATA = "/getBankComparisonData";
	private static final String GET_SPECIFIC_SEARCH_TP_BY_PAN = "/specific/searchTaxpayersByPan";
	private static final String GET_MULTIPLE_GSTIN_BY_PAN = "/specific/getPanApiData";
	private static final String GET_GST_DATA_BY_PAN = "/specific/getDetailCalculation";
	
	private static final String GET_CALCULATION_FOR_MULTIPLE_BANK = "/getCalculationForMultipleBank";
	private static final String GST_SPECIFIC_DETAIL_CALCULATION = "/specific/detailCalculation";
	private static final String GET_CALCULATIONS_FROM_GSTR1 = "/getCalculationsFromGSTR1";
	private static final String GET_CALCULATIONS_FROM_GSTR2AB2B  = "/getPurchaseCalculationsFromGSTR2AB2B";
	private static final String GET_CALCULATIONS_FROM_GSTR1_FOR_VFS  = "/getCalculationsFromGSTR1ForVFS";
	private static final String GET_KEY_PERSON_DATA  = "/get-key-person-data";
	private static final String CHECK_GST_DATA  = "/gst-data-check";
	private static final String GET_GST_MAPPING_DATA  = "/gst-mapping-data-nbj";

	private static final String URL = "url===>{}";
	private static final String USER_ID = "userId";
	
	private String gstBaseUrl;
	private RestTemplate restTemplate;

	public GstClient(String gstBaseUrl) {
		this.gstBaseUrl = gstBaseUrl;
		restTemplate = new RestTemplate();

	}

	public GstResponse manageGstFlowStep(GSTR1Request request) throws GstException {
		try {
			GstResponse response = null;
			if (request != null && request.getNextStep() != null) {

				switch (request.getCurrentStep()) {
				case REQUEST_OTP:
					response = requestOTP(request);
					if (response.getStatus() == 200) {
						if (response.getStatusCd() != null
								|| response.getStatusCd().equals(CommonUtils.ServiceStatus.ACCESS_DENIED)) {
							response.setCurrentStep(Step.REQUEST_OTP);
							response.setNextStep(Step.KARZA_GST);
							return response;
						} else if (response.getStatusCd().equals(CommonUtils.ServiceStatus.SEND_OTP)) {
							response.setCurrentStep(Step.REQUEST_OTP);
							response.setNextStep(Step.AUTH_TOKEN_FROM_OTP);
							return response;
						}
					} else {
						return response;
					}
				case AUTH_TOKEN_FROM_OTP:
					response = getOTPForAuthToken(request);
					if (response.getStatus() == 200) {
						if (response.getStatusCd() != null
								|| response.getStatusCd().equals(CommonUtils.ServiceStatus.ACCESS_DENIED)) {
							response.setCurrentStep(Step.AUTH_TOKEN_FROM_OTP);
							response.setNextStep(Step.KARZA_GST);
							return response;
						} else if (response.getStatusCd().equals(CommonUtils.ServiceStatus.USER_AUTHORIZED)) {
							response.setCurrentStep(Step.AUTH_TOKEN_FROM_OTP);
							response.setNextStep(null);
							return response;
						}

					} else {
						return response;
					}
				case GSTR_1_SUMMARY:
					response = getGstr1Summary(request);
					if (response.getStatus() == 200) {
						if (response.getStatusCd() != null
								|| response.getStatusCd().equals(CommonUtils.ServiceStatus.UNAUTHORIZEDUSER)) {
							response.setCurrentStep(Step.GSTR_1_SUMMARY);
							response.setNextStep(Step.REQUEST_OTP);
							return response;
						} else if (response.getStatusCd().equals(CommonUtils.ServiceStatus.USER_NOT_VALID)) {
							response.setCurrentStep(Step.GSTR_1_SUMMARY);
							response.setNextStep(Step.REQUEST_OTP);
							return response;
						} else {
							response.setCurrentStep(Step.GSTR_1_SUMMARY);
							return response;
						}
					} else {
						return response;
					}
				case KARZA_GST:
					response = getGstr1Summary(request);
					if (response.getStatus() == 200) {
						if (response.getStatusCd() != null
								|| response.getStatusCd().equals(HttpStatus.OK.value() + "")) {
							response.setCurrentStep(Step.KARZA_GST);
							// response.setNextStep(Step.REQUEST_OTP);
							return response;
						} else {
							response.setCurrentStep(Step.KARZA_GST);
							return response;
						}
					} else {
						return response;
					}
				default:
					return new GstResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), CommonUtils.SOMETHING_WENT_WRONG);
				}

			}

			return new GstResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), CommonUtils.SOMETHING_WENT_WRONG);
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public GstResponse getCalculations(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_CALCULATIONS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();			
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}

//	public GstResponse checkGstin(GSTR1Request request) throws Exception {
//		String url = gstBaseUrl.concat(CHECK_GSTIN);
//		try {
//			HttpHeaders headers = new HttpHeaders();
//			headers.set(CommonUtils.REQ_AUTH, "true");
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			HttpEntity<GSTR1Request> entity = new HttpEntity<GSTR1Request>(request, headers);
//			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
//		} catch (Exception e) {
//			logger.error(CommonUtils.GST_SERVICE_ERROR, e);
//			throw new Exception(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
//		}
//	}

	public GstResponse requestOTP(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(REQUEST_OTP_URI);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.set(USER_ID, request.getUserId().toString());
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public GstResponse getOTPForAuthToken(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GETOTPFORAUTHTOKEN);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.set(USER_ID, request.getUserId().toString());
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public GstResponse getGstr1Summary(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GETGSTR1SUMMARY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public GstResponse getGstr1SummaryDetailsKarza(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GETGSTR1SUMMARYDETAILSKARZA);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public GstResponse searchTaxpayers(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(SEARCHTAXPAYERS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e);
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	
	// Mobile Connector
	
	public GSTMobileResponse getKarzaGSTDataForMobile(GSTR1Request request) throws GstException{
		String url = gstBaseUrl.concat(GET_KARZA_GST_DATA_MOBILE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GSTMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e);
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	public GSTMobileResponse requestOTPForMobile(GSTR1Request request) throws GstException{
		String url = gstBaseUrl.concat(REQUEST_OTP_MOBILE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(USER_ID, request.getUserId().toString());
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GSTMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e);
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public GSTMobileResponse getOTPForAuthTokenForMobile(GSTR1Request request) throws GstException{
		String url = gstBaseUrl.concat(GET_OTP_FOR_AUTH_TOKEN_MOBILE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(USER_ID, request.getUserId().toString());
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GSTMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e);
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	 public GstResponse getCompanyDetailedData(String gstin) throws GstException {
			String url = gstBaseUrl.concat(GET_ALL_GST_DATA_BY_GSTIN ).concat("/"+gstin);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(CommonUtils.REQ_AUTH, "true");
				HttpEntity<?> entity = new HttpEntity<>(null, headers);
				return restTemplate.exchange(url, HttpMethod.GET, entity, GstResponse.class).getBody();

			} catch (Exception e) {
				logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
			}
		}
	 
	 public GstResponse getCalculationForScoring(GSTR1Request request) throws GstException {
			String url = gstBaseUrl.concat(GET_CALCULATION_FOR_SCORING);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(CommonUtils.REQ_AUTH, "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();

			} catch (Exception e) {
				logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
			}
		}
	 
	 public GstResponse detailCalculation(GSTR1Request request) throws GstException {
			String url = gstBaseUrl.concat(DETAIL_CALCULATION);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(CommonUtils.REQ_AUTH, "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();

			} catch (Exception e) {
				logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
			}
		}
	 
	 public GstResponse getCompanyTypeForGSTIN(String gstin) throws GstException {
			String url = gstBaseUrl.concat(GET_COMPANY_TYPE_FOR_GSTIN).concat("/"+gstin);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(CommonUtils.REQ_AUTH, "true");
				HttpEntity<?> entity = new HttpEntity<>(null, headers);
				return restTemplate.exchange(url, HttpMethod.GET, entity, GstResponse.class).getBody();

			} catch (Exception e) {
				logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
			}
		}
	 
	 public GstResponse getGSTSearchData(GSTR1Request request) throws GstException {
			String url = gstBaseUrl.concat(GET_GST_SEARCH_DATA);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(CommonUtils.REQ_AUTH, "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set(USER_ID, request.getUserId().toString());
				HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();

			} catch (Exception e) {
				logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
			}
		}
	 
	 public GstResponse getMonthListForSales() throws GstException {
			String url = gstBaseUrl.concat(GET_MONTH_LIST_FOR_SALES);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(CommonUtils.REQ_AUTH, "true");
				HttpEntity<?> entity = new HttpEntity<>(null, headers);
				return restTemplate.exchange(url, HttpMethod.GET, entity, GstResponse.class).getBody();

			} catch (Exception e) {
				logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
			}
		}
	 
	 public GstResponse saveCompositeData(GSTR1Request request) throws GstException {
			String url = gstBaseUrl.concat(SAVE_SOMPOSITE_DATA);
			logger.info(URL,url);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(CommonUtils.REQ_AUTH, "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();

			} catch (Exception e) {
				logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
			}
		}
	 
	 public GstResponse saveGrowthProjectedSales(GSTR1Request request) throws GstException {
			String url = gstBaseUrl.concat(SAVE_GROWTH_SALES_DATA);
			logger.info(URL,url);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(CommonUtils.REQ_AUTH, "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();

			} catch (Exception e) {
				logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
			}
		}
	 
	 
	 public GstResponse getGSTDataForBankStatmentComaparison(GSTR1Request request) throws GstException {
			String url = gstBaseUrl.concat(GST_DATA_FOR_COMAPRE_BS);
			logger.info(URL,url);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(CommonUtils.REQ_AUTH, "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();

			} catch (Exception e) {
				logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
				throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
			}
		}
	 
	 /**
	 * @author : Maaz Shaikh
	 * @Time : 16-Jul-2019 - 5:24:21 PM
	 * @return GstResponse 
	 * @param request in GSTR1Request
	 */
	public GstResponse getbankComparisonData(GSTR1Request request) throws GstException {
		 String url = gstBaseUrl.concat(GET_BANK_COMPARISION_DATA);
		 logger.info(URL,url);
		 try {
			 HttpHeaders headers = new HttpHeaders();
			 headers.set(CommonUtils.REQ_AUTH, "true");
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			 return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
			 
		 } catch (Exception e) {
			 logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			 throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		 }
	 }
	 
	/**
	 * @author vijay.chauhan
	 * @Time : 19-Aug-2019
	 * @param request
	 * @return
	 * @throws GstException
	 */
	public GstResponse gstSpecificSearchTPByPan(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_SPECIFIC_SEARCH_TP_BY_PAN);
		logger.info(URL,url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
			
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e);
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	
	/**
	 * @author nilay.darji
	 * @param request
	 * @return
	 * @throws GstException
	 */
	public GstResponse getMultipleGstInByPan(GSTR1Request request) throws GstException {
		 String url = gstBaseUrl.concat(GET_MULTIPLE_GSTIN_BY_PAN);
		 logger.info(URL,url);
		 try {
			 HttpHeaders headers = new HttpHeaders();
			 headers.set(CommonUtils.REQ_AUTH, "true");
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			 return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
			 
		 } catch (Exception e) {
			 logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			 throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		 }
	 }
	
	public GstResponse getGstDataByPan(GSTR1Request request) throws GstException {
		 String url = gstBaseUrl.concat(GET_GST_DATA_BY_PAN);
		 logger.info(URL,url);
		 try {
			 HttpHeaders headers = new HttpHeaders();
			 headers.set(CommonUtils.REQ_AUTH, "true");
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			 return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
			 
		 } catch (Exception e) {
			 logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			 throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		 }
	 }
	
	public GstResponse getCalculationForMultipleBank(GSTR1Request request) throws GstException {
		 String url = gstBaseUrl.concat(GET_CALCULATION_FOR_MULTIPLE_BANK);
		 logger.info(URL,url);
		 try {
			 HttpHeaders headers = new HttpHeaders();
			 headers.set(CommonUtils.REQ_AUTH, "true");
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			 return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
			 
		 } catch (Exception e) {
			 logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			 throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		 }
	 }	
	public GstResponse gstSpecificDetailCalculation(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GST_SPECIFIC_DETAIL_CALCULATION);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();

		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}	
	public GstResponse getRelatedPartyFlag(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_RELATED_PARTY_FLAG);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();			
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	/**
	 * @author nilay.darji
	 * @param request
	 * @return
	 * @throws GstException
	 * <p>get average total of gstr3b by applicationId and gstIn of Gstr1Request.</p>
	 */
	public GstResponse getGstr3bTotalAvgSales(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_GSTR3B_AVG_TOTAL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	/**
	 * @author nilay.darji
	 * @param request
	 * @return
	 * @throws GstException
	 * <p>get sales of gstr3b as per requested months.</p>
	 */
	public GstResponse getGstr3bSalesOfRequestedMonths(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_GSTR3B_SALES_REQ_MONTHS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	public GstResponse getCalculationsFromGSTR1(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_CALCULATIONS_FROM_GSTR1);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();			
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public GstResponse getPurchaseCalculationsFromGSTR2AB2B(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_CALCULATIONS_FROM_GSTR2AB2B);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();			
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	public GstResponse getPurchaseCalculationsFromGSTR1ForVFS(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_CALCULATIONS_FROM_GSTR1_FOR_VFS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();			
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	public GstResponse getKeyPersonDetails(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_KEY_PERSON_DATA);
		logger.info("URL ==> {}",url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();			
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	public GstResponse checkGstDataUpdatedOrNot(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(CHECK_GST_DATA);
		logger.info("URL ==> {}",url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();			
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public GstResponse getGstMappingDataForNBJ(GSTR1Request request) throws GstException {
		String url = gstBaseUrl.concat(GET_GST_MAPPING_DATA);
		logger.info("URL ==> {}",url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(CommonUtils.REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<GSTR1Request> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, GstResponse.class).getBody();			
		} catch (Exception e) {
			logger.error(CommonUtils.GST_SERVICE_ERROR, e.toString());
			throw new GstException(CommonUtils.GST_SERVICE_IS_NOT_AVAILABLE);
		}
	}
}
