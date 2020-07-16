package com.opl.retail.client.matches;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.exceptions.matches.MatchException;
import com.opl.retail.api.model.matches.ApplicationProductAuditRequest;
import com.opl.retail.api.model.matches.MatchDisplayObject;
import com.opl.retail.api.model.matches.MatchDisplayResponse;
import com.opl.retail.api.model.matches.MatchRequest;
import com.opl.retail.api.model.matches.MatchResponse;
import com.opl.retail.api.utils.matches.CommonUtils;
import static com.opl.retail.api.common.CommonUtils.BusinessType.RETAIL_AUTO_LOAN;
import static com.opl.retail.api.common.CommonUtils.BusinessType.RETAIL_PERSONAL_LOAN;
import static com.opl.retail.api.common.CommonUtils.BusinessType.RETAIL_HOME_LOAN;

public class MatchEngineClient {

	private static Logger logger = LoggerFactory.getLogger(MatchEngineClient.class);

	private static final String CALCULATE_MATCHES_OF_CORPORATE_FUNDPROVIDER = "/matches/corporate/fundprovider";
	private static final String CALCULATE_MATCHES_RESPONSE = "/matches/get_match_response";
	private static final String CALCULATE_MATCHES_OF_CORPORATE_FUNDSEEKER = "/matches/corporate/fundseeker";
	private static final String CALCULATE_MATCHES_OF_RETAIL_FUNDPROVIDER = "/matches/retail/fundprovider";
	private static final String CALCULATE_MATCHES_OF_RETAIL_FUNDSEEKER = "/matches/retail/fundseeker";
	private static final String DISPLAY_MATCHES_OF_CORPORATE = "/matches/corporate/matchDisplay";
	private static final String DISPLAY_MATCHES_OF_MFI = "/matches/mfi/matchDisplay";
	
	private static final String DISPLAY_MATCHES_OF_RETAIL = "/matches/retail/matchDisplay";
	private static final String DISPLAY_OFFLINE_MATCHES_OF_RETAIL = "/matches/retail/offline/matchDisplay";
	
	private static final String CALCULATE_STAGE_WISE_MATCHES_OF_CORPORATE_FUNDSEEKER = "/matches/corporate/fundseeker/stagewise";
	
	private static final String CALCULATE_MATCHES_OF_CORPORATE_FUNDSEEKER_UNIFORM = "/matches/corporate/fundseeker/uniform";
	private static final String CALCULATE_MATCHES_OF_CORPORATE_FUNDSEEKER_MFI = "/matches/mfi/fundseeker";
	private static final String CALCULATE_STAGE_WISE_MATCHES_OF_RETAIL_FUNDSEEKER = "/matches/retail/fundseeker/stagewise";
	private static final String DISPLAY_STAGE_WISE_MATCHED_LIST = "/matches/corporate/stagewise/matchList";
	private static final String LIST_MFI_MATCHES = "/matches/mfi/matchListMFIProduct";
	private static final String PROCESS_ADDITIONAL_EXISTING_LOAN_AMT = "/matches/corporate/processForAdditionalAndExistingLoanAmtLogic";
	private static final String SBI_MATCHES_LIST = "/matches/list/sbi/wc/renewal/matches";
	private static final String ADD_PROPOSAL = "/matches/mfi/addProposal";
	private static final String EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE = "Match Service is not available";

	private static final String SAVE_CORPORATE_MATCHES_DATA_MSME = "/matches/corporate/msmeMatchesResponse";
	private static final String SAVE_RETAIL_MATCHES_DATA_PL = "/matches/retail/plMatchesResponse";
    private static final String SAVE_RETAIL_MATCHES_DATA_HL = "/matches/retail/hlMatchesResponse";
    private static final String SAVE_RETAIL_MATCHES_DATA_AL = "/matches/retail/autoMatchesResponse";
	
	
	/*private static final String GET_CORPORATE_MATCHES_VALUE = "/matches/corporate/getmatchDisplay";*/
	
	private String matchBaseUrl;
	private RestTemplate restTemplate;

	public MatchEngineClient(String otpBaseUrl) {
		this.matchBaseUrl = otpBaseUrl;
		restTemplate = new RestTemplate();
	}

	public MatchResponse calculateMatchesOfCorporateFundProvider(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(CALCULATE_MATCHES_OF_CORPORATE_FUNDPROVIDER);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchResponse calculateMatchesOfCorporateFundSeeker(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(CALCULATE_MATCHES_OF_CORPORATE_FUNDSEEKER);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		logger.info(url);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	/*public Boolean checkStageWiseMatch(Long applicationId, CommonUtils.MatchesStages stage,Integer businessTypeId) throws MatchException {

		try {
			MatchResponse response = calculateStageWiseMatchesOfCorporateFundSeeker(applicationId, stage,businessTypeId);
			if (!CommonUtils.isObjectNullOrEmpty(response) && !CommonUtils.isObjectNullOrEmpty(response.getData())
					&& Boolean.valueOf(response.getData().toString())) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}

		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}*/

	public MatchResponse calculateStageWiseMatchesOfCorporateFundSeeker(MatchRequest matchRequest) throws MatchException {
		String url = null;
		if(RETAIL_PERSONAL_LOAN.getId() == matchRequest.getBusinessTypeId()
				|| RETAIL_HOME_LOAN.getId() == matchRequest.getBusinessTypeId()
				|| RETAIL_AUTO_LOAN.getId() == matchRequest.getBusinessTypeId()){
			url = matchBaseUrl.concat(CALCULATE_STAGE_WISE_MATCHES_OF_RETAIL_FUNDSEEKER);
		}else {
			url = matchBaseUrl.concat(CALCULATE_STAGE_WISE_MATCHES_OF_CORPORATE_FUNDSEEKER);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MatchRequest> request1 = new HttpEntity<>(matchRequest, headers);
		
		logger.info(url);
		try {
			//Long applicationId,CommonUtils.MatchesStages stage,Integer businessTypeId

			MatchResponse matchResponse = restTemplate.postForObject(url, request1, MatchResponse.class);
			logger.info("applicationId->"+matchRequest.getApplicationId()+" for stage->"+matchRequest.getStage()+" stagewise calculate resposnse ->"+matchResponse.toString());
			return matchResponse;
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public MatchResponse saveCorporateMatchesResponse(MatchRequest matchRequest) throws MatchException {
		
		String url = matchBaseUrl.concat(SAVE_CORPORATE_MATCHES_DATA_MSME);;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MatchRequest> request1 = new HttpEntity<>(matchRequest, headers);
		try {
					restTemplate.postForObject(url, request1, MatchResponse.class);
			return null;
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	
	public MatchResponse calculateStageWiseMatchesOfCorporateUniformProduct(Long applicationId) throws MatchException {
		String url = matchBaseUrl.concat(CALCULATE_MATCHES_OF_CORPORATE_FUNDSEEKER_UNIFORM);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Long> request1 = new HttpEntity<Long>(applicationId, headers);
		logger.info(url);
		try {
			//Long applicationId,CommonUtils.MatchesStages stage,Integer businessTypeId

			MatchResponse matchResponse = restTemplate.postForObject(url, request1, MatchResponse.class);
			logger.info("applicationId->" + applicationId +" and Response Is ===>" + matchResponse.toString());
			return matchResponse;
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchResponse calculateMatchesOfFundSeekerMFI(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(CALCULATE_MATCHES_OF_CORPORATE_FUNDSEEKER_MFI);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);
		logger.info(url);
		try {
			return restTemplate.postForObject(url, request1, MatchResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchResponse displayMatchList(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(DISPLAY_STAGE_WISE_MATCHED_LIST);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);
		logger.info(url);
		try {
			return restTemplate.postForObject(url, request1, MatchResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchResponse calculateMatchesOfRetailFundProvider(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(CALCULATE_MATCHES_OF_RETAIL_FUNDPROVIDER);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchResponse calculateMatchesOfRetailFundSeeker(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(CALCULATE_MATCHES_OF_RETAIL_FUNDSEEKER);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchDisplayResponse displayMatchesOfCorporate(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(DISPLAY_MATCHES_OF_CORPORATE);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchDisplayResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchDisplayResponse displayMatchesOfMFI(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(DISPLAY_MATCHES_OF_MFI);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchDisplayResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchResponse processForAdditionalAndExistingLoanAmtLogic(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(PROCESS_ADDITIONAL_EXISTING_LOAN_AMT);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	@SuppressWarnings("unchecked")
	public List<MatchDisplayObject> getMatchesResponse(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(CALCULATE_MATCHES_RESPONSE);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);
			return restTemplate.postForObject(url, request1, List.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION + e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchDisplayResponse displayMatchesOfRetail(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(DISPLAY_MATCHES_OF_RETAIL);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchDisplayResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public MatchDisplayResponse displayOfflineMatchesOfRetail(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(DISPLAY_OFFLINE_MATCHES_OF_RETAIL);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchDisplayResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchResponse matchListMFIProduct(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(LIST_MFI_MATCHES);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	/*public MatchDisplayResponse getDisplayMatchesOfCorporate(MatchRequest request) throws MatchException {
		System.out.println("=================Enter in getDisplayMatchesOfCorporate ================");
		
		String url = matchBaseUrl.concat(GET_CORPORATE_MATCHES_VALUE);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchDisplayResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}*/
	public ApplicationProductAuditRequest listSbiMatchesList(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(SBI_MATCHES_LIST);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, ApplicationProductAuditRequest.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public MatchResponse addProposal(MatchRequest request) throws MatchException {
		String url = matchBaseUrl.concat(ADD_PROPOSAL);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			HttpEntity<MatchRequest> request1 = new HttpEntity<>(request, headers);

			return restTemplate.postForObject(url, request1, MatchResponse.class);
		} catch (Exception e) {
			logger.trace(CommonUtils.EXCEPTION+e.getMessage(), e);
			throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public MatchResponse saveRetailMatchesResponse(MatchRequest matchRequest) throws MatchException {
        String url = matchBaseUrl.concat(SAVE_RETAIL_MATCHES_DATA_PL);
        ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MatchRequest> request1 = new HttpEntity<>(matchRequest, headers);
        try {
            restTemplate.postForObject(url, request1, MatchResponse.class);
            return null;
        } catch (Exception e) {
            logger.trace(CommonUtils.EXCEPTION + e.getMessage(), e);
            throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public MatchResponse saveRetailMatchesResponseHL(MatchRequest matchRequest) throws MatchException {
        String url = matchBaseUrl.concat(SAVE_RETAIL_MATCHES_DATA_HL);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MatchRequest> request1 = new HttpEntity<>(matchRequest, headers);
        try {
            restTemplate.postForObject(url, request1, MatchResponse.class);
            return null;
        } catch (Exception e) {
            logger.trace(CommonUtils.EXCEPTION + e.getMessage(), e);
            throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public MatchResponse saveRetailMatchesResponseAUTO(MatchRequest matchRequest) throws MatchException {
        String url = matchBaseUrl.concat(SAVE_RETAIL_MATCHES_DATA_AL);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MatchRequest> request1 = new HttpEntity<>(matchRequest, headers);
        try {
            restTemplate.postForObject(url, request1, MatchResponse.class);
            return null;
        } catch (Exception e) {
            logger.trace(CommonUtils.EXCEPTION + e.getMessage(), e);
            throw new MatchException(EXCEPTION_MATCH_SERVICE_IS_NOT_AVAILABLE);
        }
    }
}
