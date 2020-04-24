package com.scw.devops.collector.config;

import static org.apache.commons.lang3.Validate.notBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EndpointConfiguration {

	private static final String URL_IS_MISSING = "Store URL is missing";

	private final String storeUrl;

	@Autowired
	public EndpointConfiguration( @Value( "${store.url}" ) final String storeUrl ) {
		notBlank( storeUrl, URL_IS_MISSING );
		this.storeUrl = storeUrl;
	}

	public String getBaseUrl() {
		return storeUrl;
	}

	@Bean
	public RestTemplate restTemplate( final RestTemplateBuilder builder ) {
		return builder.build();
	}
}
