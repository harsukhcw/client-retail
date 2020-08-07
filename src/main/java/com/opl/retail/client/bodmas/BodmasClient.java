package com.opl.retail.client.bodmas;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.capitaworld.bodmas.domain.BodmasReqRes;
import com.capitaworld.bodmas.domain.CalculationReqRes;
import com.capitaworld.bodmas.domain.FolderMasterRequest;
import com.capitaworld.bodmas.domain.FormulaMasterRequest;
import com.capitaworld.bodmas.exception.BodmasException;
import com.capitaworld.bodmas.model.BodmasRequest;

public class BodmasClient {

	private static Logger logger = LoggerFactory.getLogger(BodmasClient.class);

	private String bodmasBaseUrl;
	private RestTemplate restTemplate;
	
	private static final String PING_BODMAS = "/ping";
	private static final String LIST_FOLDER_MASTER = "/list_folder_master";
	private static final String SAVE_OR_UPDATE_FOLDER_MASTER = "/save_or_update_folder_master";
	private static final String LIST_FORMULA_MASTER = "/list_formula_master";
	private static final String SAVE_OR_UPDATE_FORMULA_MASTER = "/save_or_update_formula_master";
	private static final String CALCULATE_FORMULA_LIST = "/calculate_formula_list";
	private static final String LIST_DROPDOWN_VALUE_REQUEST = "/listDropDownValueRequest";
	
	private static final String BODMAS_SERVICE_IS_NOT_AVAILABLE="Bodmas Service is not available";
	private static final String BODMAS_SERVICE_ERROR= "Bodmas Service Error = {}";
	private static final String REQ_AUTH = "req_auth";
	
	public BodmasClient(String bodmasBaseUrl) {
		this.bodmasBaseUrl = bodmasBaseUrl;
		restTemplate = new RestTemplate();
	}

	/**
	 * Testing Bodmas Service whether it is working or not.
	 * 
	 * @return
	 * @throws BodmasException
	 */
	public String ping() throws BodmasException {
		String url = bodmasBaseUrl.concat(PING_BODMAS);
		logger.info("Ping bodmas URL==>{}", url);
		try {
			return restTemplate.getForObject(url, String.class);
		} catch (Exception e) {
			logger.error(BODMAS_SERVICE_ERROR , e);
			throw new BodmasException(BODMAS_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	/**
	 * list folder master 
	 * @param req
	 * @return
	 * @throws BodmasException
	 */
	public BodmasReqRes listFolderMaster(FolderMasterRequest req) throws BodmasException {
		String url = bodmasBaseUrl.concat(LIST_FOLDER_MASTER);
		try {
			logger.info("folder list url ==>{}", url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FolderMasterRequest> entity = new HttpEntity<FolderMasterRequest>(req, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, BodmasReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(BODMAS_SERVICE_ERROR, e);
			throw new BodmasException(BODMAS_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	/**
	 * save folder master 
	 * @param req
	 * @return
	 * @throws BodmasException
	 */
	public BodmasReqRes saveOrUpdateFolderMaster(FolderMasterRequest req) throws BodmasException {
		String url = bodmasBaseUrl.concat(SAVE_OR_UPDATE_FOLDER_MASTER);
		try {
			logger.info("save folder url ==>{}" ,  url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FolderMasterRequest> entity = new HttpEntity<FolderMasterRequest>(req, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, BodmasReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(BODMAS_SERVICE_ERROR, e);
			throw new BodmasException(BODMAS_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	/**
	 * list formula master  
	 * @param req
	 * @return
	 * @throws BodmasException
	 */
	public BodmasReqRes listFormulaMaster(FormulaMasterRequest formulaMasterRequest) throws BodmasException {
		String url = bodmasBaseUrl.concat(LIST_FORMULA_MASTER);
		try {
			logger.info("List foumula url ==>{}" ,  url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FormulaMasterRequest> entity = new HttpEntity<FormulaMasterRequest>(formulaMasterRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, BodmasReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(BODMAS_SERVICE_ERROR, e);
			throw new BodmasException(BODMAS_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	/**
	 * Save Or Update Formula 
	 * @param req
	 * @return
	 * @throws BodmasException
	 */
	public BodmasReqRes saveOrUpdateFormulaMaster(FormulaMasterRequest formulaMasterRequest) throws BodmasException {
		String url = bodmasBaseUrl.concat(SAVE_OR_UPDATE_FORMULA_MASTER);
		try {
			logger.info("save foumula url ==>{}" ,  url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<FormulaMasterRequest> entity = new HttpEntity<FormulaMasterRequest>(formulaMasterRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, BodmasReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(BODMAS_SERVICE_ERROR, e);
			throw new BodmasException(BODMAS_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	
	/**
	 * Calulate formula list 
	 * @param req
	 * @return
	 * @throws BodmasException
	 */
	public BodmasReqRes calculateFormulaList(List<CalculationReqRes> calculationReqResList) throws BodmasException {
		String url = bodmasBaseUrl.concat(CALCULATE_FORMULA_LIST);
		try {
			logger.info("Calulate formula list  url ==>{}" ,  url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<CalculationReqRes>> entity = new HttpEntity<List<CalculationReqRes>>(calculationReqResList, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, BodmasReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(BODMAS_SERVICE_ERROR, e);
			throw new BodmasException(BODMAS_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	/**
	 * Calulate formula list
	 * @param req
	 * @return
	 * @throws BodmasException
	 */
	public BodmasReqRes calculateFormulaList(CalculationReqRes calculationReqResList) throws BodmasException {
		String url = bodmasBaseUrl.concat(CALCULATE_FORMULA_LIST);
		try {
			logger.info("Calulate formula list  url ==>{}" ,  url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<CalculationReqRes> entity = new HttpEntity<CalculationReqRes>(calculationReqResList, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, BodmasReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(BODMAS_SERVICE_ERROR, e);
			throw new BodmasException(BODMAS_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	/**
	 * Fetch DropDown list  
	 * @param req
	 * @return
	 * @throws BodmasException
	 */
	public BodmasReqRes listDropDownValueRequest(BodmasReqRes bodmasReqRes) throws BodmasException {
		String url = bodmasBaseUrl.concat(LIST_DROPDOWN_VALUE_REQUEST);
		try {
			logger.info("Fetch DropDown list url  ==>{}" ,  url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<BodmasReqRes> entity = new HttpEntity<BodmasReqRes>(bodmasReqRes, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, BodmasReqRes.class).getBody();
		} catch (Exception e) {
			logger.error(BODMAS_SERVICE_ERROR, e);
			throw new BodmasException(BODMAS_SERVICE_IS_NOT_AVAILABLE);
		}
	}
	
	
	public HttpEntity<BodmasRequest> getHeader(BodmasRequest BodmasRequest, String connentTye) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", connentTye);
		return new HttpEntity(BodmasRequest, headers);
	}
}