package com.opl.retail.client.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.model.workflow.WorkflowRequest;
import com.opl.retail.api.model.workflow.WorkflowResponse;

public class WorkflowClient {

	private static Logger logger = LoggerFactory.getLogger(WorkflowClient.class);

	private static final String CREATE_JOB = "/create_job";
	private static final String UPDATE_JOB = "/update_job";
	private static final String GET_ACTIVE_STEP = "/get_active_step";

	private String baseUrl;

	private RestTemplate restTemplate;

	public WorkflowClient(String baseUrl) {
		this.baseUrl = baseUrl;
		restTemplate = new RestTemplate();
	}

	public WorkflowResponse createJob(WorkflowRequest workflowRequest) {
		try {
			String url = baseUrl.concat(CREATE_JOB);
			logger.info("Workflow CLient URL==>{}", url);
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(workflowRequest), WorkflowResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error("Error while Creating job from Client!");
			e.printStackTrace();
			return null;
		}
	}
	
	public WorkflowResponse updateJob(WorkflowRequest workflowRequest) {
		try {
			String url = baseUrl.concat(UPDATE_JOB);
			logger.info("Workflow CLient URL==>{}", url);
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(workflowRequest), WorkflowResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error("Error while Creating job from Client!");
			e.printStackTrace();
			return null;
		}
	}
	
	public WorkflowResponse getActiveStep(WorkflowRequest workflowRequest) {
		try {
			String url = baseUrl.concat(GET_ACTIVE_STEP);
			logger.info("Workflow CLient URL==>{}", url);
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(workflowRequest), WorkflowResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error("Error while Creating job from Client!");
			e.printStackTrace();
			return null;
		}
	}
	private HttpEntity<WorkflowRequest> setHttpHeader(WorkflowRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<WorkflowRequest>(request, headers);
	}
}
