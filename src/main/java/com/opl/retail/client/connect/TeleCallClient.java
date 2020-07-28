package com.opl.retail.client.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.exceptions.connect.ConnectException;
import com.opl.retail.api.model.connect.EmpMasterResponse;
import com.opl.retail.api.model.connect.TeleCallCenterRequest;
import com.opl.retail.api.model.connect.TeleCallCenterResponse;

public class TeleCallClient {

    private final Logger log = LoggerFactory.getLogger(TeleCallClient.class);

    private static final String GET_TELE_CALL_BY_ID = "/connect/getById";
    private static final String GET_TELE_CALL_BY_CREATED_DATE_RANGE = "/connect/getByCreatedDateRange";
    private static final String GET_TELE_CALL_BY_MODIFIED_DATE_RANGE = "/connect/getByModifiedDateRange";
    private static final String SAVE_TELE_CALL = "/connect/save";
    private static final String UPDATE_TELE_CALL = "/connect/update";
    private static final String GET_EMP_BY_TYPE = "/connect/getEmpByType";
    private static final String REQ_AUTH = "req_auth";
    private static final String EXCEPTION = "Exception";

    private String connectBaseUrl;
    private RestTemplate restTemplate;

    public TeleCallClient(String connectBaseUrl) {
        this.connectBaseUrl = connectBaseUrl;
        restTemplate = new RestTemplate();
    }

    public TeleCallCenterResponse getById(TeleCallCenterRequest teleCallCenterRequest) throws ConnectException {

        TeleCallCenterResponse response = null;
        String url = connectBaseUrl.concat(GET_TELE_CALL_BY_ID);
        log.info("Connect GET Tele call Audit by application id or userid ===>" + url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(teleCallCenterRequest, headers), TeleCallCenterResponse.class).getBody();
        } catch (Exception e) {
            log.error(EXCEPTION,e);
            throw new ConnectException("Connect Error While GET Tele call Audit Log");
        }

        return response;
    }

    public TeleCallCenterResponse getByCreatedDateRange(TeleCallCenterRequest teleCallCenterRequest) throws ConnectException {

        TeleCallCenterResponse response = null;
        String url = connectBaseUrl.concat(GET_TELE_CALL_BY_CREATED_DATE_RANGE);
        log.info("Connect GET Tele call Audit by created date range ===>" + url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(teleCallCenterRequest, headers), TeleCallCenterResponse.class).getBody();
        } catch (Exception e) {
            log.error(EXCEPTION,e);
            throw new ConnectException("Connect Error While GET Tele call Audit by created date range");
        }
        return response;
    }

    public TeleCallCenterResponse getByModifiedDateRange(TeleCallCenterRequest teleCallCenterRequest) throws ConnectException {

        TeleCallCenterResponse response = null;
        String url = connectBaseUrl.concat(GET_TELE_CALL_BY_MODIFIED_DATE_RANGE);
        log.info("Connect GET Tele call Audit by modified date range ===>" + url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(teleCallCenterRequest, headers), TeleCallCenterResponse.class).getBody();
        } catch (Exception e) {
            log.error(EXCEPTION,e);
            throw new ConnectException("Connect Error While GET Tele call Audit by modified date range");
        }
        return response;
    }

    public boolean saveTeleCall(TeleCallCenterRequest teleCallCenterRequest) throws ConnectException {

        boolean isSuccess=false;
        String url = connectBaseUrl.concat(SAVE_TELE_CALL);
        log.info("Connect Saving tele call data ===>" + url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(teleCallCenterRequest, headers), TeleCallCenterResponse.class).getBody();
            isSuccess = true;
        } catch (Exception e) {
            log.error(EXCEPTION,e);
            throw new ConnectException("Connect Error While Saving Tele call Audit");
        }
        return isSuccess;
    }

    public boolean updateTeleCall(TeleCallCenterRequest teleCallCenterRequest) throws ConnectException {

        boolean isSuccess = false;
        String url = connectBaseUrl.concat(UPDATE_TELE_CALL);
        log.info("Connect Updating tele call center ===>" + url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(teleCallCenterRequest, headers), TeleCallCenterResponse.class).getBody();
            isSuccess=true;
        } catch (Exception e) {
            log.error(EXCEPTION,e);
            throw new ConnectException("Connect Error Updating Tele call Audit.");
        }
        return isSuccess;
    }

    public EmpMasterResponse getEmpByType(Integer integer) throws ConnectException {
        EmpMasterResponse empMasterResponse;

        String url = connectBaseUrl.concat(GET_EMP_BY_TYPE);
        log.info("Connect Getting emp info by type ===>" + url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            empMasterResponse = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(integer, headers), EmpMasterResponse.class).getBody();
        } catch (Exception e) {
            log.error(EXCEPTION,e);
            throw new ConnectException("Connect Error getting emp info by type");
        }
        return empMasterResponse;
    }
}
