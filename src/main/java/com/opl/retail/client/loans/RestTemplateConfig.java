package com.opl.retail.client.loans;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;


// @ComponentScan(basePackages = {"com.capitaworld"})
// @Component
public class RestTemplateConfig {
	
	 // Determines the timeout in milliseconds until a connection is established.
    	private static final int CONNECT_TIMEOUT = 15000;
     
    // The timeout when requesting a connection from the connection manager.
    	private static final int REQUEST_TIMEOUT = 10000;
     
    // The timeout for waiting for data
    	private static final int SOCKET_TIMEOUT = 15000;
	
    	  @Bean
    	  public RestTemplate restTemplate(HttpClient httpClient) {
    	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    	    requestFactory.setConnectionRequestTimeout(REQUEST_TIMEOUT);
    	    requestFactory.setConnectTimeout(CONNECT_TIMEOUT);
    	    requestFactory.setReadTimeout(SOCKET_TIMEOUT);
    	    requestFactory.setHttpClient(httpClient);
    	    RestTemplate restTemplate = new RestTemplate(requestFactory);
    	    restTemplate.setErrorHandler(new RestTemplateCustomErrorHandler());
    	    return restTemplate;
    	  }
	    
	    @Bean
	    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
	      PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
	      result.setMaxTotal(20);
	      return result;
	    }
	    
	    @Bean
	    public RequestConfig requestConfig() {
	      RequestConfig result = RequestConfig.custom()
	        .setConnectionRequestTimeout(REQUEST_TIMEOUT)
	        .setConnectTimeout(CONNECT_TIMEOUT)
	        .setSocketTimeout(SOCKET_TIMEOUT)
	        .build();
	      return result;
	    }
	    
	    @Bean
	    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, RequestConfig config) {
	      CloseableHttpClient result = HttpClientBuilder
	        .create()
	        .setConnectionManager(poolingHttpClientConnectionManager)
	        .setDefaultRequestConfig(config)
	        .build();
	      return result;
	    }
	    
	    
	    
	    
}

@Component
class RestTemplateCustomErrorHandler extends IOException implements ResponseErrorHandler {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5054964538412865773L;

	RestTemplateCustomErrorHandler(){
		// Do nothing because of X and Y.
	}
	/**
	 * Delegates to {@link #hasError(HttpStatus)} with the response status code.
	 */
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return hasError(getHttpStatusCode(response));
	}

	private HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode;
		try {
			statusCode = response.getStatusCode();
		}
		catch (IllegalArgumentException ex) {
			throw new UnknownHttpStatusCodeException(response.getRawStatusCode(),
					response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
		}
		return statusCode;
	}

	/**
	 * Template method called from {@link #hasError(ClientHttpResponse)}.
	 * <p>The default implementation checks if the given status code is
	 * {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR CLIENT_ERROR}
	 * or {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR SERVER_ERROR}.
	 * Can be overridden in subclasses.
	 * @param statusCode the HTTP status code
	 * @return {@code true} if the response has an error; {@code false} otherwise
	 */
	protected boolean hasError(HttpStatus statusCode) {
		return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR ||
				statusCode.series() == HttpStatus.Series.SERVER_ERROR);
	}

	/**
	 * This default implementation throws a {@link HttpClientErrorException} if the response status code
	 * is {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR}, a {@link HttpServerErrorException}
	 * if it is {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR},
	 * and a {@link RestClientException} in other cases.
	 */
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode = getHttpStatusCode(response);
		switch (statusCode.series()) {
			case CLIENT_ERROR:
				throw new HttpClientErrorException(statusCode, response.getStatusText(),
						response.getHeaders(), getResponseBody(response), getCharset(response));
			case SERVER_ERROR:
				throw new HttpServerErrorException(statusCode, response.getStatusText(),
						response.getHeaders(), getResponseBody(response), getCharset(response));
			default:
				throw new RestClientException("Unknown status code [" + statusCode + "]");
		}
	}

	private byte[] getResponseBody(ClientHttpResponse response) {
		try {
			InputStream responseBody = response.getBody();
			if (responseBody != null) {
				return FileCopyUtils.copyToByteArray(responseBody);
			}
		}
		catch (IOException ex) {
			// ignore
		}
		return new byte[0];
	}

	private Charset getCharset(ClientHttpResponse response) {
		HttpHeaders headers = response.getHeaders();
		MediaType contentType = headers.getContentType();
		return contentType != null ? contentType.getCharset() : null;
	}

}
