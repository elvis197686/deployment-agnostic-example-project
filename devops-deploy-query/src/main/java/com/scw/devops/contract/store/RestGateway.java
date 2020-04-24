package com.scw.devops.contract.store;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.scw.devops.query.config.EndpointConfiguration;

@Component
public class RestGateway {

	private final RestTemplate restTemplate;
	private final EndpointConfiguration endpointConfiguration;

	@Autowired
	public RestGateway( final RestTemplate restTemplate, final EndpointConfiguration endpointConfiguration ) {
		this.restTemplate = restTemplate;
		this.endpointConfiguration = endpointConfiguration;
	}

	@SuppressWarnings( "unchecked" )
	public <T, R> R doPost( final String rootUrl, final T command, final Class<R> clazz ) {
		ResponseEntity<? extends Object> response = restTemplate
			.postForEntity( endpointConfiguration.getBaseUrl() + rootUrl, command, clazz, new HashMap<>() );
		if ( response.getStatusCode().is2xxSuccessful() ) {
			// TODO - is there a better way to indicate no response is expected?
			if ( clazz.equals( Void.class ) ) {
				return null;
			}
			if ( clazz.isAssignableFrom( response.getBody().getClass() ) ) {
				return (R) response.getBody();
			}
			else {
				throw new RuntimeException( "Unexpected http response type from store: " + response.getBody().getClass() );
			}
		}
		throw new RuntimeException( "Bad http response code from store: " + response.getStatusCode() );
	}


}
