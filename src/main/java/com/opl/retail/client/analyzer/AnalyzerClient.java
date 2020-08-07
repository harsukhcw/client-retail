package com.opl.retail.client.analyzer;

import com.opl.retail.api.exceptions.analyzer.AnalyzerException;
import com.opl.retail.api.model.analyzer.StatementUploadRequest;
import com.opl.retail.api.model.analyzer.common.AnalyzerMobileResponse;
import com.opl.retail.api.model.analyzer.common.AnalyzerResponse;
import com.opl.retail.api.model.analyzer.common.ReportRequest;
import com.opl.retail.api.model.analyzer.common.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


public class AnalyzerClient {

	private static final Logger logger = LoggerFactory.getLogger(AnalyzerClient.class);

	private static final String EXCEPTION = "Exception :: ";

	private static final String GET_DETAILS_FROM_REPORT = "/common/getDetailsFromReport";
	private static final String GET_EXISTING_REPORT_DETAILS = "/common/getExistingReportDetails";
	private static final String GET_ORG_NAME_BY_APP_ID = "/common/getOrgNameByAppId";
	private static final String GET_DETAILS_FROM_REPORT_BY_DIRECTOR = "/common/getDetailsFromReportByDirector";
	private static final String GET_DETAILS_FROM_REPORT_FOR_CAM = "/common/getDetailsFromReportForCam";
	private static final String GET_DETAILS_FROM_REPORT_FOR_ADMIN = "/statement/getReportDetailsByAppId";
	private static final String GET_SALARY_DETAILS_FROM_REPORT_FOR_CAM = "/common/getSalaryDetailsFromReport";
	private static final String UPLOAD_STATEMENTS = "/common/uploadStatements";
	private static final String MOBILE_TEST = "/mobile/test";
	private static final String GET_DETAILS_FROM_REPORT_VIA_MOBILE = "/mobile/getDetailsFromReport";
	private static final String DEACTIVATE_OLD_REPORT = "/mobile/deActivateOldReport";
	private static final String GET_BANK_LIST = "/mobile/getBankList";
	private static final String SEND_NOTIFICATION = "/mobile/sendNotification";
	private static final String GET_NET_BANKING = "/mobile/get_net_banking";
	private static final String IS_REPORT_GENERATED = "/mobile/isReportGenerated";
	private static final String NET_BANKING_CALLBACK = "/mobile/net_banking_callback";
	private static final String REQ_AUTH = "req_auth";
	private static final String GET_DETAILS_CATEGORY_WISE = "/statement/getReportDetailCategoryWise";
	private static final String GET_BANK_RELATION_DATA = "/common/getBankRelationData";
	private static final String IS_BANK_STATEMENT_IS_UPDATED = "/common/isBankStatementIsUpdated";


	private String analyzerBaseUrl;
	private RestTemplate restTemplate;

	public AnalyzerClient(String analyzerBaseUrl) {
		this.analyzerBaseUrl = analyzerBaseUrl;
		restTemplate = new RestTemplate();

	}

	public AnalyzerResponse getDetailsFromReport(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_DETAILS_FROM_REPORT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerResponse.class).getBody();
		} catch (Exception e) {
			logger.error(" getDetailsFromReport "+e);
			throw new AnalyzerException(e);
		}
	}
	
	public String getOrgNameByAppId(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_ORG_NAME_BY_APP_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerResponse getDetailsFromReportByDirector(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_DETAILS_FROM_REPORT_BY_DIRECTOR);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerResponse getDetailsFromReportForCam(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_DETAILS_FROM_REPORT_FOR_CAM);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerResponse getDetailsFromReportForAdmin(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_DETAILS_FROM_REPORT_FOR_ADMIN);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	
	public AnalyzerResponse getSalaryDetailsFromReport(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_SALARY_DETAILS_FROM_REPORT_FOR_CAM);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	
	public AnalyzerResponse uploadStatement(StatementUploadRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(UPLOAD_STATEMENTS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			 //headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<StatementUploadRequest> entity = new HttpEntity<StatementUploadRequest>(reportRequest, headers);
			/*MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
			jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			restTemplate.getMessageConverters().add(jsonHttpMessageConverter);*/
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerMobileResponse test() throws AnalyzerException {
		String url = analyzerBaseUrl.concat(MOBILE_TEST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			 //headers.setContentType(MediaType.APPLICATION_JSON);
			
			
			return restTemplate.exchange(url, HttpMethod.GET, null, AnalyzerMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerMobileResponse getDetailsFromReportViaMobile(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_DETAILS_FROM_REPORT_VIA_MOBILE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerMobileResponse deActivateOldReport(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(DEACTIVATE_OLD_REPORT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerMobileResponse statementUploads(Statement statement) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(DEACTIVATE_OLD_REPORT);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Statement> entity = new HttpEntity<Statement>(statement, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerMobileResponse getBankList() throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_BANK_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity,AnalyzerMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerMobileResponse sendNotification(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(SEND_NOTIFICATION);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity,AnalyzerMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerMobileResponse getNetBanking(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_NET_BANKING);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity,AnalyzerMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerMobileResponse isReportGenerated(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(IS_REPORT_GENERATED);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity,AnalyzerMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerMobileResponse netBankingCallback(String reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(NET_BANKING_CALLBACK);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.TEXT_PLAIN);
			HttpEntity<String> entity = new HttpEntity<String>(reportRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity,AnalyzerMobileResponse.class).getBody();
		} catch (Exception e) {
			logger.error(EXCEPTION, e);
			throw new AnalyzerException(e);
		}
	}

	public AnalyzerResponse getExistingReportDetails(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_EXISTING_REPORT_DETAILS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerResponse.class).getBody();
		} catch (Exception e) {
			logger.error(" getDetailsFromReport "+e);
			throw new AnalyzerException(e);
		}
	}
	
	public AnalyzerResponse getDetailsByCategoryWise(ReportRequest reportRequest) throws AnalyzerException {
		String url = analyzerBaseUrl.concat(GET_DETAILS_CATEGORY_WISE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);
			
			return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerResponse.class).getBody();
		} catch (Exception e) {
			logger.error(" getDetailsCategoryWise "+e);
			throw new AnalyzerException(e);
		}
	}

    public AnalyzerResponse getBankRelationData(ReportRequest reportRequest) throws AnalyzerException {
        String url = analyzerBaseUrl.concat(GET_BANK_RELATION_DATA);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(REQ_AUTH, "true");
            HttpEntity<ReportRequest> entity = new HttpEntity<ReportRequest>(reportRequest, headers);

            return restTemplate.exchange(url, HttpMethod.POST, entity, AnalyzerResponse.class).getBody();
        } catch (Exception e) {
            logger.error(" getDetailsCategoryWise "+e);
            throw new AnalyzerException(e);
        }
    }

	public AnalyzerResponse isBankStatementIsUpdated(Long profileId, Long masterId) {
		String url;
		if(masterId == null){
			url = analyzerBaseUrl.concat(IS_BANK_STATEMENT_IS_UPDATED) + "/" + profileId;
		} else {
			url = analyzerBaseUrl.concat(IS_BANK_STATEMENT_IS_UPDATED) + "/" + profileId + "?masterId=" + masterId;
		}
		logger.info("Enter in isBankStatementIsUpdated()  ------------>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, AnalyzerResponse.class).getBody();
		} catch (Exception e) {
			logger.error("Throw Exception While checking bank statement ", e );
			return null;
		}
	}

}
