package com.opl.retail.client.loans;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

import com.opl.retail.api.exceptions.loans.ExcelException;
import com.opl.retail.api.exceptions.loans.LoansException;
import com.opl.retail.api.model.loans.CMADetailResponse;
import com.opl.retail.api.model.loans.CreditRatingOrganizationDetailRequest;
import com.opl.retail.api.model.loans.DirectorBackgroundDetailRequest;
import com.opl.retail.api.model.loans.ExcelRequest;
import com.opl.retail.api.model.loans.ExcelResponse;
import com.opl.retail.api.model.loans.FinancialArrangementsDetailRequest;
import com.opl.retail.api.model.loans.FrameRequest;
import com.opl.retail.api.model.loans.GenerateTokenRequest;
import com.opl.retail.api.model.loans.HomeLoanModelRequest;
import com.opl.retail.api.model.loans.InEligibleProposalDetailsRequest;
import com.opl.retail.api.model.loans.LoanApplicationRequest;
import com.opl.retail.api.model.loans.LoansResponse;
import com.opl.retail.api.model.loans.NTBRequest;
import com.opl.retail.api.model.loans.PaymentRequest;
import com.opl.retail.api.model.loans.common.EkycRequest;
import com.opl.retail.api.model.loans.common.HomeLoanEligibilityRequest;
import com.opl.retail.api.model.loans.common.LAPEligibilityRequest;
import com.opl.retail.api.model.loans.common.LogDetailsModel;
import com.opl.retail.api.model.loans.common.PersonalLoanEligibilityRequest;
import com.opl.retail.api.model.loans.common.UserLoanAmountMappingRequest;
import com.opl.retail.api.model.loans.corporate.CMARequest;
import com.opl.retail.api.model.loans.corporate.CorporateApplicantRequest;
import com.opl.retail.api.model.loans.corporate.CorporateDirectorIncomeRequest;
import com.opl.retail.api.model.loans.corporate.FinalTermLoanRequest;
import com.opl.retail.api.model.loans.corporate.FinalWorkingCapitalLoanRequest;
import com.opl.retail.api.model.loans.corporate.FundSeekerInputRequestResponse;
import com.opl.retail.api.model.loans.corporate.PrimaryCorporateRequest;
import com.opl.retail.api.model.loans.corporate.PrimaryTermLoanRequest;
import com.opl.retail.api.model.loans.corporate.PrimaryWorkingCapitalLoanRequest;
import com.opl.retail.api.model.loans.mfi.MFIFinancialArrangementRequest;
import com.opl.retail.api.model.loans.mobile.MRetailApplicantResponse;
import com.opl.retail.api.model.loans.mobile.MRetailCoAppGuarResponse;
import com.opl.retail.api.model.loans.mobile.MobileApiResponse;
import com.opl.retail.api.model.loans.mobile.MobileFPMatchesRequest;
import com.opl.retail.api.model.loans.mobile.MobileFrameRequest;
import com.opl.retail.api.model.loans.mobile.MobileLoanRequest;
import com.opl.retail.api.model.loans.retail.BankRelationshipRequest;
import com.opl.retail.api.model.loans.retail.CoApplicantRequest;
import com.opl.retail.api.model.loans.retail.CreditCardsDetailRequest;
import com.opl.retail.api.model.loans.retail.ExistingLoanDetailRequest;
import com.opl.retail.api.model.loans.retail.RetailApplicantIncomeRequest;
import com.opl.retail.api.model.loans.retail.RetailApplicantRequest;
import com.opl.retail.api.model.loans.retail.RetailOnformContactInfoReq;
import com.opl.retail.api.model.loans.score.ScoringRequestLoans;
import com.opl.retail.api.utils.loans.CommonUtils;
import com.opl.retail.api.utils.loans.CommonUtils.LoanType;

public class LoansClient {

	private static final String READ_CMA = "/cw_excel/read_cma";
	private static final String INACTIVE_CMA = "/cw_excel/inactive_cma";
	private static final String READ_DPR = "/cw_excel/read_dpr";
	private static final String INACTIVE_DPR = "/cw_excel/inactive_dpr";
	private static final String READ_BS = "/cw_excel/read_bs";
	private static final String INACTIVE_BS = "/cw_excel/inactive_bs";
	private static final String LOAN_APPLICATION_DETAILS_BY_USER_ID = "/loan_application/getListByUserIdList";
	private static final String USERNAME_BY_APPLICATON_ID = "/loan_application/getUserNameByApplicationId";
	private static final String USER_ID_BY_APPLICATON_ID = "/loan_application/getUserIdByApplicationId";
	private static final String PRIMARY_VIEW_BY_APPLICATON_ID = "/primaryViewByApplicationId";
	private static final String FINAL_VIEW_BY_APPLICATON_ID = "/finalViewByApplicationId";
	private static final String LOAN_PRODUCT_DETAILS_BY_USER_ID = "/product_master/getListByUserIdList";
	private static final String USERNAME_BY_PRODUCT_ID = "/product_master/getUserNameByProductId";
	private static final String USER_ID_BY_PRODUCT_ID = "/product_master/getUserIdByProductId";
	private static final String REGISTERD_USERS_DETAILS = "/loan_application/getUsersRegisteredLoanDetails";
	private static final String GET_LOAN_DETAILS_ADMIN_PANEL = "/loan_application/getLoanDetailsForAdminPanel";
	private static final String GET_UBI_REPORT_1 = "/loan_application/getUbiReport1ForAdminPanel";
	private static final String GET_UBI_REPORT_2 = "/loan_application/getUbiReport2ForAdminPanel";
	private static final String GET_UBI_REPORT_3 = "/loan_application/getUbiReport3ForAdminPanel";
	private static final String GET_UBI_REPORT_4 = "/loan_application/getUbiReport4ForAdminPanel";
	private static final String GET_UBI_REPORT_5 = "/loan_application/getUbiReport5ForAdminPanel";
	private static final String GET_FILLED_LOAN_DETAILS_ADMIN_PANEL = "/loan_application/getFilledLoanDetailsForAdminPanel";
	private static final String GET_CHATLIST_BY_FP_MAPPING = "/loan_application/getChatListByFpMappingId";
	private static final String GET_CHATLIST_BY_APPLICATION_ID = "/product_master/getChatListByApplicationId";
	private static final String CREATE_LOG = "/createLog";
	private static final String SAVE_ACHIEVEMENT_DETAILS = "/achievment_details/save";
	private static final String SAVE_ASSOCIATED_CONCERN_DETAIL = "/associated_concern_details/save";
	private static final String CORPORATE_APPLICATION_DETAILS_SAVE = "/fs_profile/save";
	private static final String CORPORATE_APPLICATION_ITR_MAPPING_SAVE = "/fs_profile/saveITRMapping";
	private static final String CORPORATE_APPLICATION_DETAILS_GET = "/fs_profile/getApplicationClientForEligibility";
	private static final String CORPORATE_APPLICATION_DETAILS_GET_NEW= "/fs_profile/getApplicationClient";
	private static final String CREDIT_RATING_ORGANIZATION_DETAILS = "/credit_rating_organization_details/save";
	private static final String EXISTING_PRODUCT_DETAILS = "/existing_product_details/save";
	private static final String MEANS_OF_FINANCE = "/means_of_finance/save";
	private static final String FINANCIAL_ARRANGEMENT_DETAILS = "/financial_arrangement_details/save";
	private static final String FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI = "/financial_arrangement_details/get_total_emi_sanction_amount";
	private static final String FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_UNIFORM = "/financial_arrangement_details/get_total_emi_sanction_amount_uniform";
	private static final String FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_FROM_DIRECTOR_ID = "/financial_arrangement_details/getTotalEmiFromDirectorId";
	private static final String FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_OF_ALL_DIRS = "/financial_arrangement_details/getTotalEmiFromForAllDir";
	private static final String FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_OF_SOFT_PING = "/financial_arrangement_details/getTotalEmiForSoftPing";
	private static final String FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_OF_SOFT_PING_FOR_CO_APP = "/financial_arrangement_details/getTotalEmiForSoftPingForCoApplicant";

	private static final String FUTURE_FINANCIAL_ESTIMATE_DETAILS = "/future_financial_estimate_details/save";
	private static final String GUARANTORS_CORPORATE_DETAILS = "/guarantors_corporate_details/save";
	private static final String MONTHLY_TURNOVER_DETAILS = "/monthly_turnover_details/save";
	private static final String OWNERSHIP_DETAILS = "/ownership_details/save";
	private static final String PAST_FINANCIAL_ESTIMATE_DETAILS = "/past_financial_estimate_details/save";
	private static final String PROMOTOR_BACKGROUND_DETAILS = "/promotor_background_details/save";
	private static final String PROPOSED_PRODUCT_DETAILS = "/proposed_product_details/save";
	private static final String SECURITY_CORPORATE_DETAILS = "/security_corporate_details/save";
	private static final String SAVE_TERM_LOAN_FINAL = "/term_loan/final/save";
	private static final String SAVE_TERM_LOAN_PRIMARY = "/term_loan/primary/save";
	private static final String SAVE_TERM_LOAN_PRIMARY_GET = "/term_loan/get_client";
	private static final String TOTAL_COST_OF_PROJECT = "/total_cost_of_project/save";
	private static final String WORKING_CAPITAL_PRIMARY = "/working_capital/primary/save";
	private static final String WORKING_CAPITAL_PRIMARY_GET = "/working_capital/get_client";
	private static final String WORKING_CAPITAL_FINAL = "/working_capital/final/save";
	private static final String UPDATE_LOAN_APPLICATION = "/loan_application/updateLoanApplication";
	private static final String BASIC_DETAIL_URL = "/fs_retail_profile/profile/get_basic_details";
	private static final String BASIC_DETAIL_URL_RETAIL = "/fs_retail_profile/profile/get_basic_details_retail";
	private static final String CO_APPLICANT_BASIC_DETAIL_URL_FOR_CIBIL = "/co_applicant/get_basic_details";
	private static final String CO_APPLICANT_BASIC_DETAIL_URL = "/co_applicant/profile/get_for_client";
	private static final String LOAN_BASIC_DETAILS = "/loan_application/getLoanBasicDetails";
	private static final String PRIMARY_INFORMATION = "/corporate_primary/primary/get";
	private static final String STRING_TO_BINARY_ARRAY = "/convertToByteArray";
	private static final String SAVE_SWITCH_EXISTING = "/corporate_primary/primary/save/switchExisting";

	private static final String MOBILE_LOANLIST = "/mobile/loanList";
	private static final String MOBILE_GET_APPLICANT = "/mobile/getApplicantDetails";
	private static final String MOBILE_SAVE_APPLICANT = "/mobile/saveApplicantDetails";
	private static final String MOBILE_LOCK_PRIMARY = "/mobile/lockPrimary";
	private static final String MOBILE_GET_COAPPLICANT = "/mobile/getCoApplicantDetails";
	private static final String MOBILE_SAVE_COAPPLICANT = "/mobile/saveCoApplicantDetails";
	private static final String MOBILE_GET_GUARANTOR = "/mobile/getGuarantorDetails";
	private static final String MOBILE_SAVE_GUARANTOR = "/mobile/saveGuarantorDetails";
	private static final String MOBILE_LOAN_ELIGIBILITY_HL_CALC_MINMAX = "/loan_eligibility/hl/calc_min_max";
	private static final String MOBILE_LOAN_ELIGIBILITY_HL_GET_ELIGIBLE_TENURE = "/loan_eligibility/hl/get_eligible_tenure";
	private static final String MOBILE_LOAN_ELIGIBILITY_HL_CALC_LOAN_AMOUNT = "/loan_eligibility/hl/calc_home_loan_amount";
	private static final String MOBILE_LOAN_ELIGIBILITY_PL_GET_ELIGIBLE_TENURE = "/loan_eligibility/pl/get_eligible_tenure";
	private static final String MOBILE_LOAN_ELIGIBILITY_PL_CALC_MINMAX = "/loan_eligibility/pl/calc_min_max";
	private static final String MOBILE_LOAN_ELIGIBILITY_LAP_GET_ELIGIBLE_TENURE = "/loan_eligibility/lap/get_eligible_tenure";
	private static final String MOBILE_LOAN_ELIGIBILITY_LAP_CALC_MINMAX = "/loan_eligibility/lap/calc_min_max";
	private static final String MOBILE_LOAN_ELIGIBILITY_LAP_CALC_LAP_AMOUNT = "/loan_eligibility/lap/calc_lap_amount";
	private static final String MOBILE_GET_FP_PRODUCT_LIST = "/mobile/getProductList";
	private static final String MOBILE_SAVE_LOANAPPLICATION = "/mobile/saveLoanApplicationDetails";
	private static final String MOBILE_GET_FP_MATCHES_LIST = "/mobile/fundproviderProposal";
	private static final String MOBILE_GET_FS_MATCHES_LIST = "/mobile/fundSeekerProposal";
	private static final String MOBILE_CHANGE_STATUS = "/mobile/changeStatus";

	private static final String EXISTING_LOAN_DETAIL_CIBIL = "/existing_loan_details/save_from_cibil";
	private static final String CREDIT_CARD_DETAIL_CIBIL = "/credit_cards_detail/save_from_cibil";
	private static final String FINANCIAL_ARRANGEMENT_DETAILS_CIBIL = "/financial_arrangement_details/save_from_cibil";
	private static final String FINANCIAL_ARRANGEMENT_DETAILS_CIBIL_ALL = "/financial_arrangement_details/save_from_cibil_all";
	private static final String MFI_FINANCIAL_ARRANGEMENT_DETAILS = "/mfi/saveFinancialDetails";
	private static final String CREDIT_RATING_DETAILS_CIBIL = "/credit_rating_organization_details/save_from_cibil";

	private static final String CREATE_LOAN_FROM_CAMPAIGN = "/loan_application/create_loan_from_campaign";
	private static final String INACTIVE_APPLICATION_BY_APPLICATION_ID = "/loan_application/inactive_application";

	private static final String EKYC_AUTHENTICATION = "/loan_application/getDetailsForEkycAuthentication";
	
	private static final String FS_DETAILS_FOR_PDF = "/fsDetailsForPdf/getDataMap";
	
	private static final String GET_OTHER_DOC_REPORT = "/corporate_upload/uploadDocumentList/get";

	private static final String GET_FULL_PRIMARY_HL = "/home/primary/get_primary_info";
	private static final String GET_FULL_PRIMARY_PL = "/personal/primary/get_primary_info";
	private static final String GET_FULL_PRIMARY_LAP = "/lap/primary/get_primary_info";
	private static final String GET_FULL_PROFILE = "/fs_retail_profile/profile/get_profile";
	private static final String GET_LOAN_PURPOSE_MODEL = "/model/hl/get_client/";
	private static final String SAVE_ITR_RETAIL_APPLICANT_DETAILS = "/fs_retail_profile/profile/saveITRRes";
	private static final String SAVE_ITR_RETAIL_CO_APPLICANT_DETAILS= "/co_applicant/profile/saveITRRes";
	
	private static final String SAVE_MUDRA_ITR_APPLICANT_DETAILS = "/fs_profile/saveMudraITRRes";
	private static final String IS_TERM_LOAN_LESS_THAN_LIMIT = "/loan_application/isTermLoanLessThanLimit";
	
	private static final String SET_ELIGIBILITY_AMOUNT = "/loan_application/set_eligibility_amount";
	
	private static final String SAVE_DIRECTOR_BACKGROUND_DETAILS = "/director_background_details/save";
	private static final String GET_DIRECTOR_BACKGROUND_DETAILS = "/director_background_details/getList_client";
	private static final String GET_DIRECTOR_BACKGROUND_DETAILS_FOR_NTB = "/director_background_details/getDirectorBasicDetailsListForNTB";
	private static final String GET_DIRECTOR_BACKGROUND_DETAIL = "/director_background_details/get";
	private static final String UPDATE_DIRECTOR_BACKGROUND_API_FLAG = "/director_background_details/update_api_flag";
	private static final String GET_PINCODE_DATA = "/pincodeData/";

	private static final String GET_LOAN_DETAILS = "/loan_application/get_client";
	private static final String GET_BASIC_INFORMATION = "/loan_application/getBasicInformation";
	
	private static final String GET_FINANCIAL_TO_BE_FILLED = "/ddr/get";
	
	private static final String GET_FINANCIAL_AUTO_FILLED_MASTER = "/ddr/getAutoFilledDetails";

	private static final String CALCULATE_SCORING = "/score/calculate_score";

	private static final String CALCULATE_SCORING_EXISTING_LIST = "/score/calculate_score/corporate_existing_list";
	private static final String CALCULATE_SCORING_MFI_LIST = "/score/calculate_score/mfi_list";
	private static final String CALCULATE_SCORING_MFI_CO_APPLICANT_LIST = "/score/calculate_score/mfi_list_coapplicant";
	
	private static final String GET_MFI_APPLICANT_DETAILS = "/mfi/getApplicantDetails";


	private static final String CALCULATE_SCORING_RETAIL_PL_LIST = "/score/calculate_score/retail_pl_list";
	private static final String CALCULATE_SCORING_RETAIL_HL_LIST = "/score/calculate_score/retail_hl_list";
	private static final String CALCULATE_SCORING_RETAIL_HL_LIST_COAPPLICANT = "/score/calculate_score/retail_hl_list_coapplicant";
	private static final String CALCULATE_SCORING_RETAIL_AL_LIST = "/score/calculate_score/retail_al_list";
	private static final String CALCULATE_SCORING_RETAIL_AL_LIST_COAPPLICANT = "/score/calculate_score/retail_al_list_coapplicant";

	private static final String GET_CMA_DETAIL = "/loan_eligibility/getCMADetailForEligibility/";
	
	private static final String GET_CMA_DETAIL_API = "/loan_eligibility/getCMADetailForAPI/";
	
	private static final String CMA_DETAILS = "/cma/get";
	private static final String SAVE_CMA_DETAILS = "/cma/save";

	private static final String FUNDSEEKER_INPUT_REQUEST_SAVE = "/fundseeker_input_request/save";
	private static final String FUNDSEEKER_INPUT_REQUEST_GET = "/fundseeker_input_request/get";

	private static final String FUNDSEEKER_INPUT_REQUEST_SAVE_MOBILE = "/fundseeker_input_request_mobile/save_oneform";
	private static final String FUNDSEEKER_INPUT_REQUEST_GET_MOBILE = "/fundseeker_input_request_mobile/get_oneform";
	private static final String FUNDSEEKER_INPUT_REQUEST_MATCHES_MOBILE = "/fundseeker_input_request_mobile/match";

	private static final String GET_ORG_PAN_DETAILS = "/fs_profile/getOrgAndPanByAppId";
	
	private static final String GET_FPDETAILS_BY_FPPRODUCTID = "/loan_application/getFpDetailsByFpProductId";
	private static final String GET_FP_DETAILS_BY_FPPRODUCT_MAPPINGID = "/loan_application/getFpDetailsByFpProductMappindId";
	private static final String SAVE_PHASE_ONE = "/loan_application/save_phase1_sidbi";
	private static final String SAVE_PHASE_TWO = "/loan_application/save_phase2_sidbi";
	
    private static final String GET_CORPORATE_SCORE="/score/calculate_score/corporate/test";
    
    private static final String SIDBI_GET_DIRECTOR_DETAILS ="/fundseeker_input_request_mobile/get_director_detail";
    private static final String SIDBI_SAVE_DIRECTOR_DETAILS ="/fundseeker_input_request_mobile/save_director_detail";
    private static final String UPDATE_PRODUCT_DETAILS ="/loan_application/updateProductDetails";
    
    private static final String GET_SCORING_EXCEL="/score/getScoreExcel";
    private static final String GET_DIRECTORLIST_BY_APPLICATION_ID = "/director_background_details/getDirectorList";
    
    private static final String GET_DATA_FOR_CGTMSE="/common/getDataForCGTMSE";
    
    
    //USERLOANAMOUNTMAPPING TABLE SERVICE
    private static final String CHECK_AMOUNT_BY_USERID_AND_PRODUCTID = "/user_amount_mapping/check_amount_by_user_and_product";
    private static final String GET_BY_USERID_AND_PRODUCTID = "/user_amount_mapping/get_by_user_and_product";
    
    private static final String GET_DATA_FOR_HUNTER="/common/getDataForHunter";
    private static final String GET_DATA_FOR_HUNTER_NTB="/common/getDataForHunterForNTB";
    
    //For Gateway
    private static final String GET_CMA_BY_APPLICATIONID_PRODUCTDOCUMENTMAPPINGID = "/corporate_upload/get_CMA_by_applicationId_productDocumentMappingId";
    private static final String UPDATE_PAYMENT_STATUS = "/loan_application/update_payment_status_sidbi";
    
    private static final String SAVE_UPDATE_DIRECTOR_INCOME_DETAILS = "/corporate_director_income_details/save_income_details";
 
      
    private static final String GET_DIRECTOR_INCOME_DETAILS = "/corporate_director_income_details/get_income_details";
    private static final String GET_DIRECTOR_INCOME_LATEST_YEAR_DETAILS = "/corporate_director_income_details/get_income_details_latest_year";
    private static final String GET_RETAIL_APPLICANT_INCOME_DETAILS = "/retail_applicant_income/get";
    private static final String GET_RETAIL_APPLICANT_INCOME_DETAILS_FOR_CO_APPLICANT = "/retail_applicant_income/get_for_client";
    private static final String SAVE_RETAIL_APPLICANT_INCOME_DETAILS = "/retail_applicant_income/saveAll";
    
    
    private static final String GET_TOKEN ="/loan_application/getToken";
    private static final String SET_TOKEN_AS_EXPIRED ="/loan_application/setTokenAsExpired";
    private static final String SAVE_LOAN_WC_RENEWAL_TYPE ="/loan_application/saveLoanWCRenewalType";
    private static final String GET_LOAN_WC_RENEWAL_TYPE ="/loan_application/getLoanWCRenewalType";
    private static final String SAVE_INELIGIBALE_PROPOSAL ="/save/ineligible/proposal";
    private static final String IS_EXIST_OFFLINE_PROPOSAL_BY_APPID ="/checkIsExistOfflineProposalByApplicationId";
    private static final String GET_COMMON_PROPERTIES ="/loan_application/getCommonPropValue";
    private static final String AND_FOR_APPLICATION_ID = " and For Application Id====>";
    private static final String GET_PRIMARY_DETAILS_CAM = "/cam/getPrimaryDataInByteArray";
    private static final String GET_PL_PRIMARY_DETAILS_CAM = "/cam/pl/getPlPrimaryDataInByteArray";
    private static final String GET_HL_PRIMARY_DETAILS_CAM = "/cam/getHlPrimaryDataInByteArray";
    private static final String GET_AL_PRIMARY_DETAILS_CAM = "/cam/getALPrimaryDataInByteArray";
    private static final String GET_APPLICATION_FORM_REPORT = "/cam/getApplicationForm";

    private static final String REQ_AUTH = "req_auth";
    private static final String GET_LOAN_APPLICATION_BY_PROPOSAL_ID="/loan_application/getLoanApplicationById";
    private static final String GET_CORPORATE_BY_PROPOSAL_ID="/final_info/getByProposalId";
    private static final String GET_INPRINCIPLE_LIST_FOR_MULTIPLEBANNK="/proposal/inprincipleDataMulipleBank";
    private static final String GET_DAY_DIFFRENCE_FOR_MULTIPLEBANNK="/proposal/getDayDiffrenceForMultipleBank";
    
    private static final String SAVE_LOGS_OF_PAYMENT_GATEWAY = "/loan_application/savePaymentGatewayAuditLogs";
    
    private static final String GET_CONCESSION_DETAILS = "/score/getConcessionDetails";
    private static final String SEND_MAIL_INELIGIBLE_FOR_SIDBI = "/sendInEligibleForSidbi";
    
    private static final String GET_APPLICATION_CAMPAIGN_CODE = "/loan_application/getApplicationCampCode";
    private static final String GET_APPLICATION_CAMPAIGN_DETAILS = "/loan_application/getApplicationCampDetails";
    private static final String GET_FINANCIAL_DATA = "/cma/getFinancialDetailsForBankIntegration";
    
    private static final String LOAN_AMOUNT_FOR_INELIGIBLE = "/get/loan_amount_for_ineligible";

	private static final String SAVE_BANK_RELATION = "/sbi_pl/primary/saveBankRelation/";
	private static final String GET_MERGED_PROFILE = "/sbi_pl/profile/getMergeProfile";
	private static final String OEM_LIST = "/auto/getOEMList";
	private static final String GET_BANK_RELATION_SALARY = "/sbi_pl/primary/getBankRelationsSalaryDetail";
	private static final String UPDATE_BANK_RELATION = "/sbi_pl/primary/updateBankRelation/";
	private static final String BS_CAM_VERSION = "/cam/getCamVersionForBSStandalone/";
	private static final Logger logger = LoggerFactory.getLogger(LoansClient.class);
	
	private String loansBaseUrl;

	private RestTemplate restTemplate = null;

	public LoansClient(String loansBaseUrl) {
		this.loansBaseUrl = loansBaseUrl;
		restTemplate = new RestTemplate();
	}

	public ExcelResponse readCMA(ExcelRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(READ_CMA);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(request), ExcelResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in readCMA : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public PrimaryCorporateRequest getPrimaryCorporateDetails(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(PRIMARY_INFORMATION);
		logger.info("URL in getPrimaryCorporateDetails : {}",url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(applicationId, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, PrimaryCorporateRequest.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getPrimaryCorporateDetails :{} ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public ExcelResponse readDPR(ExcelRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(READ_DPR);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(request), ExcelResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in readDPR : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public ExcelResponse inactiveCMA(ExcelRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(INACTIVE_CMA);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(request), ExcelResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in inactiveCMA : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public ExcelResponse inactiveDPR(ExcelRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(INACTIVE_DPR);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(request), ExcelResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in inactiveDPR : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public ExcelResponse readBS(ExcelRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(READ_BS);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(request), ExcelResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in readBS : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public ExcelResponse inactiveBS(ExcelRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(INACTIVE_BS);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(request), ExcelResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in inactiveBS : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	private HttpEntity<ExcelRequest> setHttpHeader(ExcelRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(REQ_AUTH, "true");
		return new HttpEntity<>(request, headers);
	}

	public LoansResponse getLoanDetailsByUserIdList(List<Long> request) throws LoansException {
		String url = loansBaseUrl.concat(LOAN_APPLICATION_DETAILS_BY_USER_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<List<Long>> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getLoanDetailsByUserIdList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getUserNameByApplicationId(Long request) throws LoansException {
		String url = loansBaseUrl.concat(USERNAME_BY_APPLICATON_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getUserNameByApplicationId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getPrimaryViewByApplicationId(Long request) throws LoansException {
		String url = loansBaseUrl.concat(PRIMARY_VIEW_BY_APPLICATON_ID).concat("/" + request);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getPrimaryViewByApplicationId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getFinalViewByApplicationId(Long request) throws LoansException {
		String url = loansBaseUrl.concat(FINAL_VIEW_BY_APPLICATON_ID).concat("/" + request);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getFinalViewByApplicationId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getDetailsForEkycAuthentication(EkycRequest request) throws LoansException {
		String url = loansBaseUrl.concat(EKYC_AUTHENTICATION);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EkycRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDetailsForEkycAuthentication : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public String getUserIdByApplicationId(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(USER_ID_BY_APPLICATON_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(applicationId, headers);
			LoansResponse response = restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
			if (response != null) {
				return response.getData().toString();
			} else {
				throw new LoansException("User Id not found");
			}

		} catch (Exception e) {
			logger.error("Exception in getUserIdByApplicationId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getProductDetailsByUserIdList(List<Long> request) throws LoansException {
		String url = loansBaseUrl.concat(LOAN_PRODUCT_DETAILS_BY_USER_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getProductDetailsByUserIdList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getUserNameByProductMappingId(Long request) throws LoansException {
		String url = loansBaseUrl.concat(USERNAME_BY_PRODUCT_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getUserNameByProductMappingId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getRegisteredUsersDetails(MobileLoanRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(REGISTERD_USERS_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getRegisteredUsersDetails : ",e);
			throw new LoansException("Loans service is not available while call getRegisteredUsersDetails");
		}
	}

	public LoansResponse getLoanDetailsForAdminPanel(MobileLoanRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(GET_LOAN_DETAILS_ADMIN_PANEL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getLoanDetailsForAdminPanel : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public LoansResponse getUbiReport1ForAdminPanel(MobileLoanRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(GET_UBI_REPORT_1);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getUbiReport1ForAdminPanel : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public LoansResponse getUbiReport2ForAdminPanel(MobileLoanRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(GET_UBI_REPORT_2);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getUbiReport2ForAdminPanel : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public LoansResponse getUbiReport3ForAdminPanel(MobileLoanRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(GET_UBI_REPORT_3);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getUbiReport3ForAdminPanel : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public LoansResponse getUbiReport4ForAdminPanel(MobileLoanRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(GET_UBI_REPORT_4);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getUbiReport4ForAdminPanel : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public LoansResponse getUbiReport5ForAdminPanel(MobileLoanRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(GET_UBI_REPORT_5);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getUbiReport5ForAdminPanel : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getFilledLoanDetailsForAdminPanel(MobileLoanRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(GET_FILLED_LOAN_DETAILS_ADMIN_PANEL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getFilledLoanDetailsForAdminPanel : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public String getUserIdByProductId(Long productId) throws LoansException {
		String url = loansBaseUrl.concat(USER_ID_BY_PRODUCT_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(productId, headers);
			LoansResponse response = restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
			if (response != null) {
				return response.getData().toString();
			} else {
				throw new LoansException("Loan Response Found Null While Getting UserId by Product Id");
			}
		} catch (Exception e) {
			logger.error("Exception in getUserIdByProductId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getChatListByApplicationId(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_CHATLIST_BY_APPLICATION_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(applicationId, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getChatListByApplicationId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getChatListByFpMappingId(Long productMappingId) throws LoansException {
		String url = loansBaseUrl.concat(GET_CHATLIST_BY_FP_MAPPING);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(productMappingId, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getChatListByFpMappingId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse createLog(LogDetailsModel logDetailsModel) throws LoansException {
		String url = loansBaseUrl.concat(CREATE_LOG);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LogDetailsModel> entity = new HttpEntity<>(logDetailsModel, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in createLog : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getLoanListForMobile(MobileLoanRequest mobileUserRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOANLIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(mobileUserRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getLoanListForMobile : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse lockPrimaryDetails(MobileLoanRequest mobileUserRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOCK_PRIMARY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(mobileUserRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in lockPrimaryDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getApplicantDetails(MobileLoanRequest mobileUserRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_GET_APPLICANT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(mobileUserRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getApplicantDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveApplicantDetails(MRetailApplicantResponse mRetailApplicantResponse) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_SAVE_APPLICANT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MRetailApplicantResponse> entity = new HttpEntity<>(mRetailApplicantResponse, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in saveApplicantDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveAchievementdetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(SAVE_ACHIEVEMENT_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveAchievementdetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveAssociatedConcernDetail(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(SAVE_ASSOCIATED_CONCERN_DETAIL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveAssociatedConcernDetail : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveCorporateApplicant(CorporateApplicantRequest applicantRequest) throws ExcelException {
		String url = loansBaseUrl.concat(CORPORATE_APPLICATION_DETAILS_SAVE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<CorporateApplicantRequest> entity = new HttpEntity<>(applicantRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveCorporateApplicant : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveITRMappingCorporateApplicant(CorporateApplicantRequest applicantRequest) throws ExcelException {
		String url = loansBaseUrl.concat(CORPORATE_APPLICATION_ITR_MAPPING_SAVE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<CorporateApplicantRequest> entity = new HttpEntity<>(applicantRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveITRMappingCorporateApplicant : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getCorporateApplicant(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(CORPORATE_APPLICATION_DETAILS_GET).concat("/" + applicationId);
		logger.info("url for Getting Corporate Details From Client=================>{} = {} = {}" , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCorporateApplicant : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getCorporateApplicantNew(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(CORPORATE_APPLICATION_DETAILS_GET_NEW).concat("/" + applicationId);
		logger.info("url for Getting Corporate Details From Client=================>{} = {} = {}",url,AND_FOR_APPLICATION_ID,applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCorporateApplicantNew : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	public LoansResponse saveCreditRatingOrganizationDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(CREDIT_RATING_ORGANIZATION_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveCreditRatingOrganizationDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveExistingProductDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(EXISTING_PRODUCT_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveExistingProductDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveMeansOfFinance(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(MEANS_OF_FINANCE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveMeansOfFinance : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveFinancialArrangementDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(FINANCIAL_ARRANGEMENT_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveFinancialArrangementDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public FinancialArrangementsDetailRequest getTotalEMIAndSanctionAmount(Long applicationId) throws LoansException{
		String url = loansBaseUrl.concat(FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI).concat("/" + applicationId);
		logger.info("url for Getting TotalEMI From Client=================>{} = {} = {}" , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, FinancialArrangementsDetailRequest.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getTotalEMIAndSanctionAmount : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public FinancialArrangementsDetailRequest getTotalEMIAndSanctionAmountUniform(Long applicationId) throws LoansException{
		String url = loansBaseUrl.concat(FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_UNIFORM).concat("/" + applicationId);
		logger.info("url for Getting TotalEMI From Client FOr Uniform Product=================>{} = {} = {} " , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, FinancialArrangementsDetailRequest.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getTotalEMIAndSanctionAmount : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	//Dhaval
	public LoansResponse getTotalEMIFromDirectorId(NTBRequest request) throws LoansException{
		String url = loansBaseUrl.concat(FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_FROM_DIRECTOR_ID);
		logger.info("url for Getting TotalEMIDirector From Client=================>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<NTBRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getTotalEMIFromDirectorId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	//Akshay
	public LoansResponse getTotalEMIOfAllDir(Long applicationId) throws LoansException{
		String url = loansBaseUrl.concat(FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_OF_ALL_DIRS);
		logger.info("url for Getting getTotalEMIOfAllDir From Client=================>{}", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(applicationId, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getTotalEMIOfAllDir : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getTotalEMISoftPing(Long applicationId) throws LoansException{
		String url = loansBaseUrl.concat(FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_OF_SOFT_PING);
		logger.info("url for Getting getTotalEMISoftPing From Client=================>{}", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(applicationId, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getTotalEMIOfAllDir : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getTotalEMISoftPingForCoApplicant(Long applicationId,Long coApplicant) throws LoansException{
		String url = loansBaseUrl.concat(FINANCIAL_ARRANGEMENT_DETAILS_TOTAL_EMI_OF_SOFT_PING_FOR_CO_APP).concat("/").concat(applicationId.toString()).concat("/").concat(coApplicant.toString());
		logger.info("url for Getting getTotalEMISoftPing From Client=================>{}", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(coApplicant, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getTotalEMIOfAllDir : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}



	public LoansResponse saveFutureFinancialEstimatesDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(FUTURE_FINANCIAL_ESTIMATE_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveFutureFinancialEstimatesDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveGuarantorsCorporateDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(GUARANTORS_CORPORATE_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveGuarantorsCorporateDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveMonthlyTurnoverDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(MONTHLY_TURNOVER_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveMonthlyTurnoverDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveOwnershipDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(OWNERSHIP_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveOwnershipDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse savePastFinancialEstimateDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(PAST_FINANCIAL_ESTIMATE_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in savePastFinancialEstimateDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse savePromotorBackgroundDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(PROMOTOR_BACKGROUND_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in savePromotorBackgroundDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveProposedProductDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(PROPOSED_PRODUCT_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveProposedProductDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveSecurityCorporateDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(SECURITY_CORPORATE_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveSecurityCorporateDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveTermLoanFinal(FinalTermLoanRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(SAVE_TERM_LOAN_FINAL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<FinalTermLoanRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveTermLoanFinal : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveTermLoanPrimary(PrimaryTermLoanRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(SAVE_TERM_LOAN_PRIMARY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<PrimaryTermLoanRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveTermLoanPrimary : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveTotalCostOfProject(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(TOTAL_COST_OF_PROJECT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in : saveTotalCostOfProject ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveWorkingCapitalPrimary(PrimaryWorkingCapitalLoanRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(WORKING_CAPITAL_PRIMARY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<PrimaryWorkingCapitalLoanRequest> entity = new HttpEntity<>(
					request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveWorkingCapitalPrimary : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getWorkingCapitalPrimary(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(WORKING_CAPITAL_PRIMARY_GET).concat("/" + applicationId);
		logger.info("url for Getting Working Capital Primary From Client=================>{} = {} = {} " , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getWorkingCapitalPrimary : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveWorkingCapitalFinal(FinalWorkingCapitalLoanRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(WORKING_CAPITAL_FINAL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FinalWorkingCapitalLoanRequest> entity = new HttpEntity<>(request,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveWorkingCapitalFinal : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveLoanApplicationMaster(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(UPDATE_LOAN_APPLICATION);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveLoanApplicationMaster : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse updateLoanApplicationMaster(LoanApplicationRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(UPDATE_LOAN_APPLICATION);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in updateLoanApplicationMaster : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse getLoanBasicDetails(LoanApplicationRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(LOAN_BASIC_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getLoanBasicDetails : ",e);
			throw new ExcelException(
					"Loans service is not available while calling loan basic details for sending mails");
		}
	}

	public LoansResponse getBasicDetail(Long userId, Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(BASIC_DETAIL_URL).concat("/" + applicationId + "/" + userId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getBasicDetail : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getBasicDetailRetail(Long userId, Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(BASIC_DETAIL_URL_RETAIL).concat("/" + applicationId + "/" + userId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getBasicDetailRetail : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getCoApplicantBasicDetail(Long userId, Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(CO_APPLICANT_BASIC_DETAIL_URL_FOR_CIBIL).concat("/" + applicationId + "/" + userId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getBasicDetail : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public CoApplicantRequest getCoApplicantBasicDetailForCalculation(Long applicationId,Long coApplicantId) throws ExcelException {
		String url = loansBaseUrl.concat(CO_APPLICANT_BASIC_DETAIL_URL).concat("/" + applicationId + "/" + coApplicantId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, CoApplicantRequest.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCoApplicantBasicDetailForCalculation : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse getCoApplicantDetails(MobileLoanRequest mobileUserRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_GET_COAPPLICANT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(mobileUserRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getCoApplicantDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveCoApplicantDetails(MRetailCoAppGuarResponse coAppGuarResponse) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_SAVE_COAPPLICANT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MRetailCoAppGuarResponse> entity = new HttpEntity<>(coAppGuarResponse,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in saveCoApplicantDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getGuarantorDetails(MobileLoanRequest mobileUserRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_GET_GUARANTOR);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(mobileUserRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getGuarantorDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveGuarantorDetails(MRetailCoAppGuarResponse coAppGuarResponse) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_SAVE_GUARANTOR);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MRetailCoAppGuarResponse> entity = new HttpEntity<>(coAppGuarResponse,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in saveGuarantorDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse mobileHlCalcMinMax(HomeLoanEligibilityRequest homeLoanRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_HL_CALC_MINMAX);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HomeLoanEligibilityRequest> entity = new HttpEntity<>(homeLoanRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in mobileHlCalcMinMax : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse mobileHlGetEligibleTenure(HomeLoanEligibilityRequest homeLoanRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_HL_GET_ELIGIBLE_TENURE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<HomeLoanEligibilityRequest> entity = new HttpEntity<>(homeLoanRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in mobileHlGetEligibleTenure : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse mobileHlCalcHomeLoanAmount(HomeLoanEligibilityRequest homeLoanRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_HL_CALC_LOAN_AMOUNT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HomeLoanEligibilityRequest> entity = new HttpEntity<>(homeLoanRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in mobileHlCalcHomeLoanAmount : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse mobilePlGetEligibleTenure(PersonalLoanEligibilityRequest eligibilityRequest)
			throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_PL_GET_ELIGIBLE_TENURE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<PersonalLoanEligibilityRequest> entity = new HttpEntity<>(
					eligibilityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in mobilePlGetEligibleTenure : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse mobilePlCalcMinMax(PersonalLoanEligibilityRequest eligibilityRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_PL_CALC_MINMAX);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<PersonalLoanEligibilityRequest> entity = new HttpEntity<>(
					eligibilityRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in mobilePlCalcMinMax : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse mobileLapGetEligibleTenure(LAPEligibilityRequest eligibilityRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_LAP_GET_ELIGIBLE_TENURE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LAPEligibilityRequest> entity = new HttpEntity<>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in mobileLapGetEligibleTenure : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse mobileLapCalcMinMax(LAPEligibilityRequest eligibilityRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_LAP_CALC_MINMAX);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LAPEligibilityRequest> entity = new HttpEntity<>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in mobileLapCalcMinMax : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse mobileLapCalcLapAmount(LAPEligibilityRequest eligibilityRequest) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_LOAN_ELIGIBILITY_LAP_CALC_LAP_AMOUNT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LAPEligibilityRequest> entity = new HttpEntity<>(eligibilityRequest,
					headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in mobileLapCalcLapAmount : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveExistingLoanFromCibil(List<ExistingLoanDetailRequest> detailRequests, Long userId,
			Long clientId, Long applicationId, Integer applicantType) throws ExcelException {
		String url = loansBaseUrl.concat(EXISTING_LOAN_DETAIL_CIBIL)
				.concat("/" + applicationId + "/" + userId + "/" + clientId + "/" + applicantType);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ExistingLoanDetailRequest>> entity = new HttpEntity<>(
					detailRequests, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveExistingLoanFromCibil : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse saveCreditCardFromCibil(List<CreditCardsDetailRequest> detailRequests, Long userId,
			Long clientId, Long applicationId, Integer applicantType) throws ExcelException {
		String url = loansBaseUrl.concat(CREDIT_CARD_DETAIL_CIBIL)
				.concat("/" + applicationId + "/" + userId + "/" + clientId + "/" + applicantType);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<CreditCardsDetailRequest>> entity = new HttpEntity<>(
					detailRequests, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveCreditCardFromCibil : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveFinancialArrangementDetailFromCibil(List<FinancialArrangementsDetailRequest> detailRequests, Long userId,
			Long clientId, Long applicationId,Long directorId) throws ExcelException {
		String url = loansBaseUrl.concat(FINANCIAL_ARRANGEMENT_DETAILS_CIBIL)
				.concat("/" + applicationId + "/" + userId + "/" + clientId + "/" + directorId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<FinancialArrangementsDetailRequest>> entity = new HttpEntity<>(
					detailRequests, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveFinancialArrangementDetailFromCibil : ",e);
			throw new ExcelException(url + " " + e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveAllExistingLoansByApplicationId(List<FinancialArrangementsDetailRequest> detailRequests, Long userId, Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(FINANCIAL_ARRANGEMENT_DETAILS_CIBIL_ALL).concat("/" + applicationId + "/" + userId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<FinancialArrangementsDetailRequest>> entity = new HttpEntity<>(
					detailRequests, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveAllExistingLoansByApplicationId() : ", e);
			throw new ExcelException(url + " " + e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveMfiFinancialArrangementDetail(List<MFIFinancialArrangementRequest> detailRequests, Long applicationId, Long userId, Long applicantId, Integer provider) throws ExcelException {
		String url = loansBaseUrl.concat(MFI_FINANCIAL_ARRANGEMENT_DETAILS).concat("/" + applicationId + "/" + userId + "/" + applicantId + "/" + provider);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<MFIFinancialArrangementRequest>> entity = new HttpEntity<>(detailRequests, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveMfiFinancialArrangementDetail : ",e);
			throw new ExcelException(url + " " + e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveCreditRatingDetailFromCibil(List<CreditRatingOrganizationDetailRequest> detailRequests, Long userId,
			Long clientId, Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(CREDIT_RATING_DETAILS_CIBIL)
				.concat("/" + applicationId + "/" + userId + "/" + clientId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<CreditRatingOrganizationDetailRequest>> entity = new HttpEntity<>(
					detailRequests, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveCreditRatingDetailFromCibil : ",e);
			throw new LoansException(url + " " + e.getCause().getMessage());
		}
	}

	public LoansResponse saveLoanApplicantDetails(MobileFrameRequest request) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_SAVE_LOANAPPLICATION);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileFrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in saveLoanApplicantDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getProductList(MobileLoanRequest request) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_GET_FP_PRODUCT_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getProductList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse fundproviderProposal(MobileFPMatchesRequest request) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_GET_FP_MATCHES_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileFPMatchesRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in fundproviderProposal : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse fundSeekerProposal(MobileFPMatchesRequest request) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_GET_FS_MATCHES_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileFPMatchesRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in fundSeekerProposal : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse changeStatus(MobileFPMatchesRequest request) throws LoansException {
		String url = loansBaseUrl.concat(MOBILE_CHANGE_STATUS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<MobileFPMatchesRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in changeStatus : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getCreateCampaignLoan(Long loanTypeId,Long userId, Boolean isActive,Integer businessTypeId,Integer connectFlowTypeId, String...code) throws ExcelException {
		logger.info("BEFORE CALL CREATE LOAN APPLICATION -----USERID---->{}-----businessType---->{}" ,userId, businessTypeId);
		String url = loansBaseUrl.concat(CREATE_LOAN_FROM_CAMPAIGN) + "?clientId=" + userId + "&isActive=" + isActive + "&businessType=" + businessTypeId + (connectFlowTypeId !=null ? "&connectFlowTypeId=" + connectFlowTypeId : "") + (loanTypeId !=null ? "&loanTypeId=" + loanTypeId : "") ;
		logger.info("CREATE LOAN APPLICATION URL IN LOAN CLIENT ------------------------>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<String>> entity = new HttpEntity<>(Arrays.asList(code),headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCreateCampaignLoan : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse convertToByteArrayFile(Long applicantId) throws LoansException {
		String url = loansBaseUrl.concat(STRING_TO_BINARY_ARRAY).concat("/" + applicantId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(applicantId, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in convertToByteArrayFile : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	
	public LoansResponse getDataMap(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(FS_DETAILS_FOR_PDF) + "/" + applicationId;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDataMap : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	
	public LoansResponse getOtherDocReport(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_OTHER_DOC_REPORT) + "/" + applicationId;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getOtherDocReport : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
		}
	public LoansResponse getFullPrimaryDetails(Long applicationId,Long userId,Integer productId) throws LoansException {
		String url = null;
		LoanType type = CommonUtils.LoanType.getType(productId);
		switch (type) {
		case HOME_LOAN:
			url = loansBaseUrl.concat(GET_FULL_PRIMARY_HL);			
			break;
		case PERSONAL_LOAN:
			url = loansBaseUrl.concat(GET_FULL_PRIMARY_PL);			
			break;
		case LAP_LOAN:
			url = loansBaseUrl.concat(GET_FULL_PRIMARY_LAP);			
			break;
		default:
			break;
		}

		if (url == null){
			throw new LoansException("Url must not be null.");
		}

		url = url.concat("/" + applicationId + "/" + userId);

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getFullPrimaryDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public RetailApplicantRequest getFullProfileDetail(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_FULL_PROFILE).concat("/" + applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, RetailApplicantRequest.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getFullProfileDetail : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse isTermLoanLessThanLimit(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(IS_TERM_LOAN_LESS_THAN_LIMIT) + "/" + applicationId;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in isTermLoanLessThanLimit : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
		}
	
	public Integer setEligibilityAmount(LoanApplicationRequest applicationRequest) throws LoansException {
		String url = loansBaseUrl.concat(SET_ELIGIBILITY_AMOUNT);
		logger.info("Eligibility update client URL==>{}"  , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(applicationRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, Integer.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in setEligibilityAmount : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveDirectorBackgroundDetails(FrameRequest request) throws ExcelException {
		String url = loansBaseUrl.concat(SAVE_DIRECTOR_BACKGROUND_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveDirectorBackgroundDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse updateDirectorAPIFlag(Long directorId,Long userId,Integer apiId, Boolean apiFlag) throws ExcelException {
		String url = loansBaseUrl.concat(UPDATE_DIRECTOR_BACKGROUND_API_FLAG) + "?userId=" + userId + "&directorId=" + directorId + "&apiId=" + apiId + "&apiFlag=" + apiFlag;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null,headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in updateDirectorAPIFlag : ",e);
			if(e.getMessage().contains("404")) {
				throw new ExcelException(url + " is Not Found");
			}else if(e.getMessage().contains("400")) {
				throw new ExcelException(url + " is Not Valid Request");
			}else {
				throw new ExcelException(e.getCause().getMessage());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DirectorBackgroundDetailRequest> getDirectorBackgroundDetails(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_DIRECTOR_BACKGROUND_DETAILS).concat("/" + applicationId);
		logger.info("url for Getting DirectorBackgroundDetails From Client=================>{} = {} = {} " , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, List.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDirectorBackgroundDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getDirectorBackgroundDetailsForNTB(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_DIRECTOR_BACKGROUND_DETAILS_FOR_NTB).concat("/" + applicationId);
		logger.info("url for Getting DirectorBackgroundDetails for NTB From Client=================>{} = {} = {} " , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDirectorBackgroundDetailsForNTB : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getDirectorBackgroundDetail(Long id) throws ExcelException {
		String url = loansBaseUrl.concat(GET_DIRECTOR_BACKGROUND_DETAIL).concat("/" + id);
		logger.info("url for Getting DirectorBackgroundDetail From Client=================>{} == and For Id====>{}",url, id);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDirectorBackgroundDetail : ",e);
			if(e.getMessage().contains("404")) {
				throw new ExcelException(url + " is Not Found");
			}else if(e.getMessage().contains("400")) {
				throw new ExcelException(url + " " + e.getCause().getMessage());
			}else {
				throw new ExcelException(e.getCause().getMessage());
			}
		}
	}
	
	
	public LoanApplicationRequest getLoanMasterInfo(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_BASIC_INFORMATION).concat("/" + applicationId);
		try {
			logger.info("url====================>{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoanApplicationRequest.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getLoanMasterInfo : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getFilledDetails(Long appId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_FINANCIAL_TO_BE_FILLED).concat("/"+appId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getFilledDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getAutoFilledDetails(Long appId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_FINANCIAL_AUTO_FILLED_MASTER).concat("/"+appId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getAutoFilledDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}



	public LoansResponse calculateScoring(ScoringRequestLoans scoringRequestLoans) throws LoansException {
		String url = loansBaseUrl.concat(CALCULATE_SCORING);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringRequestLoans> entity = new HttpEntity<>(scoringRequestLoans,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in calculateScoring : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse calculateScoringCorporateExistingList(List<ScoringRequestLoans> scoringRequestLoansList) throws LoansException {
		String url = loansBaseUrl.concat(CALCULATE_SCORING_EXISTING_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequestLoans>> entity = new HttpEntity<>(scoringRequestLoansList,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in calculateScoringCorporateExistingList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse calculateScoringMFIList(List<ScoringRequestLoans> scoringRequestLoansList) throws LoansException {
		String url = loansBaseUrl.concat(CALCULATE_SCORING_MFI_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequestLoans>> entity = new HttpEntity<>(scoringRequestLoansList,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in calculateScoringCorporateExistingList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse calculateScoringMFICOApplicantList(List<ScoringRequestLoans> scoringRequestLoansList) throws LoansException {
		String url = loansBaseUrl.concat(CALCULATE_SCORING_MFI_CO_APPLICANT_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequestLoans>> entity = new HttpEntity<>(scoringRequestLoansList,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in calculateScoringCorporateExistingList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse calculateScoringRetailPLList(List<ScoringRequestLoans> scoringRequestLoansList) throws LoansException {
		String url = loansBaseUrl.concat(CALCULATE_SCORING_RETAIL_PL_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequestLoans>> entity = new HttpEntity<>(scoringRequestLoansList,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in calculateScoringRetailPLList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse calculateScoringRetailHLList(List<ScoringRequestLoans> scoringRequestLoansList) throws LoansException {
		String url = loansBaseUrl.concat(CALCULATE_SCORING_RETAIL_HL_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequestLoans>> entity = new HttpEntity<>(scoringRequestLoansList,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in calculateScoringRetailHLList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse calculateScoringRetailALList(List<ScoringRequestLoans> scoringRequestLoansList) throws LoansException {
		String url = loansBaseUrl.concat(CALCULATE_SCORING_RETAIL_AL_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequestLoans>> entity = new HttpEntity<>(scoringRequestLoansList,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in calculateScoringRetailALList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse calculateScoringRetailHLListForCoApplicant(List<ScoringRequestLoans> scoringRequestLoansList) throws LoansException {
		String url = loansBaseUrl.concat(CALCULATE_SCORING_RETAIL_HL_LIST_COAPPLICANT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequestLoans>> entity = new HttpEntity<>(scoringRequestLoansList,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in calculateScoringRetailPLList : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse calculateScoringRetailALListForCoApplicant(List<ScoringRequestLoans> scoringRequestLoansList) throws LoansException {
		String url = loansBaseUrl.concat(CALCULATE_SCORING_RETAIL_AL_LIST_COAPPLICANT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequestLoans>> entity = new HttpEntity<>(scoringRequestLoansList,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in calculateScoringRetailALListForCoApplicant : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public CMADetailResponse getCMADetils(Long appId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_CMA_DETAIL).concat("/"+appId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, CMADetailResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCMADetils : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
		
	}
	
	public CMADetailResponse getCMADetilsAPI(Long appId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_CMA_DETAIL_API).concat("/"+appId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FrameRequest> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, CMADetailResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCMADetils : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
		
	}
	
	
	public CMARequest getCMA(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(CMA_DETAILS) + "/" + applicationId;
		logger.info("Enter in Loan CLient For get CMA Details ----------------------> {}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, CMARequest.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception While Get CMA Details Using Loan CLient : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveCMA(CMARequest cmaRequest) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_CMA_DETAILS);
		logger.info("Enter in save CMA details in Loan client");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<CMARequest> entity = new HttpEntity<>(cmaRequest,headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while call save CMA details : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}


	public LoansResponse saveFundseekerInputRequest(FundSeekerInputRequestResponse request) throws ExcelException {
		String url = loansBaseUrl.concat(FUNDSEEKER_INPUT_REQUEST_SAVE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FundSeekerInputRequestResponse> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveFundseekerInputRequest : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}


	public LoansResponse getFundseekerInputRequest(FundSeekerInputRequestResponse request) throws ExcelException {
		String url = loansBaseUrl.concat(FUNDSEEKER_INPUT_REQUEST_GET);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FundSeekerInputRequestResponse> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getFundseekerInputRequest : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getOrgAndPanByAppId(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_ORG_PAN_DETAILS).concat("/" + applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getOrgAndPanByAppId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public LoansResponse inactiveApplication(Long applicationId,Long userId) throws LoansException {
		String url = loansBaseUrl.concat(INACTIVE_APPLICATION_BY_APPLICATION_ID).concat("/" + applicationId).concat("/" + userId);
		logger.info("url to Inactive Application==>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in inactiveApplication : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse savePhase1Sidbi(LoanApplicationRequest loanApplicationRequest) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_PHASE_ONE);
		logger.info("Enter in " + SAVE_PHASE_ONE + " -------- URL ---------->{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(loanApplicationRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in savePhase1Sidbi : ",e);
			throw new LoansException(SAVE_PHASE_ONE + " Loans service is not available");
		}
	}
	
	public LoansResponse savePhase2Sidbi(LoanApplicationRequest loanApplicationRequest) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_PHASE_TWO);
		logger.info("Enter in " + SAVE_PHASE_TWO + " -------- URL ---------->{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(loanApplicationRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in savePhase2Sidbi : ",e);
			throw new LoansException(SAVE_PHASE_TWO + " Loans service is not available");
		}
	}
	
	public LoansResponse getFpDetailsByFpProductId(Long fpProductId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_FPDETAILS_BY_FPPRODUCTID).concat("/"+fpProductId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(fpProductId, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getFpDetailsByFpProductId : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getFpDetailsByFpProductMappingId(Long fpProductMappingId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_FP_DETAILS_BY_FPPRODUCT_MAPPINGID).concat("/"+fpProductMappingId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(fpProductMappingId, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getFpDetailsByFpProductMappingId : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	
	public LoansResponse getCorporateScore(ScoringRequestLoans scoringRequestLoans) throws LoansException {
		String url = loansBaseUrl.concat(GET_CORPORATE_SCORE);
		logger.info("url to get Corporate Score Calculation ==>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringRequestLoans> entity = new HttpEntity<>(scoringRequestLoans, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCorporateScore : ",e);
			throw new LoansException(e);
		}
	}
		
	public MobileApiResponse getSIDBIDirectorDetails(FundSeekerInputRequestResponse fsInputReqRes) throws ExcelException {
		String url = loansBaseUrl.concat(SIDBI_GET_DIRECTOR_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FundSeekerInputRequestResponse> entity = new HttpEntity<>(fsInputReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, MobileApiResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getSIDBIDirectorDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public MobileApiResponse saveSIDBIDirectorDetails(FundSeekerInputRequestResponse fsInputReqRes) throws ExcelException {
		String url = loansBaseUrl.concat(SIDBI_SAVE_DIRECTOR_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FundSeekerInputRequestResponse> entity = new HttpEntity<>(fsInputReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, MobileApiResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveSIDBIDirectorDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse updateProductDetails(LoanApplicationRequest loanRequest) throws ExcelException {
		String url = loansBaseUrl.concat(UPDATE_PRODUCT_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in updateProductDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public MobileApiResponse saveOneFormForMobile(FundSeekerInputRequestResponse request) throws ExcelException {
		String url = loansBaseUrl.concat(FUNDSEEKER_INPUT_REQUEST_SAVE_MOBILE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FundSeekerInputRequestResponse> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, MobileApiResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveOneFormForMobile : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public MobileApiResponse getOneFormForMobile(FundSeekerInputRequestResponse fundSeekerInputRequestResponse) throws ExcelException {
		String url = loansBaseUrl.concat(FUNDSEEKER_INPUT_REQUEST_GET_MOBILE);
		logger.info("url for Getting Oneform details From Client=================>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FundSeekerInputRequestResponse> entity = new HttpEntity<>(fundSeekerInputRequestResponse, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, MobileApiResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getOneFormForMobile : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public MobileApiResponse matchesForMobile(MobileLoanRequest mobileLoanRequest) throws ExcelException {
		String url = loansBaseUrl.concat(FUNDSEEKER_INPUT_REQUEST_MATCHES_MOBILE);
		logger.info("Url for MATCHES details From Client=================>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MobileLoanRequest> entity = new HttpEntity<>(mobileLoanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, MobileApiResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in matchesForMobile : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	
	public LoansResponse getScoringExcel(MultipartFile multipartFile) throws LoansException, ExcelException {
		String url = loansBaseUrl.concat(GET_SCORING_EXCEL);
		logger.info("url to get Corporate Score Calculation ==>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set(REQ_AUTH, "true");
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFile.getBytes()){
                @Override
                public String getFilename(){
                    return "";
                }
            };
            map.add("file", contentsAsResource);
           /* map.add("applicationId", applicationId);*/

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
          return  restTemplate.postForObject(url, request,LoansResponse.class);
            
		} catch (IOException e) {
			logger.error("Exception in getScoringExcel : ",e);

			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getDataForCGTMSE(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_DATA_FOR_CGTMSE).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDataForCGTMSE : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse getDirectorsListByApplicationId(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_DIRECTORLIST_BY_APPLICATION_ID) + "/" + applicationId;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getDirectorsListByApplicationId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	/**
	 * CHEKCK AMOUNT IS UNDER USER DISBURSEMENT AUTHORITY 
	 * @param amount
	 * @param userId
	 * @param productId
	 * @return
	 * @throws LoansException
	 */
	public LoansResponse checkAmountByUserIdAndProductId(Long userId, Double amount, Long productId) throws LoansException {
		String url = loansBaseUrl.concat(CHECK_AMOUNT_BY_USERID_AND_PRODUCTID);
		logger.info("Enter in LoanClient For Check amount for FP user ----------->{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			UserLoanAmountMappingRequest req = new UserLoanAmountMappingRequest(userId,productId,amount); 
			HttpEntity<UserLoanAmountMappingRequest> entity = new HttpEntity<>(req, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in checkAmountByUserIdAndProductId : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	/**
	 * GET USER MIN MAX AND PRODUCT MAPPING DATA BY USER ID AND PRODUCT ID 
	 * @param userId
	 * @param productId
	 * @return
	 * @throws LoansException
	 */
	public LoansResponse getUserAmountMapByUserAndProduct(Long userId, Long productId) throws LoansException {
		String url = loansBaseUrl.concat(GET_BY_USERID_AND_PRODUCTID);
		logger.info("Enter in GET user amount mapping details by user id and product id -------> {}", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			UserLoanAmountMappingRequest req = new UserLoanAmountMappingRequest(userId,productId);
			HttpEntity<UserLoanAmountMappingRequest> entity = new HttpEntity<>(req, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in getUserAmountMapByUserAndProduct : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	
	public LoansResponse getDataForHunter(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_DATA_FOR_HUNTER).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDataForHunter : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getDataForHunterForNTB(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_DATA_FOR_HUNTER_NTB).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDataForHunterForNTB : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	
	public LoansResponse getCMAForGateway(Long applicationId,Long productDocumentMappingId) throws LoansException {
		String url = loansBaseUrl.concat(GET_CMA_BY_APPLICATIONID_PRODUCTDOCUMENTMAPPINGID).concat("/"+applicationId+"/"+productDocumentMappingId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCMAForGateway : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse updatePaymentInfoForGateway(PaymentRequest paymentRequest) throws LoansException {
		String url = loansBaseUrl.concat(UPDATE_PAYMENT_STATUS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(paymentRequest, headers), LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in updatePaymentInfoForGateway : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveOrUpdateDirectorIncomeDetails(List<CorporateDirectorIncomeRequest> request) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_UPDATE_DIRECTOR_INCOME_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<CorporateDirectorIncomeRequest>> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveOrUpdateDirectorIncomeDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getDirectorIncomeDetails(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_DIRECTOR_INCOME_DETAILS).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDirectorIncomeDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getDirectorIncomeLatestYearDetails(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_DIRECTOR_INCOME_LATEST_YEAR_DETAILS).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getDirectorIncomeLatestYearDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public String getToken(GenerateTokenRequest generateTokenRequest) throws LoansException {

		String url = loansBaseUrl.concat(GET_TOKEN);
		logger.info("Sidbi Integration get token URL===>{}" , url);
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<?> entity = new HttpEntity<>(generateTokenRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity , String.class)
					.getBody();
		} catch (Exception e) {
			logger.error("Exception while getting token in  Loan Client!!",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public String setTokenAsExpired(GenerateTokenRequest generateTokenRequest) throws LoansException {

		String url = loansBaseUrl.concat(SET_TOKEN_AS_EXPIRED);
		logger.info("Sidbi Integration set token as expire URL===>{}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
		
			HttpEntity<?> entity = new HttpEntity<>(generateTokenRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity , String.class)
					.getBody();
		} catch (Exception e) {
			logger.error("Exception while setting  token as expired Loan Client!!",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getRetailApplicantIncomeDetails(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_RETAIL_APPLICANT_INCOME_DETAILS).concat("/" + applicationId);
		try {
			logger.info("Enter in Get Retail Applicant Income Details---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getRetailApplicantIncomeDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public List<RetailApplicantIncomeRequest> getRetailApplicantIncomeDetailsOfCoApplicant(Long coApplicantId) throws LoansException {
		String url = loansBaseUrl.concat(GET_RETAIL_APPLICANT_INCOME_DETAILS_FOR_CO_APPLICANT).concat("/" + coApplicantId);
		try {
			logger.info("Enter in Get Retail Applicant Income Details---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<RetailApplicantIncomeRequest>>() {
            }).getBody();
		} catch (Exception e) {
			logger.error("Exception in getRetailApplicantIncomeDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveOrUpdateRetailApplicantIncomeDetails(List<RetailApplicantIncomeRequest> request) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_RETAIL_APPLICANT_INCOME_DETAILS);
		try {
			logger.info("Enter in save Retail Applicant Income Details---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<RetailApplicantIncomeRequest>> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveOrUpdateRetailApplicantIncomeDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveITRResRetailApplicantDetails(RetailApplicantRequest request) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_ITR_RETAIL_APPLICANT_DETAILS);
		try {
			logger.info("Enter in save ITR Response Retail Applicant Details---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<RetailApplicantRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveITRResRetailApplicantDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveITRResMudraApplicantDetails(CorporateApplicantRequest request) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_MUDRA_ITR_APPLICANT_DETAILS);
		try {
			logger.info("Enter in save Mudra ITR Response Retail Applicant Details---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<CorporateApplicantRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveITRResMudraApplicantDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveITRResRetailCoApplicantDetails(RetailApplicantRequest request) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_ITR_RETAIL_CO_APPLICANT_DETAILS);
		try {
			logger.info("Enter in save ITR Response Retail CO Applicant Details---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<RetailApplicantRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveITRResRetailCoApplicantDetails : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getPincodeData(String request) throws LoansException {
		String url = loansBaseUrl.concat(GET_PINCODE_DATA).concat(request);
		try {
			logger.info("Entering in GET_PINCODE_DATA -----> {}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getPincodeData : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	/**
	 * @param id : applicationId(Long) and wcRenewalStatus(Integer) :- Oneform WcRenewalType Enum 
	 * @return
	 * @throws LoansException
	 */
	public LoansResponse saveLoanWCRenewalType(LoanApplicationRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_LOAN_WC_RENEWAL_TYPE);
		try {
			logger.info("Enter in saveLoanWCRenewalType---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LoanApplicationRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveLoanWCRenewalType : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getLoanWCRenewalType(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(GET_LOAN_WC_RENEWAL_TYPE);
		try {
			logger.info("Enter in Get WC Renewal Type Status---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getLoanWCRenewalType : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse saveIneligibleProposal(InEligibleProposalDetailsRequest loanRequest) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_INELIGIBALE_PROPOSAL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<InEligibleProposalDetailsRequest> entity = new HttpEntity<>(loanRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveIneligibleProposal : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse checkIsExistOfflineProposalByApplicationId(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(IS_EXIST_OFFLINE_PROPOSAL_BY_APPID).concat("/"+applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in isExistOfflineProposalsByApplicationId : ", e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	public LoansResponse getMfiApplicantDetails(Long applicationId,Integer type) throws LoansException {
		String url = loansBaseUrl.concat(GET_MFI_APPLICANT_DETAILS).concat("/" + applicationId +"/" + type);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {			
			logger.error("{}",e);
			throw new LoansException("Loans service is not available While executing getMfiApplicantDetails()...");
		}
	}

	public LoansResponse getLoanApplicationByProposalId(Long proposalId) throws LoansException {
		String url = loansBaseUrl.concat(GET_LOAN_APPLICATION_BY_PROPOSAL_ID).concat("/" + proposalId);
		try {
			logger.info("Enter in GET_LOAN_PROPOSAL_BY_PROPOSAL_ID ---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveIneligibleProposal : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	/**
	 *
	 * @param keyName
	 * @return  in data key
	 * @throws LoansException
	 */
	public LoansResponse getCommonPropValue(String keyName) throws LoansException {
		String url = loansBaseUrl.concat(GET_COMMON_PROPERTIES).concat("/" + keyName);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCommonPropValue : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	/**
	 * Client for cam report primary data uses in gateway
	 * */
	public LoansResponse getCamReportPrimaryData(Long applicationId,Long fpProductId, Long proposalId) throws LoansException {
		String url = loansBaseUrl.concat(GET_PRIMARY_DETAILS_CAM+"/"+ applicationId+"/" +fpProductId + "/" +proposalId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getCamReportPrimaryData : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	/**
	 * Client for PL cam report primary data uses in gateway
	 * */
	public LoansResponse getPLCamReportPrimaryData(Long applicationId,Long fpProductId, Long proposalId) throws LoansException {
		String url = loansBaseUrl.concat(GET_PL_PRIMARY_DETAILS_CAM+"/"+ applicationId+"/" +fpProductId + "/" +proposalId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getPLCamReportPrimaryData : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	/**
	 * Client for HL cam report primary data uses in gateway
	 * */
	public LoansResponse getHLCamReportPrimaryData(Long applicationId,Long fpProductId, Long proposalId) throws LoansException {
		String url = loansBaseUrl.concat(GET_HL_PRIMARY_DETAILS_CAM+"/"+ applicationId+"/" +fpProductId + "/" +proposalId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getHLCamReportPrimaryData : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	/**
	 * Client for AL cam report primary data uses in gateway
	 * */
	public LoansResponse getALCamReportPrimaryData(Long applicationId,Long fpProductId, Long proposalId) throws LoansException {
		String url = loansBaseUrl.concat(GET_AL_PRIMARY_DETAILS_CAM+"/"+ applicationId+"/" +fpProductId + "/" +proposalId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getALCamReportPrimaryData : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	/**
	 * Client for Application Form report data uses in gateway
	 * */
	public byte[] getApplicationFormReport(Long applicationId,Long fpProductId, Long proposalId , Long loanTypeId) {
		String url = loansBaseUrl.concat(GET_APPLICATION_FORM_REPORT + "/" + applicationId + "/" +fpProductId + "/" +proposalId + "/" +loanTypeId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getApplicationForm : {}",e);
		}
		return null;
	}
	
	public LoansResponse getCorporateApplicantByProposalId(Long proposalId) throws LoansException {
		String url = loansBaseUrl.concat(GET_CORPORATE_BY_PROPOSAL_ID).concat("/" + proposalId);
		try {
			logger.info("Enter in GET_LOAN_PROPOSAL_BY_PROPOSAL_ID ---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {			
			logger.error("{}",e);
			throw new LoansException("Loans service is not available While Get responce from /GET_LOAN_PROPOSAL_BY_PROPOSAL_ID");
		}
	}
	public LoansResponse getInprincipleList() throws LoansException {
		String url = loansBaseUrl.concat(GET_INPRINCIPLE_LIST_FOR_MULTIPLEBANNK);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getInprincipleList  : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public LoansResponse getMultipleBankDayDiffrenceForInprinciple(Integer loanType) throws LoansException {
		String url = loansBaseUrl.concat(GET_DAY_DIFFRENCE_FOR_MULTIPLEBANNK).concat("/")+loanType;
		try {
			logger.info("Enter in GET_DAY_DIFFRENCE_FOR_MULTIPLEBANNK ---------->{}" , url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {			
			logger.error("{}",e);
			throw new LoansException("Loans service is not available While Get responce from /GET_DAY_DIFFRENCE_FOR_MULTIPLEBANNK");
		}
	}
	public LoansResponse saveSwitchExisting(Long applicationId) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_SWITCH_EXISTING);
		logger.info("URL in saveSwitchExisting : {}",url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			PrimaryCorporateRequest primaryCorporateRequest = new PrimaryCorporateRequest();
			primaryCorporateRequest.setId(applicationId);
			HttpEntity<PrimaryCorporateRequest> entity = new HttpEntity<>(primaryCorporateRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveSwitchExisting :{} ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	public HomeLoanModelRequest getHLModel(Long modelId) {
		String url = loansBaseUrl.concat(GET_LOAN_PURPOSE_MODEL).concat("/" + modelId);
		logger.info("URL in getHLModel : {}",url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, HomeLoanModelRequest.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getHLModel :{} ",e);
			return null;
		}
	}
	
	public LoansResponse savePaymentGatewayAuditLogs(PaymentRequest paymentRequest) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_LOGS_OF_PAYMENT_GATEWAY);
		logger.info("Save Payment Gateway Audit URL==>{}", url);
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<PaymentRequest> entity = new HttpEntity<PaymentRequest>(paymentRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception while calling gateway client for updatepayment ------------>",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	// FOR RETAIL CONCESSION RELATED
	public LoansResponse getRetailConcessionDetails(ScoringRequestLoans scoringRequestLoans) throws LoansException {
		String url = loansBaseUrl.concat(GET_CONCESSION_DETAILS);
		logger.info("Get Retail Concession Details================{}==========={}>>", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ScoringRequestLoans> entity = new HttpEntity<ScoringRequestLoans>(scoringRequestLoans, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			throw new LoansException();
		}
	}
	
	// FOR send mail in-eligible
	public LoansResponse sendInEligibleForSidbi(Long applicationId) throws LoansException {		
		String url = loansBaseUrl.concat(SEND_MAIL_INELIGIBLE_FOR_SIDBI).concat("/" + applicationId);
		logger.info("send InEligibleForSidbi================{}==========={}>>", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(applicationId, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();

		} catch (Exception e) {
			logger.error("Exception in sendInEligibleForSidbi : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}
	
	/**
	 * GET APPLICATION CAMPAIGN CODE FROM LOAN APPLICATION MASTER
	 * @param applicationId
	 * @return PLEASE FIND CAMPAIGN CODE FROM "data" key from "LoansResponse" Class
	 * @throws ExcelException 
	 */
	public LoansResponse getApplicationCampaignCode(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_APPLICATION_CAMPAIGN_CODE).concat("/" + applicationId);
		logger.info("url for Getting APPLICATION CAMPIGN CODE=================>{} = {} = {}" , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getApplicationCampaignCode : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	
	/**
	 * GET FINANCIAL DATA FOR PUSH API, IRR, CAMS and TEASOR VIEW
	 * @param applicationId
	 * @param proposalId
	 * @return PLEASE FIND FinancialRequest FROM "data" key from "LoansResponse" Class
	 * @throws ExcelException
	 * @Controller :- CMAController.java
	 * UTL :- http://localhost:8484/loans/cma/getFinancialDetailsForBankIntegration/10001179?proposalId=3366
	 */
	public LoansResponse getFinancialData(Long applicationId,Long proposalId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_FINANCIAL_DATA).concat("/" + applicationId);
		if(proposalId != null) {
			url.concat("?proposalId=" + proposalId);
		}
		logger.info("url for Getting FINANCIAL DETAILS=================>{} = {} = {}" , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getFinancialData : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	/**
	 * GET Loan Amount For In-eligible case For (LOS/LLMS SBI)  
	 * @param applicationId
	 * 
	 */
	public LoansResponse getLoanAmountForInEligibileCase(Long applicationId)  {
		String url = loansBaseUrl.concat(LOAN_AMOUNT_FOR_INELIGIBLE).concat("/" + applicationId);
		
		logger.info("url for Getting LOAN_AMOUNT_FOR_INELIGIBLE DETAILS=================>{} = {} = {}" , url , AND_FOR_APPLICATION_ID , applicationId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(REQ_AUTH, "true");
		HttpEntity<?> entity = new HttpEntity<>(null, headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		
	}

	/**
	 * Save bank relation
	 * @param relationReq
	 * @param applicationId
	 * @param userId
	 * @return
	 * @throws LoansException
	 */
	public LoansResponse saveBankRelation(BankRelationshipRequest relationReq , Long applicationId, Long userId) throws LoansException {
		String url = loansBaseUrl.concat(SAVE_BANK_RELATION) + applicationId + "?userId=" + userId;
		logger.info("save Bank Relation url-------------->> [{}]", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<BankRelationshipRequest> entity = new HttpEntity<BankRelationshipRequest>(relationReq, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getMergedProfile : ",e);
			throw new LoansException();
		}
	}
	
	public LoansResponse getMergedProfile(RetailOnformContactInfoReq req) throws LoansException {
		String url = loansBaseUrl.concat(GET_MERGED_PROFILE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<RetailOnformContactInfoReq> entity = new HttpEntity<>(req, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in saveBankRelation : ",e);
			throw new LoansException();
		}
	}
	
	/**
	 * GET APPLICATION CAMPAIGN DETAILS(OrgId, OrgCode, OrgName, IsBankSpecificON) FROM LOAN APPLICATION MASTER
	 * @param applicationId
	 * @return PLEASE FIND CAMPAIGN DETAILS OBJECT MAP FROM "data" key from "LoansResponse" Class
	 * @Map From data key :- map.put("userOrgId",obj[0]); map.put("organisationName", obj[1]); map.put("campaignCode", obj[2]); map.put("isBankSpecificOn", true or false or null);
	 * @throws ExcelException 
	 */
	public LoansResponse getApplicationCampaignDetails(Long applicationId) throws ExcelException {
		String url = loansBaseUrl.concat(GET_APPLICATION_CAMPAIGN_DETAILS).concat("/" + applicationId);
		logger.info("url for Getting APPLICATION CAMPIGN DETAILS=================>{} = {} = {}" , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getApplicationCampaignDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}
	public LoansResponse getOemList() throws ExcelException {
		String url = loansBaseUrl.concat(OEM_LIST);
		logger.info("url for Getting OEM LIST =================>{} = {} = {}" , url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getApplicationCampaignDetails : ",e);
			throw new ExcelException(e.getCause().getMessage());
		}
	}

	public LoansResponse getBankRelationsSalaryDetail(Long applicationId, String acc) throws LoansException {
		String url = loansBaseUrl.concat(GET_BANK_RELATION_SALARY).concat("/" + applicationId+"/"+acc);
		logger.info("url for Getting BANK RELATION DETAILS=================>{} = {} = {}" , url , AND_FOR_APPLICATION_ID , applicationId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getBankRelations : ",e);
			throw new LoansException(e.getCause().getMessage());
		}
	}

	public LoansResponse updateBankRelation(Long id) throws LoansException {
		String url = loansBaseUrl.concat(UPDATE_BANK_RELATION) + id;
		logger.info("save Bank Relation url-------------->> [{}]", url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<BankRelationshipRequest> entity = new HttpEntity<BankRelationshipRequest>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, LoansResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Exception in getMergedProfile : ",e);
			throw new LoansException();
		}
	}


public String getCamVersionForBSStandalone(String  type) throws LoansException {
	String url = loansBaseUrl.concat(BS_CAM_VERSION) + type;
	logger.info("save Bank Relation url-------------->> [{}]", url);
	try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(REQ_AUTH, "true");
		HttpEntity<BankRelationshipRequest> entity = new HttpEntity<BankRelationshipRequest>(null, headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
	} catch (Exception e) {
		logger.error("Exception in getCamVersionForBSStandalone : ",e);
		throw new LoansException();
	}
}

}

