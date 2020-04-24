package com.scw.devops.test.integration.config;

import static java.lang.Boolean.parseBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@Configuration
public class ApplicationConfigurationImpl implements com.scw.devops.collector.config.ApplicationConfiguration {

	private final boolean initialiseOnStartup;

	@Autowired
	public ApplicationConfigurationImpl(@Value("${initialise_on_startup:false}") final String initialiseOnStartupStr) {
		this.initialiseOnStartup = initialiseOnStartupStr == null ? false : parseBoolean(initialiseOnStartupStr);
	}

	public boolean runIngestionOnStartup() {
		return initialiseOnStartup;
	}

	@Bean
	@Scope("prototype")
	public Logger logger(final InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.registerModule(new Jdk8Module());
		return jsonObjectMapper;
	}

	@Bean
	@Scope("prototype")
	public ObjectMapper yamlObjectMapper() {
		return new ObjectMapper(new YAMLFactory());

	}

}
