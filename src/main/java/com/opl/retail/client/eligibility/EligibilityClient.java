package com.opl.retail.client.eligibility;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.exceptions.eligibility.EligibilityExceptions;
import com.opl.retail.api.model.eligibility.CoApplicantEligibilityRequest;
import com.opl.retail.api.model.eligibility.EligibililityRequest;
import com.opl.retail.api.model.eligibility.EligibilityResponse;
import com.opl.retail.api.model.eligibility.HLEligibilityRequest;
import com.opl.retail.api.model.eligibility.LAPEligibilityRequest;
import com.opl.retail.api.model.eligibility.MFIRequest;
import com.opl.retail.api.model.eligibility.OnePageEligibilityResponse;
import com.opl.retail.api.model.eligibility.PLEligibilityRequest;
import com.opl.retail.api.utils.eligibility.EligibilityUtils;

public class EligibilityClient {

	private static final Logger logger = LoggerFactory.getLogger(EligibilityClient.class);

	private static final String CALCULATE_PERSONAL_LOAN_ELIGIBILITY = "/ct/calc_personal_eligible";
	
	private static final String CALCULATE_NTB_LOAN_ELIGIBILITY = "/cr/calc_ntb_eligible";
	private static final String CALCULATE_CORPORATE_ELIGIBILITY_DATA = "/cr/get_eligibility_data";
	private static final String CALCULATE_ONE_PAGE_ELIGIBILITY_DATA = "/el/calc_one_page_eligible";
	private static final String GET_RETAIL_ELIGIBILITY_DATA = "/ct/get_personal_eligibility_data";
	private static final String GET_HL_ELEGIBLITY_DATA = "/ct/get_home_loan_eligibility_data";
	private static final String CALCULATE_NTB_ELIGIBILITY_DATA = "/cr/get_ntb_eligibility_data";
	private static final String CALCULATE_CORPORATE_LOAN_ELIGIBILITY = "/cr/calc_corporate_eligible";
	private static final String CALCULATE_HOME_LOAN_ELIGIBILITY_FOR_UBI = "/hl/calc_eligibiity_client";
	private static final String MOBILE_LOAN_ELIGIBILITY_HL_CALC_MINMAX = "/hl/calc_min_max";
	private static final String ELIGIBILITY_HL = "/hl/calculate_eligibility";
	private static final String ELIGIBILITY_AL = "/al/calculate_eligibility";
	private static final String ELIGIBILITY_BASED_ON_INCOME_HL = "/hl/calculate_eligibility_based_on_income";
	private static final String ELIGIBILITY_FINAL_HL = "/hl/calculate_eligibility_final";
	private static final String MOBILE_LOAN_ELIGIBILITY_HL_GET_ELIGIBLE_TENURE = "/hl/get_eligible_tenure";
	private static final String MOBILE_LOAN_ELIGIBILITY_HL_CALC_LOAN_AMOUNT = "/hl/calc_home_loan_amount";
	private static final String MOBILE_LOAN_ELIGIBILITY_PL_GET_ELIGIBLE_TENURE = "/pl/get_eligible_tenure";
	private static final String MOBILE_LOAN_ELIGIBILITY_PL_CALC_MINMAX = "/pl/calc_min_max";
	private static final String MOBILE_LOAN_ELIGIBILITY_LAP_GET_ELIGIBLE_TENURE = "/lap/get_eligible_tenure";
	private static final String MOBILE_LOAN_ELIGIBILITY_LAP_CALC_MINMAX = "/lap/calc_min_max";
	private static final String MOBILE_LOAN_ELIGIBILITY_LAP_CALC_LAP_AMOUNT = "/lap/calc_lap_amount";
	private static final String CALCULATE_GET_MOTHLY_INCOME_ELIGIBILITY = "/ct/get/monthlyIncome";
	private static final String CALCULATE_GET_MOTHLY_INCOME_ELIGIBILITY_PL = "/ct/get/monthlyIncomePL";
	private static final String CALCULATE_GET_MOTHLY_INCOME_ELIGIBILITY_CO_APPLICANT = "/ct/get/mon_inc_co_app";
	private static final String CALCULATE_MFI_LOAN_CALCULATION = "/mfi/calculate_mfi";
	private static final String GET_MFI_ELEGIBLITY_DATA = "/mfi/get_mfi_data";
	
	private String eligibilityBaseUrl;
	private RestTemplate restTemplate;

	private static final String ELIGIBILILITYREQUEST_NULL_EMPTY_MSG ="List of EligibililityRequest is Null Or Empty";
	private static final String ELIGIBILITY_URL_MSG = "Eligibility url=============>";
	private static final String REQ_AUTH ="req_auth";
	private static final String CLIENT_REQUEST_OBJECT_MSG = "Client Request Object=============>";
	
	private static final String GET_ONEPAGER_ELIGIBILITY_DATA="/el/get_one_pager_eligibility_data";





	public EligibilityClient(String eligibilityBaseUrl) {
		this.eligibilityBaseUrl = eligibilityBaseUrl;
		restTemplate = new RestTemplate();
	}

	public EligibilityResponse corporateLoanCalculation(List<EligibililityRequest> eligibilityRequest)
			throws EligibilityExceptions {
		if(EligibilityUtils.isListNullOrEmpty(eligibilityRequest)) {
			logger.info("List is Null while getting ");
			throw new NullPointerException(ELIGIBILILITYREQUEST_NULL_EMPTY_MSG);
		}
		String url = eligibilityBaseUrl.concat(CALCULATE_CORPORATE_LOAN_ELIGIBILITY);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<List<EligibililityRequest>> entity = new HttpEntity<List<EligibililityRequest>>(
					eligibilityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION +" corporateLoanCalculation :: " ,e);
			throw new EligibilityExceptions(EligibilityUtils.SOMETHING_WENT_WRONG);
		}
	}
	
	public EligibilityResponse ntbLoanCalculation(List<EligibililityRequest> eligibilityRequest)
			throws EligibilityExceptions {
		if(EligibilityUtils.isListNullOrEmpty(eligibilityRequest)) {
			logger.info("List is Null while getting ntb data ");
			throw new NullPointerException(ELIGIBILILITYREQUEST_NULL_EMPTY_MSG);
		}
		String url = eligibilityBaseUrl.concat(CALCULATE_NTB_LOAN_ELIGIBILITY);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<List<EligibililityRequest>> entity = new HttpEntity<List<EligibililityRequest>>(
					eligibilityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION +" ntbLoanCalculation :: " ,e);
			throw new EligibilityExceptions(EligibilityUtils.SOMETHING_WENT_WRONG);
		}
	}
	
	public EligibilityResponse mfiLoanCalculation(List<MFIRequest> mfiRequest)
			throws EligibilityExceptions {
		if(EligibilityUtils.isListNullOrEmpty(mfiRequest)) {
			logger.info("List is Null Or Empty----{}");
			throw new NullPointerException(ELIGIBILILITYREQUEST_NULL_EMPTY_MSG);
		}
		String url = eligibilityBaseUrl.concat(CALCULATE_MFI_LOAN_CALCULATION);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<List<MFIRequest>> entity = new HttpEntity<List<MFIRequest>>(
					mfiRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION +" MFI LOAN CALCULATION :: " ,e);
			throw new EligibilityExceptions(EligibilityUtils.SOMETHING_WENT_WRONG);
		}
	}

	// FOR PERSNAL LOAN PURPOSE-------------
	public EligibilityResponse persnalLoanCalculation(List<EligibililityRequest> eligibilityRequest)
			throws EligibilityExceptions {
		if(EligibilityUtils.isListNullOrEmpty(eligibilityRequest)) {
			logger.info("List is Null while getting PERSNAL LOAN DATA");
			throw new NullPointerException(ELIGIBILILITYREQUEST_NULL_EMPTY_MSG);
		}
		String url = eligibilityBaseUrl.concat(CALCULATE_PERSONAL_LOAN_ELIGIBILITY);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<List<EligibililityRequest>> entity = new HttpEntity<List<EligibililityRequest>>(
					eligibilityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " persnalLoanCalculation : " ,e);
			throw new EligibilityExceptions(EligibilityUtils.SOMETHING_WENT_WRONG);
		}
	}

	

	public EligibilityResponse corporateLoanData(EligibililityRequest eligibilityRequest)
			throws EligibilityExceptions {
		logger.info(CLIENT_REQUEST_OBJECT_MSG + eligibilityRequest.toString());
		
		String url = eligibilityBaseUrl.concat(CALCULATE_CORPORATE_ELIGIBILITY_DATA);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EligibililityRequest> entity = new HttpEntity<EligibililityRequest>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + "corporateLoanData :: "  ,e);
			throw new EligibilityExceptions(
					"Eligibilty service is not available while call eligibility calculation data");
		}
	}
	
	
	public EligibilityResponse onePageEligibilityData(List<EligibililityRequest> eligibilityRequests)
			throws EligibilityExceptions {
		logger.info(CLIENT_REQUEST_OBJECT_MSG + eligibilityRequests.toString());
		
		String url = eligibilityBaseUrl.concat(CALCULATE_ONE_PAGE_ELIGIBILITY_DATA);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<EligibililityRequest>> entity = new HttpEntity<>(eligibilityRequests,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + "one page eligibility Data  :: "  ,e);
			throw new EligibilityExceptions(
					"Eligibilty service is not available while call one page eligibility data");
		}
	}
	
	
	

	// GETTING DATA FOR DISPLAY TEASER AND CAM REPORT --->
	public EligibilityResponse getRetailLoanData(EligibililityRequest eligibilityRequest)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(GET_RETAIL_ELIGIBILITY_DATA);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EligibililityRequest> entity = new HttpEntity<EligibililityRequest>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " getRetailLoanData : " ,e);
			throw new EligibilityExceptions(
					"Eligibilty service is not available while call getting eligibility data");
		}
	}
	// GETTING DATA FOR DISPLAY TEASER AND CAM REPORT For HL--->
	public EligibilityResponse getHLLoanData(EligibililityRequest eligibilityRequest)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(GET_HL_ELEGIBLITY_DATA);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EligibililityRequest> entity = new HttpEntity<EligibililityRequest>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();
			
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " getRetailLoanData : " ,e);
			throw new EligibilityExceptions(
					"Eligibilty service is not available while call getting eligibility data");
		}
	}
	
	// GETTING DATA FOR DISPLAY TEASER AND CAM REPORT For HL--->
		public EligibilityResponse getMfiLoanDetails(MFIRequest mfiRequest)
				throws EligibilityExceptions {
			String url = eligibilityBaseUrl.concat(GET_MFI_ELEGIBLITY_DATA);
			logger.info(ELIGIBILITY_URL_MSG + url);
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set(REQ_AUTH, "true");
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<MFIRequest> entity = new HttpEntity<MFIRequest>(mfiRequest,
						headers);
				return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();
				
			} catch (Exception e) {
				logger.error(EligibilityUtils.EXCEPTION + " loan mfi data : " ,e);
				throw new EligibilityExceptions("ELIGIBILITY SERVICE IS NOT AVAILABLE WHILE CALL LOAN MFI DATA ===");
			}
		}
	
	public EligibilityResponse ntbLoanData(EligibililityRequest eligibilityRequest)
			throws EligibilityExceptions {
		logger.info(CLIENT_REQUEST_OBJECT_MSG + eligibilityRequest.toString());
		
		String url = eligibilityBaseUrl.concat(CALCULATE_NTB_ELIGIBILITY_DATA);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EligibililityRequest> entity = new HttpEntity<EligibililityRequest>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " ntbLoanData : " ,e);
			throw new EligibilityExceptions(
					"Eligibilty service is not available while call eligibility ntb calculation data");
		}
	}
	
	
//	public EligibilityResponse corporateLoanData(List<EligibililityRequest> eligibilityRequest)
//			throws Exception {
//		if(EligibilityUtils.isListNullOrEmpty(eligibilityRequest)) {
//			logger.info("List is Null while getting ");
//			throw new NullPointerException(ELIGIBILILITYREQUEST_NULL_EMPTY_MSG);
//		}
//		logger.info(CLIENT_REQUEST_OBJECT_MSG + eligibilityRequest.toString());
//		String url = eligibilityBaseUrl.concat(CALCULATE_CORPORATE_LOAN_DATA);
//		logger.info(ELIGIBILITY_URL_MSG + url);
//		try {
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			headers.set(REQ_AUTH, "true");
//			HttpEntity<List<EligibililityRequest>> entity = new HttpEntity<List<EligibililityRequest>>(
//					eligibilityRequest, headers);
//			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception(EligibilityUtils.SOMETHING_WENT_WRONG);
//		}
//	}

	public HLEligibilityRequest HLCalculation(HLEligibilityRequest eligibilityRequest) throws EligibilityExceptions {
		if (EligibilityUtils.isObjectListNull(eligibilityRequest, eligibilityRequest.getCode(),
				eligibilityRequest.getUserId(), eligibilityRequest.getApplicationId())) {
			logger.info("Someone is NULL in (Whole Object,Code,ApplicationId,UserId) Please verify you data");
			throw new NullPointerException("Code,ApplicationId,UserId Must not be NULL or Empty");
		}
		logger.info(CLIENT_REQUEST_OBJECT_MSG + eligibilityRequest.toString());
		String url = eligibilityBaseUrl.concat(CALCULATE_HOME_LOAN_ELIGIBILITY_FOR_UBI);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<HLEligibilityRequest> entity = new HttpEntity<HLEligibilityRequest>(eligibilityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, HLEligibilityRequest.class).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " HLCalculation : " ,e);
			throw new EligibilityExceptions(EligibilityUtils.SOMETHING_WENT_WRONG);
		}
	}

	public EligibilityResponse mobileHlCalcMinMax(HLEligibilityRequest homeLoanRequest) throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_HL_CALC_MINMAX);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");

			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HLEligibilityRequest> entity = new HttpEntity<HLEligibilityRequest>(homeLoanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " mobileHlCalcMinMax : " ,e);
			throw new EligibilityExceptions(
					"Eligibility service is not available while call Mobile Loan Eligibility HomeLoan Calc Min Max for mobile app");
		}
	}

	public EligibilityResponse mobileHlGetEligibleTenure(HLEligibilityRequest homeLoanRequest)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_HL_GET_ELIGIBLE_TENURE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<HLEligibilityRequest> entity = new HttpEntity<HLEligibilityRequest>(homeLoanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " mobileHlGetEligibleTenure :: " ,e);
			throw new EligibilityExceptions(
					"Eligibility service is not available while call Mobile Loan Eligibility HomeLoan get eligible tenure for mobile app");
		}
	}

	public EligibilityResponse mobileHlCalcHomeLoanAmount(HLEligibilityRequest homeLoanRequest)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_HL_CALC_LOAN_AMOUNT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HLEligibilityRequest> entity = new HttpEntity<HLEligibilityRequest>(homeLoanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " mobileHlCalcHomeLoanAmount :: " ,e);
			throw new EligibilityExceptions(
					"Eligibility service is not available while call Mobile Loan Eligibility HomeLoan calc loan amount for mobile app");
		}
	}

	public EligibilityResponse mobilePlGetEligibleTenure(PLEligibilityRequest eligibilityRequest)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_PL_GET_ELIGIBLE_TENURE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<PLEligibilityRequest> entity = new HttpEntity<PLEligibilityRequest>(eligibilityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " mobilePlGetEligibleTenure :: " ,e);
			throw new EligibilityExceptions(
					"Eligibilty service is not available while call Mobile Loan Eligibility PersonalLoan get eligible amount for mobile app");
		}
	}

	public EligibilityResponse mobilePlCalcMinMax(PLEligibilityRequest eligibilityRequest)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_PL_CALC_MINMAX);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<PLEligibilityRequest> entity = new HttpEntity<PLEligibilityRequest>(eligibilityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " mobilePlCalcMinMax :: " ,e);
			throw new EligibilityExceptions(
					"Eligibility service is not available while call Mobile Loan Eligibility PersonalLoan calc min max for mobile app");
		}
	}

	public EligibilityResponse mobileLapGetEligibleTenure(LAPEligibilityRequest eligibilityRequest)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_LAP_GET_ELIGIBLE_TENURE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LAPEligibilityRequest> entity = new HttpEntity<LAPEligibilityRequest>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " mobileLapGetEligibleTenure :: " ,e);
			throw new EligibilityExceptions(
					"Eligibilty service is not available while call Mobile Loan Eligibility LAP get eligible tenure for mobile app");
		}
	}

	public EligibilityResponse mobileLapCalcMinMax(LAPEligibilityRequest eligibilityRequest)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_LAP_CALC_MINMAX);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LAPEligibilityRequest> entity = new HttpEntity<LAPEligibilityRequest>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " mobileLapCalcMinMax :: " ,e);
			throw new EligibilityExceptions(
					"Eligibility service is not available while call Mobile Loan Eligibility LAP Calc Min max for mobile app");
		}
	}

	public EligibilityResponse mobileLapCalcLapAmount(LAPEligibilityRequest eligibilityRequest)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_LAP_CALC_LAP_AMOUNT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LAPEligibilityRequest> entity = new HttpEntity<LAPEligibilityRequest>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " mobileLapCalcLapAmount :: " ,e);
			throw new EligibilityExceptions(
					"Eligibility service is not available while call Mobile Loan Eligibility LAP calc lap amount for mobile app");
		}
	}

	public EligibilityResponse getMonthlyIncome(EligibililityRequest eligibililityRequest) throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(CALCULATE_GET_MOTHLY_INCOME_ELIGIBILITY);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<EligibililityRequest> entity = new HttpEntity<EligibililityRequest>(eligibililityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " getMonthlyIncome HL AND AL:: " ,e);
			throw new EligibilityExceptions(EligibilityUtils.SOMETHING_WENT_WRONG);
		}
	}

	public EligibilityResponse getMonthlyIncomePL(EligibililityRequest eligibililityRequest) throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(CALCULATE_GET_MOTHLY_INCOME_ELIGIBILITY_PL);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<EligibililityRequest> entity = new HttpEntity<EligibililityRequest>(eligibililityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " getMonthlyIncome PL :: " ,e);
			throw new EligibilityExceptions(EligibilityUtils.SOMETHING_WENT_WRONG);
		}
	}
	
	
	public List<CoApplicantEligibilityRequest> getMonthlyIncomeForCoApplicant(List<CoApplicantEligibilityRequest> coAplicantRequest,Long applicationId) throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(CALCULATE_GET_MOTHLY_INCOME_ELIGIBILITY_CO_APPLICANT).concat("/").concat(applicationId.toString());
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<List<CoApplicantEligibilityRequest>> entity = new HttpEntity<>(coAplicantRequest,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<CoApplicantEligibilityRequest>>() {}).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + " getMonthlyIncomeForCoApplicant :: " ,e);
			throw new EligibilityExceptions(EligibilityUtils.SOMETHING_WENT_WRONG + " " + e.getMessage());
		}
	}
	
	/* one pager eligibility Data client By Nilay.... */
	
	public EligibilityResponse getOnePagerEligibilityData(OnePageEligibilityResponse eligibilityRequest)
			throws EligibilityExceptions {
		logger.info(CLIENT_REQUEST_OBJECT_MSG + eligibilityRequest.toString());
		
		String url = eligibilityBaseUrl.concat(GET_ONEPAGER_ELIGIBILITY_DATA);
		logger.info(ELIGIBILITY_URL_MSG + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<OnePageEligibilityResponse> entity = new HttpEntity<OnePageEligibilityResponse>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, EligibilityResponse.class).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + "corporateLoanData :: "  ,e);
			throw new EligibilityExceptions(
					"Eligibilty service is not available while call eligibility calculation data");
		}
	}
	
	public List<HLEligibilityRequest> getHLEligibility(List<HLEligibilityRequest> eligibilityRequests)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(ELIGIBILITY_HL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<HLEligibilityRequest>> entity = new HttpEntity<>(eligibilityRequests,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<HLEligibilityRequest>>() {
            }).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + "getHLEligibility :: "  ,e);
			throw new EligibilityExceptions("Error while calculating HL Eligibility");
		}
	}
	
	public List<HLEligibilityRequest> getALEligibility(List<HLEligibilityRequest> eligibilityRequests)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(ELIGIBILITY_AL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<HLEligibilityRequest>> entity = new HttpEntity<>(eligibilityRequests,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<HLEligibilityRequest>>() {
            }).getBody();

		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + "getHLEligibility :: "  ,e);
			throw new EligibilityExceptions("Error while calculating HL Eligibility");
		}
	}
	
	public HLEligibilityRequest getHLEligibilityBasedOnIncome(HLEligibilityRequest eligibilityRequests)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(ELIGIBILITY_BASED_ON_INCOME_HL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HLEligibilityRequest> entity = new HttpEntity<>(eligibilityRequests,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, HLEligibilityRequest.class).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + "getHLEligibility :: "  ,e);
			throw new EligibilityExceptions("Error while calculating HL Eligibility");
		}
	}
	public HLEligibilityRequest getHLEligibilityFinal(HLEligibilityRequest eligibilityRequests)
			throws EligibilityExceptions {
		String url = eligibilityBaseUrl.concat(ELIGIBILITY_FINAL_HL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HLEligibilityRequest> entity = new HttpEntity<>(eligibilityRequests,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, HLEligibilityRequest.class).getBody();
		} catch (Exception e) {
			logger.error(EligibilityUtils.EXCEPTION + "getHLEligibility :: "  ,e);
			throw new EligibilityExceptions("Error while calculating HL Eligibility");
		}
	}

}
