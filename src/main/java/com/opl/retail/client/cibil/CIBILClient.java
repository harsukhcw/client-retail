package com.opl.retail.client.cibil;


import com.opl.retail.api.exceptions.cibil.CibilException;
import com.opl.retail.api.model.cibil.CibilRequest;
import com.opl.retail.api.model.cibil.CibilResponse;
import com.opl.retail.api.model.cibil.CibilScoreLogRequest;
import com.opl.retail.api.utils.cibil.CibilUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class CIBILClient {

	private static final Logger logger = LoggerFactory.getLogger(CIBILClient.class);
	
	private static final String CIBIL_REPORT_MSME = "/msme/cibil_report";
//	private static final String CIBIL_REPORT_MFI = "/mfi/getReport";
	private static final String CIBIL_REPORT_MSME_INDIVIDUAL = "/msme/cibil_report_individual";
	private static final String CIBIL_SCORE = "/msme/get_cibil_score_client";
	private static final String CIBIL_DPD_SCORE = "/msme/get_dpd_years";
	private static final String CIBIL_MATCHES_RESULT = "/msme/matches_result";
	private static final String CIBIL_SCORING_RESULT = "/msme/scoring_result";
	private static final String CIBIL_INDIVIDUAL_DPD = "/msme/consumer/dpd";
	private static final String CIBIL_GET_DPD_FOR_NO_OF_MONTH = "/msme/get_all_dpd_new";
	private static final String CIBIL_GET_ALL_DIRECTOR_SCORE = "/msme/get_all_director_score_new";
	private static final String CIBIL_REPORT_DIRECTOR = "/msme/cibil_report_director";
	private static final String CIBIL_SCORE_BY_PAN = "/msme/get_cibil_score_by_pancard";
	private static final String CIBIL_MULTIPLE_PROVIDER_SCORE_BY_PAN_APPLICATION_ID = "/msme/get_multiple_provider_score_by_pan_application_id";
	private static final String GET_DIRECTOR_DETAILS = "/msme/get_director_details";
	private static final String GET_MSME_COMMERCIAL = "/msme/get_msme_commercial";
	private static final String GET_DIRECOTR_AVERAGE_SCORE = "/msme/get_dir_avg_score";
	private static final String CIBIL_GET_DPD_FOR_NO_OF_MONTH_SOFT_PING = "/get_all_dpd";
	private static final String CIBIL_REPORT_SIDBI_FLOW = "/get_cibil_report_sidbi_flow";
	private static final String APPLICATION_ID_MUST_NOT_BE_NULL = "applicationId Must not be null";
	private static final String PAN_MUST_NOT_BE_NULL = "Pan must not be null";
	private static final String CIBIL_GET_CMR_SCORE = "/msme/get_cmr_score";
	private static final String GET_SOFTPING_SCORES = "/get_softping_scores";
	private static final String CIBIL_CLIENT_URL = "CIBIL Client URL===>";

	private String cibilBaseUrl;
	private RestTemplate restTemplate;

	public CIBILClient(String cibilBaseUrl) {
		this.cibilBaseUrl = cibilBaseUrl;
		restTemplate = new RestTemplate();
	}

	/**
	 * Getting Cibil score for Company like Private Limited or Public Limited or Partnership.
	 * @param applicationId
	 * @param userId
	 * @return
	 * @throws CibilException
	 */
	
	public CibilResponse getCibilReport(Long applicationId, Long userId, Long orgId) throws CibilException {
		if (CibilUtils.isObjectListNull(applicationId)) {
			throw new NullPointerException("applicationId and UsesrId Must not be null while Getting Cibil score for MSME Report");
		}
		CibilRequest cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);
		cibilRequest.setUserId(userId);
		cibilRequest.setOrgId(orgId);
		String url = cibilBaseUrl.concat(CIBIL_REPORT_MSME);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}

	/**
	 * For Co-Origination
	 * @param applicationId
	 * @param userId
	 * @param dataInput
	 * @param isNbfcUser
	 * @return
	 * @throws CibilException
	 */
	public CibilResponse getCibilReport(Long applicationId, Long userId, Object dataInput,boolean isNbfcUser,Long orgId) throws CibilException {
		if (CibilUtils.isObjectListNull(applicationId)) {
			throw new NullPointerException("applicationId and UsesrId Must not be null while Getting Cibil score for MSME Report");
		}
		CibilRequest cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);
		cibilRequest.setUserId(userId);
		cibilRequest.setDataInput(dataInput);
		cibilRequest.setIsNbfcUser(isNbfcUser);
		cibilRequest.setOrgId(orgId);
		String url = cibilBaseUrl.concat(CIBIL_REPORT_MSME);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}

	/**
	 * Getting Cibil score for Company like MFI.
	 * @param applicationId
	 * @param userId
	 * @return
	 * @throws CibilException
	 */
	/*public CibilResponse getCibilReportMfi(Long applicationId,Long applicantId,Long userId,Long coApplicantId) throws CibilException {
		if (CibilUtils.isObjectListNull(applicationId)) {
			throw new NullPointerException("applicationId and UsesrId Must not be null while Getting Cibil score for MFI Report");
		}
		CibilRequest  cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);
		cibilRequest.setApplicantId(applicantId);
		cibilRequest.setCoApplicantId(coApplicantId);
		cibilRequest.setUserId(userId);
		String url = cibilBaseUrl.concat(CIBIL_REPORT_MFI);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}*/
	
	/**
	 * Getting Cibil Report for Individual Like Director or Owner
	 * @param applicationId
	 * @param userId
	 * @return
	 * @throws CibilException
	 */
	public CibilResponse getCibilReportIndividual(Long applicationId,Long userId) throws CibilException {
		if (CibilUtils.isObjectListNull(applicationId,userId)) {
			throw new NullPointerException("applicationId and UsesrId Must not be null while Getting Cibil score for MSME Report For Individual");
		}
		CibilRequest  cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);
		cibilRequest.setUserId(userId);
		String url = cibilBaseUrl.concat(CIBIL_REPORT_MSME_INDIVIDUAL);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}

	/**
	 * Generate Director Cibil Report Director
	 * @param applicationId
	 * @param directorId
	 * @return
	 * @throws CibilException
	 */
	public CibilResponse generateCibilReportDirector(Long applicationId,Long directorId,Long userId) throws CibilException {
		if (CibilUtils.isObjectListNull(applicationId,directorId)) {
			throw new NullPointerException("applicationId and directorId Must not be null while Getting Cibil score for MSME Report For Individual Director");
		}
		CibilRequest  cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);
		cibilRequest.setId(directorId);
		cibilRequest.setUserId(userId);
		String url = cibilBaseUrl.concat(CIBIL_REPORT_DIRECTOR);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	/**
	 * return cibil score of individual, if There is multiple Cibil score for Same application it will return Average of all Director.
	 * @param cibilRequest consume application id for which we will get Cibil Score
	 * @return
	 * @throws CibilException
	 */
	public CibilResponse getCibilScore(CibilRequest cibilRequest) throws CibilException {
		if (CibilUtils.isObjectNullOrEmpty(cibilRequest) && CibilUtils.isObjectNullOrEmpty(cibilRequest.getApplicationId())) {
			throw new NullPointerException("CibilRequest and ApplicationId Must not be null");
		}
		String url = cibilBaseUrl.concat(CIBIL_SCORE);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	/**
	 * Getting DPD years
	 * @param cibilRequest
	 * @return
	 * @throws CibilException
	 */
	public CibilResponse getDPDYears(Long applicationId) throws CibilException {
		if (CibilUtils.isObjectNullOrEmpty(applicationId)) {
			throw new NullPointerException(APPLICATION_ID_MUST_NOT_BE_NULL);
		}
		CibilRequest cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);

		String url = cibilBaseUrl.concat(CIBIL_DPD_SCORE);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	/**
	 * Check is any DPD for No of Given month in Parameter
	 * @param applicationId
	 * @param month Checking DPD
	 * @return
	 * @throws CibilException
	 */
	
	public CibilResponse getDPDLastXMonth(Long applicationId) throws CibilException {
		if (CibilUtils.isObjectNullOrEmpty(applicationId)) {
			throw new NullPointerException(APPLICATION_ID_MUST_NOT_BE_NULL);
		}
		CibilRequest cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);
		cibilRequest.setDpdDays(12);

		String url = cibilBaseUrl.concat(CIBIL_GET_DPD_FOR_NO_OF_MONTH);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	public Map<String, List<String>> getDPDLastXMonthForIndividual(CibilRequest cibilRequest) throws CibilException {
		if (CibilUtils.isObjectNullOrEmpty(cibilRequest) || CibilUtils.isObjectNullOrEmpty(cibilRequest.getApplicantId())) {
			throw new NullPointerException(APPLICATION_ID_MUST_NOT_BE_NULL);
		}
		cibilRequest.setDpdDays(12);
		String url = cibilBaseUrl.concat(CIBIL_INDIVIDUAL_DPD);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), Map.class).getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	/**
	 * Getting DPD by Pan No Soft Ping
	 * @param pan
	 * @return
	 * @throws CibilException
	 */
	public CibilResponse getDPDLastXMonth(Long applicationId,String pan) throws CibilException {
		if (CibilUtils.isObjectNullOrEmpty(applicationId)) {
			throw new NullPointerException(APPLICATION_ID_MUST_NOT_BE_NULL);
		}
		CibilRequest cibilRequest = new CibilRequest();
		cibilRequest.setPan(pan);
		cibilRequest.setApplicationId(applicationId);
		cibilRequest.setDpdDays(12);
		String url = cibilBaseUrl.concat(CIBIL_GET_DPD_FOR_NO_OF_MONTH_SOFT_PING);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	/**
	 * Getting Cibil Report by PAN from Soft Ping SIdbi Flow
	 * @param pan
	 * @return
	 * @throws CibilException
	 */
	public CibilResponse getCIBILReportSidbiFlow(String pan) throws CibilException {
		if (CibilUtils.isObjectNullOrEmpty(pan)) {
			throw new NullPointerException("pan Must not be null");
		}
		CibilRequest cibilRequest = new CibilRequest();
		cibilRequest.setPan(pan);
		String url = cibilBaseUrl.concat(CIBIL_REPORT_SIDBI_FLOW);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	
	/**
	 * Get Score of All the Director
	 * @param applicationId
	 * @return
	 * @throws CibilException
	 */
	public CibilResponse getAllDirectorScore(Long applicationId) throws CibilException {
		if (CibilUtils.isObjectNullOrEmpty(applicationId)) {
			throw new NullPointerException(APPLICATION_ID_MUST_NOT_BE_NULL);
		}
		CibilRequest cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);

		String url = cibilBaseUrl.concat(CIBIL_GET_ALL_DIRECTOR_SCORE);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	
	public List<CibilScoreLogRequest> getSoftpingScores(Long applicationId, String pan) throws CibilException {
		if (CibilUtils.isObjectNullOrEmpty(applicationId) || CibilUtils.isObjectNullOrEmpty(pan)) {
			throw new NullPointerException(APPLICATION_ID_MUST_NOT_BE_NULL);
		}
		if (CibilUtils.isObjectNullOrEmpty(pan)) {
			throw new NullPointerException(PAN_MUST_NOT_BE_NULL);
		}
		CibilRequest cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);
		cibilRequest.setPan(pan);
		String url = cibilBaseUrl.concat(GET_SOFTPING_SCORES);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), new ParameterizedTypeReference<List<CibilScoreLogRequest>>() {
            }).getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	public CibilScoreLogRequest getCibilScoreByPanCard(CibilRequest cibilRequest) throws CibilException {
		logger.info("CIBILCLient ----------------------------------------------------ENTER IN GET CIBIL SCORE BY PAN ");
		String url = cibilBaseUrl.concat(CIBIL_SCORE_BY_PAN);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilScoreLogRequest.class).getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	public List<CibilScoreLogRequest> getMultipleProviderScoreByApplicationIdAndPan(CibilRequest cibilRequest) throws CibilException {
		logger.info("CIBILCLient ----------------------------------------------------ENTER IN getMultipleProviderScoreByApplicationIdAndPan ");
		String url = cibilBaseUrl.concat(CIBIL_MULTIPLE_PROVIDER_SCORE_BY_PAN_APPLICATION_ID);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), new ParameterizedTypeReference<List<CibilScoreLogRequest>>() {
            }).getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}

	private HttpEntity<CibilRequest> getHttpHeader(CibilRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<CibilRequest>(request, headers);
	}
	
	public CibilResponse getDirectorDetails(CibilRequest cibilRequest) throws CibilException {
		logger.info("CIBILCLient ----------------------------------------------------ENTER IN GET_DIRECTOR_DETAILS BY PAN ");
		String url = cibilBaseUrl.concat(GET_DIRECTOR_DETAILS);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	
	public String getMsmeCommercial(CibilRequest cibilRequest) throws CibilException {
		logger.info("CIBILCLient ----------------------------------------------------ENTER IN GET_MSME_COMMERCIAL BY PAN ");
		String url = cibilBaseUrl.concat(GET_MSME_COMMERCIAL);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), String.class).getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	public CibilResponse getDirectorAverageScore(Long applicationId) throws CibilException {
		logger.info("CIBILCLient ----------------------------------------------------ENTER IN getDirectorAverageScore");
		if(applicationId == null) {
			throw new NullPointerException("Application Id Must not NULL");
		}
		CibilRequest cibilRequest = new CibilRequest();
		cibilRequest.setApplicationId(applicationId);
		String url = cibilBaseUrl.concat(GET_DIRECOTR_AVERAGE_SCORE);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION+e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	public String getCMRScore(Long applicationId) throws CibilException {
		logger.info("CIBILCLient ----------------------------------------------------ENTER IN getCMRScore");
		if(applicationId == null) {
			throw new NullPointerException("Application Id Must not NULL");
		}
		String url = cibilBaseUrl.concat(CIBIL_GET_CMR_SCORE).concat("/" + applicationId);
		logger.info(CIBIL_CLIENT_URL + url);
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		HttpEntity<Long> entity = new HttpEntity<>(null, headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
	}
	
	public CibilResponse getMatchesResult(CibilRequest cibilRequest) throws CibilException {
		String url = cibilBaseUrl.concat(CIBIL_MATCHES_RESULT);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION + e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
	
	public CibilResponse getScoringResult(CibilRequest cibilRequest) throws CibilException {
		String url = cibilBaseUrl.concat(CIBIL_SCORING_RESULT);
		logger.info(CIBIL_CLIENT_URL + url);
		try {
			return restTemplate.exchange(url, HttpMethod.POST, getHttpHeader(cibilRequest), CibilResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(CibilUtils.EXCEPTION + e.getMessage(), e);
			throw new CibilException(e.getMessage());
		}
	}
}
