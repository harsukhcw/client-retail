package com.opl.retail.client.oneform;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

 

/**
 * All Constant has a Enum
 * @author ankit.gupta
 *
 */
@Component
public class PushPullApiClient {
    
	private static final Logger logger = LoggerFactory.getLogger(PushPullApiClient.class.getName());
	//String type constant
	public static final String APPLICATION_ID = "applicationId";
	public static final String PROPOSAL_ID = "proposalId";
	public static final String FP_PRODUCT_ID = "fpProductId";
	public static final String ORG_ID = "orgId";
	
	//String type constant for real time push flags (flag similar as Proposal Master)
    public static final Long PAYMENT_GETWAY_IN_PRINCIPAL = 2l;
    public static final Long MATHCES_HOLD = 3l;
    public static final Long MATHCES_DECLINE = 4l ;
    public static final Long MATHCES_SANCTION = 5l ;
    public static final Long MATHCES_PARTIALLY_DISBURSEMENT = 11l ;
    public static final Long MATHCES_FILL_DISBURSEMENT = 13l;
    
    //Below constant only for API'S    
    public static final Long SCHEDULAR = 101l ;
    public static final Long POSTMAN_MANNUAL = 102l; 
    public static final Long USERS_BRANCH_TRANSFER = 103l;
    
    public static final Long ALLOW_TO_SANCTION_OTHER_BANK_VIA_REVERSE_API = 104l;
    public static final Long RESTRICT_TO_SANCTION_OTHER_BANK_VIA_REVERSE_API = 105l;
    
    //IN CASE OF MULITPLE SANCTION
    public static final Long ALLOW_TO_DISBURSED_OTHER_BANK_VIA_REVERSE_API = 106l;
    public static final Long RESTRICT_TO_DISBURSED_OTHER_BANK_VIA_REVERSE_API = 107l;
    
    private static final String PUSH_REAL_TIME_PROPOSAL_DETAIL = "/push_real_time_proposal_detail";
    private static final String PUSH_UPDATED_PROPOSAL_DETAIL = "/pushUpdatedProposalDetail";

    private String pushPullApiClientUrl;
    
    
    public String getPushPullApiClientUrl() {
		return pushPullApiClientUrl;
	}

	public void setPushPullApiClientUrl(String pushPullApiClientUrl) {
		this.pushPullApiClientUrl = pushPullApiClientUrl;
	}

	public PushPullApiClient(String url ) {
    	this.pushPullApiClientUrl = url;
    }
    
    public PushPullApiClient() {
    }

    @Async
    public Object pushRealTimeProposalDetail(Map<String, Object> body) {
    	// TODO Auto-generated method stub
    	return pushData(body, PUSH_REAL_TIME_PROPOSAL_DETAIL);
    }
    
    @Async
    public Object pushUpdateProposalDetail(Map<String, Object> body) {
		
		return pushData(body, PUSH_UPDATED_PROPOSAL_DETAIL);
	}

	private Object pushData(Map<String, Object> body , String baseUrl) {
		logger.info("Base url : " , baseUrl);
    	if(pushPullApiClientUrl == null || pushPullApiClientUrl.isEmpty()      ) {
    		return "Currecntly Off push realy time proposal";
    	}
        HttpHeaders headers = new HttpHeaders();
        headers.set("req_auth", "true");
        headers.setContentType(MediaType.APPLICATION_JSON) ;
        RestTemplate restTemplate = new RestTemplate();
        String url = pushPullApiClientUrl+baseUrl; 
        logger.info("Full url : " , url);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST,  httpEntity, String.class).getBody();
        
    }
   
}    
