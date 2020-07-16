package com.opl.retail.client.reports;

import com.opl.retail.api.model.loans.common.ReportRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class ReportsClient {

	private static final String GET_APPLICATION_PDF_RESPONSE = "/pdf/app/details";
	private static final String GET_JASPER_REPORT_PDF= "/adv/reports/getReport";

	private String reportsUrl;
	private RestTemplate restTemplate;

	public ReportsClient(String reportsUrl) {
		this.reportsUrl = reportsUrl;
		restTemplate = new RestTemplate();
	}

	public byte[] generatePDFFile(ReportRequest request) {
		String url = reportsUrl.concat(GET_APPLICATION_PDF_RESPONSE);

		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ReportRequest> entity = new HttpEntity<>(request, headers);

		return restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class).getBody();

	}
	
	public byte[] getReport(ReportRequest request) {
		String url = reportsUrl.concat(GET_JASPER_REPORT_PDF);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("req_auth", "true");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ReportRequest> entity = new HttpEntity<>(request, headers);
		
		return restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class).getBody();
		
	}
}
