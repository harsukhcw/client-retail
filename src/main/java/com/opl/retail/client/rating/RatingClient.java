package com.opl.retail.client.rating;

import com.opl.retail.api.exceptions.rating.RatingException;
import com.opl.retail.api.model.rating.IrrRequest;
import com.opl.retail.api.model.rating.RatingResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RatingClient {

	
	private static final String CALCULATE_IRR_RATING = "/calculate_irr_rating";
	private static final String GET_IRR_INDUSTRY = "/get_irr_industry";
	
	private String ratingBaseUrl;
	private RestTemplate restTemplate;

	public RatingClient(String ratingBaseUrl) {
		this.ratingBaseUrl = ratingBaseUrl;
		restTemplate = new RestTemplate();
	}
	
	
	public RatingResponse calculateIrrRating(IrrRequest irrRequest) throws RatingException {
		String url = ratingBaseUrl.concat(CALCULATE_IRR_RATING);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<IrrRequest> entity = new HttpEntity<IrrRequest>(irrRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, RatingResponse.class).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RatingException("Ratings Service is not available");
		}
	}
	
	
	public IrrRequest getIrrIndustry(IrrRequest irrRequest) throws RatingException {
		String url = ratingBaseUrl.concat(GET_IRR_INDUSTRY);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<IrrRequest> entity = new HttpEntity<IrrRequest>(irrRequest, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, IrrRequest.class).getBody();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RatingException("Ratings Service is not available");
		}
	}
	
}
