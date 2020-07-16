package com.opl.retail.client.matches;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.exceptions.matches.MatchException;
import com.opl.retail.api.model.matches.ProposalCountResponse;
import com.opl.retail.api.model.matches.ProposalMappingRequest;
import com.opl.retail.api.model.matches.ProposalMappingResponse;
import com.opl.retail.api.utils.matches.CommonUtils;

public class ProposalDetailsClient {
	
	
	private static Logger logger = LoggerFactory.getLogger(ProposalDetailsClient.class);

	private static final String PROPOSAL_LIST_FUNDPROVIDER = "/proposal/fundprovider";
	private static final String PROPOSAL_LIST_FUNDSEEKER = "/proposal/fundseeker";
	private static final String PROPOSAL_COUNT_FUNDPROVIDER = "/proposal/count/fundprovider";
	private static final String PROPOSAL_COUNT_FUNDSEEKER = "/proposal/count/fundseeker";
	private static final String PROPOSAL_CHANGE_STATUS = "/proposal/changeStatus";
	private static final String PROPOSAL_SENDREQUEST = "/proposal/sendRequest";
	private static final String PROPOSAL_GET = "/proposal/get";
	private static final String LIST_PROPOSAL = "/proposal/listfundseekerproposal";
	private static final String LIST_PROPOSAL_CONNECTIONS = "/proposal/connections";
	private static final String GET_APPLICATION_STATUS = "/proposal/fundseeker/applicationStatus";
	private static final String LIST_CHATLIST_FUNDPROVIDER = "/proposal/chat_list/fundprovider";
	private static final String LIST_CHATLIST_FUNDSEEKER = "/proposal/chat_list/fundseeker";
	private static final String SAVE_SUGGESTION_LIST = "/proposal/saveSuggestionList";
	private static final String CHECK_PROPOSAL_PENDING = "/proposal/checkPendingProposal";
	private static final String GET_PROPOSAL_PENDING_COUNT = "/proposal/getPendingProposalCount";
	private static final String UPDATE_ASSIGN_DETAILS = "/proposal/updateAssignDetails";
	private static final String LIST_PROPOSAL_BY_ASSIGN_BY = "/proposal/listProposalByAssign";
	private static final String SAVE_DISBURSEMENT_DETAILS = "/proposal/saveDisbursementDetails";
	private static final String SAVE_PROPOSAL_ON_LOAN_SELECTION = "/proposal/save/onLoanSelection";
	private static final String UPDATE_BRANCHID = "/proposal/updateBranchId";
	private static final String ACTIVATE_PROPOSAL = "/proposal/activate/proposal";
	private static final String GET_ACTIVE_PROPOSAL = "/proposal/getActiveProposal";
	private static final String GET_IN_PRINCIPLE = "/proposal/getInPrincipleByApplicationId";
	private static final String GET_IN_PRINCIPLE_MOBILE = "/proposal/mobile/getInPrincipleByApplicationId";
	private static final String GET_ACTIVE_PROPOSAL_DETAILS = "/proposal/getActiveProposalDetails";
	private static final String GET_ACTIVE_PROPOSAL_DETAILS_BY_APPLICATION_ID = "/proposal/getActiveProposalByApplicationId";
	private static final String GET_PPROPOSALS_BY_APPLICATIONID = "/proposal/getProposalsByApplicationId";
	private static final String GET_PPROPOSALS_BY_APPLICATIONID_AND_USER_ORG_ID = "/proposal/getProposalByApplicationIdAndUserOrgId";
	private static final String GET_DISBURSEMENT_REUEST_DETAILS = "/proposal/getRequestDisbursementDetails";
	private static final String SAVE_DISBURSEMENT_REUEST_DETAILS = "/proposal/saveRequestDisbursementDetails";

	private static final String EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE = "Proposal Service is not available";
    private static final String EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE = "Match Service is not available";
    private static final String REQUEST_AUTH_HEADER = "req_auth";

	private String matchBaseUrl;
	private RestTemplate restTemplate;

	public ProposalDetailsClient(String otpBaseUrl) {
		this.matchBaseUrl = otpBaseUrl;
		restTemplate = new RestTemplate();
	}
	
	
	public ProposalMappingResponse proposalListOfFundProvider(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(PROPOSAL_LIST_FUNDPROVIDER);
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	
	
	public ProposalMappingResponse proposalListOfFundSeeker(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(PROPOSAL_LIST_FUNDSEEKER);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalCountResponse proposalCountOfFundSeeker(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(PROPOSAL_COUNT_FUNDSEEKER);
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalCountResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalCountResponse proposalCountOfFundProvider(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(PROPOSAL_COUNT_FUNDPROVIDER);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalCountResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse changeStatus(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(PROPOSAL_CHANGE_STATUS);
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse sendRequest(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(PROPOSAL_SENDREQUEST);
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse getProposal(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(PROPOSAL_GET);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse listOfFundSeekerProposal(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(LIST_PROPOSAL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse connections(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(LIST_PROPOSAL_CONNECTIONS);
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse getFundSeekerApplicationStatus(Long applicationId) throws MatchException {
		String url = matchBaseUrl.concat(GET_APPLICATION_STATUS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(applicationId, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	
	public ProposalMappingResponse getFundProviderChatList(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(LIST_CHATLIST_FUNDPROVIDER);
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	
	public ProposalMappingResponse getFundSeekerChatList(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(LIST_CHATLIST_FUNDSEEKER);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse saveSuggestionList(List<Long> request) throws MatchException {
		String url = matchBaseUrl.concat(SAVE_SUGGESTION_LIST);
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long> > entity = new HttpEntity<List<Long> >(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException("saveSuggestionList Service is not available");
		}
	}
	
	public ProposalMappingResponse checkPendingProposal() throws MatchException {
		String url = matchBaseUrl.concat(CHECK_PROPOSAL_PENDING);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQUEST_AUTH_HEADER, "true");
		    HttpEntity<Long> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, ProposalMappingResponse.class).getBody();
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException("checkPendingProposal Service is not available");
		}
	}	

	
	public ProposalMappingResponse getPendingProposalCount(Long request) throws MatchException {
		String url = matchBaseUrl.concat(GET_PROPOSAL_PENDING_COUNT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException("getPendingProposalCount Service is not available");
		}
	}
	
	/**
	 * AXIS BANK CHANGES
	 * @param request
	 * @return
	 * @throws MatchException
	 */
	public ProposalMappingResponse updateAssignDetails(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(UPDATE_ASSIGN_DETAILS);
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException("Proposal Service is not available when updated assign details");
		}
	}
	
	public ProposalMappingResponse proposalListByAssignee(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(LIST_PROPOSAL_BY_ASSIGN_BY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_PROPOSAL_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse saveDisbursementDetails(Object request) throws MatchException {
		String url = matchBaseUrl.concat(SAVE_DISBURSEMENT_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQUEST_AUTH_HEADER, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			logger.info("In Proposal Client for  SAVE DISBURSEMENT");
			HttpEntity<Object> entity = new HttpEntity<Object>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException("error while aveDisbursementDetails");
		}
	}

	public ProposalMappingResponse savePoposalOnLoanSelection(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(SAVE_PROPOSAL_ON_LOAN_SELECTION);
		logger.info("savePoposalOnLoanSelection------->" + url );
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQUEST_AUTH_HEADER, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse updateBranchId(ProposalMappingRequest request) throws MatchException {
		String url = matchBaseUrl.concat(UPDATE_BRANCHID);
		logger.info("updateBranchId------->{}" , url );
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQUEST_AUTH_HEADER, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ProposalMappingResponse activateProposalOnPayment(Long applicationId) throws MatchException {
		String url = matchBaseUrl.concat(ACTIVATE_PROPOSAL);
		logger.info(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			ProposalMappingRequest request = new ProposalMappingRequest();
			request.setApplicationId(applicationId);
			HttpEntity<ProposalMappingRequest> request1 = new HttpEntity<>(request, headers);
			return restTemplate.postForObject(url, request1, ProposalMappingResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse getActivateProposalById(Long fpProductId, Long applicationId) throws MatchException {
		String url = matchBaseUrl.concat(GET_ACTIVE_PROPOSAL).concat("/" +fpProductId).concat("/" + applicationId);
		logger.info(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			ProposalMappingRequest request = new ProposalMappingRequest();
			request.setApplicationId(applicationId);
		
			HttpEntity<ProposalMappingRequest> request1 = new HttpEntity<>(request, headers);
			return restTemplate.postForObject(url, request1, ProposalMappingResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse getInPricipleById(Long applicationId) throws MatchException {
		String url = matchBaseUrl.concat(GET_IN_PRINCIPLE);
		logger.info(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<Long> request1 = new HttpEntity<>(applicationId, headers);
			return restTemplate.postForObject(url, request1, ProposalMappingResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse getActiveProposalDetails(ProposalMappingRequest proposalMappingRequest) throws MatchException {
		String url = matchBaseUrl.concat(GET_ACTIVE_PROPOSAL_DETAILS);
		logger.info(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<ProposalMappingRequest> request1 = new HttpEntity<>(proposalMappingRequest, headers);
			return restTemplate.postForObject(url, request1, ProposalMappingResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ProposalMappingResponse getActiveProposalByApplicationID(Long applicationId) throws MatchException {

		String url = matchBaseUrl.concat(GET_ACTIVE_PROPOSAL_DETAILS_BY_APPLICATION_ID);
		logger.info(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			ProposalMappingRequest proposalMappingRequest = new ProposalMappingRequest();
			proposalMappingRequest.setApplicationId(applicationId);
			HttpEntity<ProposalMappingRequest> request1 = new HttpEntity<>(proposalMappingRequest,headers);
			return restTemplate.postForObject(url, request1, ProposalMappingResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse getProposalsByApplicationId(Long applicationId) throws MatchException {

		String url = matchBaseUrl.concat(GET_PPROPOSALS_BY_APPLICATIONID);
		logger.info(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			ProposalMappingRequest proposalMappingRequest = new ProposalMappingRequest();
			proposalMappingRequest.setApplicationId(applicationId);
			HttpEntity<ProposalMappingRequest> request1 = new HttpEntity<>(proposalMappingRequest,headers);
			return restTemplate.postForObject(url, request1, ProposalMappingResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse getInPricipleByIdForMobile(Long applicationId, Long orgId) throws MatchException {
		String url = matchBaseUrl.concat(GET_IN_PRINCIPLE_MOBILE).concat("/"+orgId);
		logger.info(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<Long> request1 = new HttpEntity<>(applicationId, headers);
			return restTemplate.postForObject(url, request1, ProposalMappingResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ProposalMappingResponse getProposalByApplicationIdAndUserOrgId(Long userOrgId,Long applicationId) throws MatchException {
		String url = matchBaseUrl.concat(GET_PPROPOSALS_BY_APPLICATIONID_AND_USER_ORG_ID);
		logger.info("updateBranchId------->{}" , url );
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQUEST_AUTH_HEADER, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			ProposalMappingRequest request=new ProposalMappingRequest();
			request.setApplicationId(applicationId);
			request.setUserOrgId(userOrgId);
			HttpEntity<ProposalMappingRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ProposalMappingResponse getDisbursementRequestDetails(Object request) throws MatchException {
		String url = matchBaseUrl.concat(GET_DISBURSEMENT_REUEST_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQUEST_AUTH_HEADER, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			logger.info("In Proposal Client for  get disbursement request");
			HttpEntity<Object> entity = new HttpEntity<Object>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException("error while getting disbursement request");
		}
	}

	public ProposalMappingResponse saveRequestDisbursementDetails(Object disbursementRequest) throws MatchException {
		String url = matchBaseUrl.concat(SAVE_DISBURSEMENT_REUEST_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQUEST_AUTH_HEADER, "true");
			//headers.setContentType(MediaType.APPLICATION_JSON);
			logger.info("In Proposal Client for  save disbursement request");
			HttpEntity<Object> entity = new HttpEntity<>(disbursementRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ProposalMappingResponse.class).getBody();
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException("error while saving disbursement request");
		}
	}
}
