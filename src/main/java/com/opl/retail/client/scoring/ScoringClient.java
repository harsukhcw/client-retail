package com.opl.retail.client.scoring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.common.CommonUtils;
import com.opl.retail.api.exceptions.scoring.ScoringException;
import com.opl.retail.api.model.scoring.GenericCheckerReqRes;
import com.opl.retail.api.model.scoring.ModelParameterResponse;
import com.opl.retail.api.model.scoring.ScoringRequest;
import com.opl.retail.api.model.scoring.ScoringResponse;
import com.opl.retail.api.model.scoring.scoringmodel.ScoringModelReqRes;
import com.opl.retail.api.service.scoring.MCLRReqRes;
import com.opl.retail.api.service.scoring.REPOReqRes;

public class ScoringClient {

	
	private static final String LIST_FIELD = "/list_field";
	private static final String LIST_FIELD_BY_BUSINESS_TYPE_ID = "/list_field_by_business_type_id";
	private static final String LIST_FIELD_BY_BUSINESS_TYPE_ID_FOR_CO_APP = "/list_field_by_business_type_id_for_co_app";
	private static final String CALCULATE_SCORE = "/calculate_score";
	private static final String CALCULATE_SCORE_LIST = "/calculate_score_list";
	private static final String GET_SCORE = "/get_score";
	private static final String GET_SCORE_CO_APPLICANT = "/get_score_coapplicant";
	private static final String GET_SCORE_LIST = "/get_score_list";
	private static final String GET_SCORE_LIST_FOR_CO_APP = "/get_score_list_for_coapp";
	private static final String GET_SCORE_RESULT = "/get_score_result";

	private static final String GET_SCORING_MODEL_MASTER_LIST = "/get_scoring_model_master_list";
	/*private static final String SAVE_SCORING_MODEL = "/save_scoring_model";*/
	private static final String GET_SCORING_MODEL_MASTER_DETAIL = "/get_scoring_model_master_detail";
	/*private static final String SAVE_SCORING_MODEL_DETAIL = "/save_scoring_model_Detail";*/

	private static final String GET_SCORING_MODEL_TEMP_LIST = "/get_scoring_model_temp_list";
	private static final String SAVE_SCORING_MODEL_TEMP = "/save_scoring_model_temp";
	private static final String GET_SCORING_MODEL_TEMP_DETAIL = "/get_scoring_model_temp_detail";
	private static final String SAVE_SCORING_MODEL_TEMP_DETAIL = "/save_scoring_model_temp_Detail";
	private static final String SEND_TO_CHECKER = "/sendToChecker";

	private static final String GET_MIN_MAX_MARGIN = "/get_min_max_margin";

	private static final String GET_AVG_SCORING_MODEL_DATA = "/get_avg_scoring_model_data";

	private static final String GET_MCLR_HISTROY = "/get_mclr_history";
	private static final String GET_REPO_HISTROY = "/get_repo_history";
	private static final String GET_LATEST_MCLR = "/getLatestMCLRDetails";
	private static final String GET_MCLR_FOR_CHECKER = "/getMCLRForChecker";
	private static final String GET_REPO_FOR_CHECKER = "/getREPOForChecker";
	private static final String CREATE_JOB = "/create_job";
	private static final String CREATE_JOB_FOR_REPO = "/create_job_for_repo";
	private static final String SAVE_MCLR = "/save_mclr";
	private static final String SAVE_REPO = "/save_repo";
	private static final String SEND_TO_CHECKER_MCLR = "/sendToCheckerMCLR";
	private static final String SEND_TO_CHECKER_REPO = "/sendToCheckerREPO";
	private static final String GET_EFFECTIVE_MCLR = "/getEffectiveMCLR";
	private static final String COPY_SCORING_MODEL = "/copy_scoring_model";
	private static final String INACTIVATE_SCORING_DETAILS = "/inactive_scoring_details";
	private static final String GET_SCORING_REMOVED_HISTORY = "/get_history_scoring_details";

	private final Logger logger = LoggerFactory.getLogger(ScoringClient.class);
	private static final String REQ_AUTH = "req_auth";
	private static final String SCORING_SERVICE_IS_NOT_AVAILABLE = "Scoring Service is not available";

	
	private String scoringBaseUrl;
	private RestTemplate restTemplate;

	public ScoringClient(String scoringBaseUrl) {
		this.scoringBaseUrl = scoringBaseUrl;
		restTemplate = new RestTemplate();
	}
	
	
	public ScoringResponse listField(ScoringRequest scoringRequest) throws ScoringException {
		String url = scoringBaseUrl.concat(LIST_FIELD);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringRequest> entity = new HttpEntity<ScoringRequest >(scoringRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException("Scoring Service is not available while call listFields");
		}
	}

	public ScoringResponse listFieldByBusinessTypeId(ScoringRequest scoringRequest) throws ScoringException {
		String url = scoringBaseUrl.concat(LIST_FIELD_BY_BUSINESS_TYPE_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringRequest> entity = new HttpEntity<ScoringRequest >(scoringRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException("Scoring Service is not available while call listFields By Business Type Id");
		}
	}
	
	public List<ModelParameterResponse> listFieldByBusinessTypeIdForCoApplicant(ScoringRequest scoringRequest) throws ScoringException {
		String url = scoringBaseUrl.concat(LIST_FIELD_BY_BUSINESS_TYPE_ID_FOR_CO_APP);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringRequest> entity = new HttpEntity<ScoringRequest >(scoringRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<ModelParameterResponse>>() {}).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException("Scoring Service is not available while call listFields By Business Type Id");
		}
	}
	
	public ScoringResponse calculateScore(ScoringRequest scoringRequest) throws ScoringException {
		String url = scoringBaseUrl.concat(CALCULATE_SCORE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringRequest> entity = new HttpEntity<ScoringRequest >(scoringRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse calculateScoreList(List<ScoringRequest> scoringRequestList) throws ScoringException {
		String url = scoringBaseUrl.concat(CALCULATE_SCORE_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequest>> entity = new HttpEntity(scoringRequestList, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse getScore(ScoringRequest scoringRequest) throws ScoringException {
		String url = null;
		if(scoringRequest.getCoAppId() != null) {
			url = scoringBaseUrl.concat(GET_SCORE_CO_APPLICANT);	
		}else {
			url = scoringBaseUrl.concat(GET_SCORE);	
		}
		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringRequest> entity = new HttpEntity<ScoringRequest >(scoringRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ScoringResponse getScoreList(List<ScoringRequest> scoringRequestList) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_SCORE_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequest>> entity = new HttpEntity<List<ScoringRequest>>(scoringRequestList, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ScoringResponse getScoreListForCoApplicant(List<ScoringRequest> scoringRequestList) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_SCORE_LIST_FOR_CO_APP);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ScoringRequest>> entity = new HttpEntity<List<ScoringRequest>>(scoringRequestList, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}


	public ScoringResponse getScoreResult(ScoringRequest scoringRequest) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_SCORE_RESULT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringRequest> entity = new HttpEntity<ScoringRequest >(scoringRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}


	////////////// Fp Scoring Matrix Client//////////

	//from master Controller
	public ScoringModelReqRes getScoringModelMasterList(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_SCORING_MODEL_MASTER_LIST);
		logger.info("Enter in  getScoringModelMasterList(){} =============== URL ==> " + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}


	/*public ScoringModelReqRes saveScoringModel(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(SAVE_SCORING_MODEL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}*/


	public ScoringModelReqRes getScoringModelMasterDetail(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_SCORING_MODEL_MASTER_DETAIL);
		logger.info("Enter in  getScoringModelMasterDetail(){} =============== URL ==> " + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	/*public ScoringModelReqRes saveScoringModelDetail(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(SAVE_SCORING_MODEL_DETAIL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}*/

	//From Temp Comtroller 
	
	public ScoringModelReqRes getScoringModelTempList(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_SCORING_MODEL_TEMP_LIST);
		logger.info("Enter in  getScoringModelTempList(){} =============== URL ==> " + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}


	public ScoringModelReqRes saveScoringModelTemp(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(SAVE_SCORING_MODEL_TEMP);
		logger.info("Enter in  saveScoringModelTemp(){} =============== URL ==> " + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}


	public ScoringModelReqRes getScoringModelTempDetail(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_SCORING_MODEL_TEMP_DETAIL);
		logger.info("Enter in  getScoringModelTempDetail(){} =============== URL ==> " + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringModelReqRes saveScoringModelTempDetail(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(SAVE_SCORING_MODEL_TEMP_DETAIL);
		logger.info("Enter in  saveScoringModelTempDetail(){} =============== URL ==> " + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public List<GenericCheckerReqRes> sendToChecker(List<GenericCheckerReqRes>  genericCheckerReqResList, Long userId) throws ScoringException {
		String url = scoringBaseUrl.concat(SEND_TO_CHECKER+"/"+userId);
		logger.info("Enter in  sendToChecker(){} =============== URL  ==> " + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<GenericCheckerReqRes>> entity = new HttpEntity<List<GenericCheckerReqRes> >(genericCheckerReqResList, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, List.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}


	public ScoringModelReqRes getMinMaxMargin(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_MIN_MAX_MARGIN);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringModelReqRes getAvgScoringModelData(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_AVG_SCORING_MODEL_DATA);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public List<GenericCheckerReqRes> sendToCheckerMCLR(List<GenericCheckerReqRes> genericCheckerReqResList, Long userId) throws ScoringException {
		String url = scoringBaseUrl.concat(SEND_TO_CHECKER_MCLR + "/" + userId);
		logger.info("Enter in  sendToChecker(){} =============== URL  ==> "+url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<GenericCheckerReqRes>> entity = new HttpEntity<List<GenericCheckerReqRes>>(genericCheckerReqResList, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, List.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public List<GenericCheckerReqRes> sendToCheckerREPO(List<GenericCheckerReqRes> genericCheckerReqResList, Long userId) throws ScoringException {
		String url = scoringBaseUrl.concat(SEND_TO_CHECKER_REPO + "/" + userId);
		logger.info("Enter in  sendToChecker(){} =============== URL  ==> "+url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<GenericCheckerReqRes>> entity = new HttpEntity<List<GenericCheckerReqRes>>(genericCheckerReqResList, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, List.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}


	public ScoringResponse createJob(MCLRReqRes mclrReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(CREATE_JOB);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MCLRReqRes> entity = new HttpEntity<>(mclrReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse createJobForRepo(REPOReqRes repoReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(CREATE_JOB_FOR_REPO);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<REPOReqRes> entity = new HttpEntity<>(repoReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse getMCLRHistory(MCLRReqRes mclrReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_MCLR_HISTROY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MCLRReqRes> entity = new HttpEntity<>(mclrReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse getREPOHistory(REPOReqRes repoReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_REPO_HISTROY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<REPOReqRes> entity = new HttpEntity<>(repoReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse getLatestMCLRDetails(MCLRReqRes mclrReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_LATEST_MCLR);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MCLRReqRes> entity = new HttpEntity<>(mclrReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse getMCLRForChecker(MCLRReqRes mclrReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_MCLR_FOR_CHECKER);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MCLRReqRes> entity = new HttpEntity<>(mclrReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse getREPOForChecker(REPOReqRes repoReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_REPO_FOR_CHECKER);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<REPOReqRes> entity = new HttpEntity<>(repoReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse saveMCLR(MCLRReqRes mclrReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(SAVE_MCLR);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MCLRReqRes> entity = new HttpEntity<>(mclrReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse saveREPO(REPOReqRes repoReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(SAVE_REPO);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<REPOReqRes> entity = new HttpEntity<>(repoReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}

	public ScoringResponse getEffectiveMCLR(MCLRReqRes mclrReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_EFFECTIVE_MCLR);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MCLRReqRes> entity = new HttpEntity<>(mclrReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}


	public ScoringModelReqRes copyScoringModel(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(COPY_SCORING_MODEL);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ScoringModelReqRes inactivateScoringDetails(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(INACTIVATE_SCORING_DETAILS);
		logger.info("Enter in  inactivateScoringDetails(){} =============== URL ==> " + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	public ScoringModelReqRes getScoringHistoryDetails(ScoringModelReqRes scoringModelReqRes) throws ScoringException {
		String url = scoringBaseUrl.concat(GET_SCORING_REMOVED_HISTORY);
		logger.info("Enter in  getScoringHistoryDetails(){} =============== URL ==> " + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ScoringModelReqRes> entity = new HttpEntity<ScoringModelReqRes >(scoringModelReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, ScoringModelReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new ScoringException(SCORING_SERVICE_IS_NOT_AVAILABLE);
		}
	}
}
