package com.opl.retail.client.workflow;

import java.util.List;

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
	private static final String CREATE_JOB_FOR_MASTERS = "/create_job_for_masters";
	private static final String UPDATE_JOB = "/update_job";
	private static final String GET_ACTIVE_STEP = "/get_active_step";
	private static final String GET_ACTIVE_STEP_FOR_MASTERS = "/get_active_step_for_masters";
	private static final String AUDIT_TRAIL = "/get_audit_trail";
	private static final String WORKFLOW_CLIENT_URL = "Workflow CLient URL==>{}";
	private static final String ERROR_WHILE_CREATING_JOB_FROM_CLIENT = "Error while Creating job from Client!";

	private String baseUrl;

	private RestTemplate restTemplate;

	public WorkflowClient(String baseUrl) {
		this.baseUrl = baseUrl;
		restTemplate = new RestTemplate();
	}

	/**
	 * To start or Initiate the Process of Workflow
	 * @param workflowRequest
	 * @return
	 */
	public WorkflowResponse createJob(WorkflowRequest workflowRequest) {
		try {
			String url = baseUrl.concat(CREATE_JOB);
			logger.info(WORKFLOW_CLIENT_URL, url);
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(workflowRequest), WorkflowResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(ERROR_WHILE_CREATING_JOB_FROM_CLIENT, e);
			return null;
		}
	}
	
	/**
	 * Creating Job Or Process For Masters Data
	 * @param workflowId - To Start Workflow
	 * @param actionId - Default Action 
	 * @return
	 */
	
	public WorkflowResponse createJobForMasters(Long workflowId,Long actionId,Long userId) {
		try {
			WorkflowRequest workflowRequest = new WorkflowRequest();
			workflowRequest.setActionId(actionId);
			workflowRequest.setWorkflowId(workflowId);
			workflowRequest.setUserId(userId);
			String url = baseUrl.concat(CREATE_JOB_FOR_MASTERS);
			logger.info(WORKFLOW_CLIENT_URL, url);
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(workflowRequest), WorkflowResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(ERROR_WHILE_CREATING_JOB_FROM_CLIENT, e);
			return null;
		}
	}
	
	/**
	 * Updating State from CUrrent to Next or Previous step
	 * @param workflowRequest
	 * @return
	 */
	public WorkflowResponse updateJob(WorkflowRequest workflowRequest) {
		try {
			String url = baseUrl.concat(UPDATE_JOB);
			logger.info(WORKFLOW_CLIENT_URL, url);
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(workflowRequest), WorkflowResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(ERROR_WHILE_CREATING_JOB_FROM_CLIENT, e);
			return null;
		}
	}
	
	/**
	 * Get Active Steps to Authorize Person
	 * @param workflowRequest
	 * @return
	 */
	
	public WorkflowResponse getActiveStep(WorkflowRequest workflowRequest) {
		try {
			String url = baseUrl.concat(GET_ACTIVE_STEP);
			logger.info(WORKFLOW_CLIENT_URL, url);
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(workflowRequest), WorkflowResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(ERROR_WHILE_CREATING_JOB_FROM_CLIENT, e);
			return null;
		}
	}
	
	public WorkflowResponse getActiveStepForMaster(Long jobId, List<Long> roleIds,Long userId) {
		try {
			WorkflowRequest workflowRequest = new WorkflowRequest();
			workflowRequest.setRoleIds(roleIds);
			workflowRequest.setJobId(jobId);
			workflowRequest.setUserId(userId);
			String url = baseUrl.concat(GET_ACTIVE_STEP_FOR_MASTERS);
			logger.info(WORKFLOW_CLIENT_URL, url);
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(workflowRequest), WorkflowResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(ERROR_WHILE_CREATING_JOB_FROM_CLIENT, e);
			return null;
		}
	}
	
	/**
	 * Getting AUdit Trail of whole Process
	 * 
	 * Note : Please Set UserId if You are using Client.
	 * @param workflowRequest
	 * @return
	 */
	public WorkflowResponse getAuditTrail(WorkflowRequest workflowRequest) {
		try {
			String url = baseUrl.concat(AUDIT_TRAIL);
			logger.info(WORKFLOW_CLIENT_URL, url);
			return restTemplate.exchange(url, HttpMethod.POST, setHttpHeader(workflowRequest), WorkflowResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error(ERROR_WHILE_CREATING_JOB_FROM_CLIENT, e);
			return null;
		}
	}
	
	private HttpEntity<WorkflowRequest> setHttpHeader(WorkflowRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(request, headers);
	}
}
