package com.opl.retail.client.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.retail.api.exceptions.notification.NotificationException;
import com.opl.retail.api.model.notification.NotificationRequest;
import com.opl.retail.api.model.notification.NotificationResponse;


/**
 * @author Sanket
 *
 */

public class NotificationClient{
	private final Logger logger = LoggerFactory.getLogger(NotificationClient.class);
	private static final String REQ_AUTH = "req_auth";
	private static final String CLIENTID = "?clientId=";
	private static final String NOTIFICATION_SERVICE_IS_NOT_AVAILABLE = "Notification Service is not available";
	private static final String NOTIFICATION_SERVICE_EXCEPTION = "Notification Service Exception";
	private static final String MOBILE_NOTIFICATION_SERVICE_EXCEPTION = "Mobile,Notification Service Exception";
	private static final String SEND_EMAIL_NOTIFICATION = "/send";
	private static final String GET_ALL_UNREAD_NOTIFICATION = "/getAllUnreadNotification";
	private static final String GET_ALL_NOTIFICATION = "/getAllNotification";
	private static final String SET_IS_READ = "/setIsReadNotification";
	private static final String GET_ALL_UNREAD_NOTIFICATION_BY_APPLICATIONID = "/getAllUnreadNotificationByAppId";
	private static final String GET_ALL_NOTIFICATION_BY_APPLICATIONID = "/getAllNotificationByAppId"; 
	private static final String GET_ALL_UNREAD_NOTIFICATION_BY_PRODUCTID = "/getAllUnreadNotificationByProdId";
	private static final String GET_ALL_NOTIFICATION_BY_PRODUCTID = "/getAllNotificationByProdId";
	private static final String GET_ALL_SP_NOTIFICATION_BY_PRODUCTID = "/getAllSPNotificationByProdId"; 
	private static final String GET_ALL_SP_NOTIFICATION_BY_APPID = "/getAllSPNotificationByAppId"; 
	
	
	private static final String GET_ALL_RECENT_VIEW_NOTIFICATION_BY_PRODID = "/getAllRecentViewNotificationByProdId";
	private static final String GET_ALL_RECENT_VIEW_NOTIFICATION_BY_APPID = "/getAllRecentViewNotificationByAppId";
	
	private static final String GET_ALL_LATEST_RECENT_VIEW_NOTIFICATION_BY_APPID = "/getAllLatestRecentViewNotificationByAppId";
	private static final String GET_ALL_LATEST_RECENT_VIEW_NOTIFICATION_BY_PRODID = "/getAllLatestRecentViewNotificationByProdId";
	
	private static final String MOBILE_GET_ALL_UNREAD_NOTIFICATION = "/getAllUnreadNotificationForMobile";
	private static final String MOBILE_SET_IS_READ = "/setIsReadNotificationForMobile";
	
	private static final String GET_ALL_NOTIFICATION_COUNT ="/getNotificationCount";
	
	private static final String GET_SP_NOTIFICATION_COUNT = "/getSPNotificationCount";
	private static final String GET_LOGO_URL = "/logoURL";
	private static final String GET_LAST_SENT_EMAIL = "/getLastEmailByToEmailAndSubject";
	private static final String config = "/config/getSchedulerDetails";
	
	 private String notificationBaseUrl;
	 private RestTemplate restTemplate;
	 
	 public NotificationClient(String notificationBaseUrl) {
	  this.notificationBaseUrl = notificationBaseUrl;
	  restTemplate = new RestTemplate();
	  
	 }


	 public NotificationResponse send(NotificationRequest request) throws NotificationException{
	  String url = notificationBaseUrl.concat(SEND_EMAIL_NOTIFICATION);
	  try {
		  HttpHeaders headers = new HttpHeaders();
		  headers.set(REQ_AUTH, "true");
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
		  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
	  } catch (Exception e) {
	  		logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
	   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
	  }
	 }
	 
	 public NotificationResponse getAllUnreadNotification(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_UNREAD_NOTIFICATION);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
//			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.GET, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllNotification(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_NOTIFICATION);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
//			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.GET, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }

	 public NotificationResponse setIsReadNotification(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(SET_IS_READ);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllUnreadNotificationByAppId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_UNREAD_NOTIFICATION_BY_APPLICATIONID).concat("/"+request.getApplicationId()+CLIENTID+request.getClientRefId());
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
//			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.GET, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllNotificationByAppId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_NOTIFICATION_BY_APPLICATIONID).concat("/"+request.getApplicationId()+CLIENTID+request.getClientRefId());
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllUnreadNotificationByProdId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_UNREAD_NOTIFICATION_BY_PRODUCTID).concat("/"+request.getProductId()+CLIENTID+request.getClientRefId());
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
/*			  headers.setContentType(MediaType.APPLICATION_JSON);*/
			  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.GET, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllNotificationByProdId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_NOTIFICATION_BY_PRODUCTID).concat("/"+request.getProductId()+CLIENTID+request.getClientRefId());
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }

	 public NotificationResponse getAllRecentViewNotificationByProdId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_RECENT_VIEW_NOTIFICATION_BY_PRODID);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllLatestRecentViewNotificationByProdId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_LATEST_RECENT_VIEW_NOTIFICATION_BY_PRODID);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllRecentViewNotificationByAppId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_RECENT_VIEW_NOTIFICATION_BY_APPID);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllLatestRecentViewNotificationByAppId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_LATEST_RECENT_VIEW_NOTIFICATION_BY_APPID);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllUnreadNotificationForMobile(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(MOBILE_GET_ALL_UNREAD_NOTIFICATION);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MOBILE_NOTIFICATION_SERVICE_EXCEPTION, e);
			  throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse setIsReadNotoficationForMobile(NotificationRequest notificationRequest) throws NotificationException{
		  String url = notificationBaseUrl.concat(MOBILE_SET_IS_READ);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(notificationRequest, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MOBILE_NOTIFICATION_SERVICE_EXCEPTION, e);
			  throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getNotificationCount(NotificationRequest notificationRequest) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_NOTIFICATION_COUNT);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(notificationRequest, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MOBILE_NOTIFICATION_SERVICE_EXCEPTION, e);
			  throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getSPNotificationCount(NotificationRequest notificationRequest) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_SP_NOTIFICATION_COUNT);
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(notificationRequest, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(MOBILE_NOTIFICATION_SERVICE_EXCEPTION, e);
			  throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 
	 public NotificationResponse getAllSPNotificationByProdId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_SP_NOTIFICATION_BY_PRODUCTID).concat("/"+request.getProductId()+CLIENTID+request.getClientRefId());
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 
	 public NotificationResponse getAllSPNotificationByAppId(NotificationRequest request) throws NotificationException{
		  String url = notificationBaseUrl.concat(GET_ALL_SP_NOTIFICATION_BY_APPID).concat("/"+request.getApplicationId()+CLIENTID+request.getClientRefId());
		  try {
			  HttpHeaders headers = new HttpHeaders();
			  headers.set(REQ_AUTH, "true");
			  headers.setContentType(MediaType.APPLICATION_JSON);
			  HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(request, headers);
			  return restTemplate.exchange(url, HttpMethod.POST, entity, NotificationResponse.class).getBody();
		  } catch (Exception e) {
		   logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		  }
		 }
	 public String getLogoUrl() throws NotificationException {
		 String url= notificationBaseUrl.concat(GET_LOGO_URL);
		try {
			HttpHeaders header=new HttpHeaders();
			header.set(REQ_AUTH, "true");
			header.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(null, header);
			 return restTemplate.exchange(url, HttpMethod.GET,entity,String.class).getBody();
		} catch (Exception e) {
			logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		}
	 }
	 
	 public NotificationResponse getLastSentEmailByToEmailAndSubject(NotificationRequest request) throws NotificationException {
		 String url= notificationBaseUrl.concat(GET_LAST_SENT_EMAIL);
		try {
			HttpHeaders header=new HttpHeaders();
			header.set(REQ_AUTH, "true");
			header.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(request, header);
			 return restTemplate.exchange(url, HttpMethod.POST,entity,NotificationResponse.class).getBody();
		} catch (Exception e) {
			logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
		   throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		}
	 }
	 public NotificationResponse checkConfig(Long schedulerId) throws NotificationException {
		 String url= notificationBaseUrl.concat(config).concat("/"+schedulerId);
		 try {
			 HttpHeaders header=new HttpHeaders();
			 header.set(REQ_AUTH, "true");
			 header.setContentType(MediaType.APPLICATION_JSON);
			 HttpEntity<NotificationRequest> entity = new HttpEntity<NotificationRequest>(null, header);
			 return restTemplate.exchange(url, HttpMethod.GET,entity,NotificationResponse.class).getBody();
		 } catch (Exception e) {
			 logger.error(NOTIFICATION_SERVICE_EXCEPTION, e);
			 throw new NotificationException(NOTIFICATION_SERVICE_IS_NOT_AVAILABLE);
		 }
	 }
	 
}