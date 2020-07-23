package com.opl.retail.client.oneform;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.exceptions.oneform.OneFormException;
import com.opl.retail.api.model.oneform.AmountCriteriaResponse;
import com.opl.retail.api.model.oneform.ITRStateRequest;
import com.opl.retail.api.model.oneform.IndustrySectorSubSectorTeaserRequest;
import com.opl.retail.api.model.oneform.IrrBySectorAndSubSector;
import com.opl.retail.api.model.oneform.MasterResponse;
import com.opl.retail.api.model.oneform.ODOPIndustryListResponse;
import com.opl.retail.api.model.oneform.OneFormResponse;
import com.opl.retail.api.utils.oneform.CommonUtils;

/**
 * @author Sanket
 *
 */
public class OneFormClient {

	private static final Logger logger = LoggerFactory.getLogger(OneFormClient.class.getName());
	private static final String REQ_AUTH = "req_auth";

	private static final String REQUEST_CITY_FROM_CITY = "/getCityByCityListId";
	private static final String REQUEST_CITY_FROM_STATE = "/getCityListByStateIdListId";
	private static final String REQUEST_COUNTRY_FROM_COUNTRY = "/getCountryByCountryListId";
	private static final String REQUEST_ESTABISHMENT_YEAR_BY_ID = "/getYearByYearId";
	private static final String REQUEST_INDUSTRY_BY_ID = "/getIndustryById";
	private static final String REQUEST_INDUSTRY_SECTOR_MAPPING = "/getIndustrySectorMappingBySectorId";
	private static final String REQUEST_SECTOR_BY_INDUSTRY_LIST = "/getSectorListByIndustryList";
	private static final String REQUEST_IRR_SECTOR_SUBSECTOR_MAPPING = "/getIrrBySectorAndSubSector";
	private static final String INDUSTRY_BY_MAPP_ID = "/getIndustrySecByMappingId";
	private static final String SUBSECTOR_NAME_BY_MAPP_ID = "/getSubSecNameByMappingId";
	private static final String REQUEST_RATING_BY_RATING_ID = "/getRatingOptionList/getRatingById";
	private static final String RATING_OPTION_BY_ID = "/getRatingById";
	private static final String REQUEST_SECTOR_BY_ID = "/getSectorById";
	private static final String REQUEST_STATE_FROM_COUNTRY = "/getStateListByCountryIdListId";
	private static final String STATELIST_FROM_STATELIST_ID = "/getStateByStateListId";	
	private static final String REQUEST_INDUSTRY_SECTOR_SUB_SECTOR = "/getIndustrySectorSubSector";
	private static final String REQUEST_BANK_BY_ID = "/banks/getOne";
	private static final String REQUEST_BANK_BY_STATUS = "/banks/getAll";
	private static final String REQUEST_BANK_BY_IDS = "/banks/getAllByIds";
	private static final String REQUEST_BANK_BY_CODE = "/banks/get_by_code";
	private static final String GET_STATE_CODE_BY_ID = "/get_code_by_id";
	private static final String GET_AMOUNT_CRITERIA = "/amount_criteria";
	private static final String CHECK_CITY = "/getCityList/checkCity";
	private static final String GET_STATE_BY_ITR_CODE = "/getStateList/getStateByITRCode/";
	private static final String GET_STATE_DETAIL_FOR_ITR = "/getStateList/getStateDetailsForITR";
	private static final String GET_LIST = "/getList";
	private static final String GET_ENVIRONMENT_BY_ID = "/getEnvironmentIdById";
	private static final String GET_CITY_STATE_COUNTRY_BY_ID = "/getCityStateCountryById";
	private static final String STATELIST_FROM_STATE_CODE = "/getStateByStateCode";
	private static final String GETMASTERTABLEDATA = "/getmastertabledata";
	private static final String GET_ODOP_INDUSTRY_LIST = "/master/getODOPIndustryList";
	private static final String GET_ODOP_ACTUAL_INDUSTRY = "/getODOPIndustry";
	private static final String GET_ECLGS_LOV= "/getEclgsLOV";
	private String oneFormBaseUrl;
	private RestTemplate restTemplate;

	public OneFormClient(String oneFormBaseUrl) {
		this.oneFormBaseUrl = oneFormBaseUrl;
		restTemplate = new RestTemplate();

	}

	public OneFormResponse getCityByCityListId(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_CITY_FROM_CITY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse checkCity(MasterResponse masterResponse) throws OneFormException {
		String url = oneFormBaseUrl.concat(CHECK_CITY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MasterResponse> entity = new HttpEntity<>(masterResponse, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getCityListByStateIdListId(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_CITY_FROM_STATE);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getCountryByCountryListId(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_COUNTRY_FROM_COUNTRY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getYearByYearId(Long... ids) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_ESTABISHMENT_YEAR_BY_ID);
		try {
			List<Long> id = Arrays.asList(ids);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getIndustryById(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_INDUSTRY_BY_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getIndustrySectorMappingBySectorId(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_INDUSTRY_SECTOR_MAPPING);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse getSectorListByIndustryList(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_SECTOR_BY_INDUSTRY_LIST);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse getIrrBySectorAndSubSector(IrrBySectorAndSubSector irrBySectorAndSubSector) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_IRR_SECTOR_SUBSECTOR_MAPPING);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<IrrBySectorAndSubSector> entity = new HttpEntity<>(irrBySectorAndSubSector, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse getIndustrySecByMappingId(Long id) throws OneFormException {
		String url = oneFormBaseUrl.concat(INDUSTRY_BY_MAPP_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<Long> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse getSubSecNameByMappingId(Long id) throws OneFormException {
		String url = oneFormBaseUrl.concat(SUBSECTOR_NAME_BY_MAPP_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<Long> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse subSectorNameByMappingId(Long id) throws OneFormException {
		String url = oneFormBaseUrl.concat(INDUSTRY_BY_MAPP_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<Long> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getRatingById(Long id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_RATING_BY_RATING_ID).concat("/" + id);
		try {
			logger.info(url);
			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<Long> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getRatingOptionById(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(RATING_OPTION_BY_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(REQ_AUTH, "true");
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getSectorById(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_SECTOR_BY_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getStateListByCountryIdListId(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_STATE_FROM_COUNTRY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getStateByStateListId(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(STATELIST_FROM_STATELIST_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getStateByStateCode(Long stateCode) throws OneFormException {
		String url = oneFormBaseUrl.concat(STATELIST_FROM_STATE_CODE);
		try {
			url = url.concat("/" + stateCode);
			logger.info(url);
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
//			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	
	public OneFormResponse getStateCodeByStateListId(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(GET_STATE_CODE_BY_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getIndustrySectorSubSector(IndustrySectorSubSectorTeaserRequest request) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_INDUSTRY_SECTOR_SUB_SECTOR);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<IndustrySectorSubSectorTeaserRequest> entity = new HttpEntity<>(
					request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getBankByIds(List<Long> id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_BANK_BY_IDS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getBankById(Long id) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_BANK_BY_ID);
		url = url.concat("/" + id);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
//			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getBankByStatus(Boolean isActive) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_BANK_BY_STATUS);
		url = url.concat("/" + isActive);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
//			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getBankByCode(String code) throws OneFormException {
		String url = oneFormBaseUrl.concat(REQUEST_BANK_BY_CODE);
		url = url.concat("/" + code);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
//			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public AmountCriteriaResponse getAmountCriteria(Long amount,Integer bankId) throws OneFormException {
		String url = oneFormBaseUrl.concat(GET_AMOUNT_CRITERIA);
		url = url.concat("/" + bankId);
		logger.info("url=============>" + url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(amount, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, AmountCriteriaResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse getStateByITRCode(String itrCode) {
		try {
			String url = oneFormBaseUrl.concat(GET_STATE_BY_ITR_CODE) + itrCode;
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<?> entity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			return null;
		}
	}
	
	public OneFormResponse getStateDetailForITR(ITRStateRequest request) throws OneFormException {
		String url = oneFormBaseUrl.concat(GET_STATE_DETAIL_FOR_ITR);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ITRStateRequest> entity = new HttpEntity<>(request, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse getList(String name) throws OneFormException {
		String url = oneFormBaseUrl.concat(GET_LIST);
		url = url.concat("/" + name);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<Long> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}

	public OneFormResponse getEnvironmentCategoryIdById(Long id) throws OneFormException {
		String url = oneFormBaseUrl.concat(GET_ENVIRONMENT_BY_ID);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Long> entity = new HttpEntity<>(id, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse getCityStateCountryById(Long cityId, Integer stateId,Integer countryId) throws OneFormException {
		String url = oneFormBaseUrl.concat(GET_CITY_STATE_COUNTRY_BY_ID + "/" + cityId + "/" + stateId + "/" + countryId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<Long>> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public String getMasterTableData(Long id, String tabName) throws OneFormException {
		StringBuilder url = new StringBuilder(oneFormBaseUrl.concat(GETMASTERTABLEDATA)+"/"+id+"/"+tabName);
		System.out.println(url);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			HttpEntity<Long> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url.toString(), HttpMethod.GET, entity, String.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	public OneFormResponse getODOPIndustryList(Long orgId) throws OneFormException {
		String url = oneFormBaseUrl.concat(GET_ODOP_INDUSTRY_LIST+ "/" +orgId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<List<ODOPIndustryListResponse>> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse getODOPIndustry(Integer id, Integer orgId) throws OneFormException {
		String url = oneFormBaseUrl.concat(GET_ODOP_ACTUAL_INDUSTRY+ "/" +id +"/"+orgId);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
	public OneFormResponse getEclgsLOVString(Long id, String type) throws OneFormException {
		String url = oneFormBaseUrl.concat(GET_ECLGS_LOV+ "/" +id +"/"+type);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(REQ_AUTH, "true");
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> entity = new HttpEntity<>(null, headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, OneFormResponse.class).getBody();
		} catch (Exception e) {
			logger.error(CommonUtils.EXCEPTION, e);
			throw new OneFormException(e.getMessage());
		}
	}
	
}
