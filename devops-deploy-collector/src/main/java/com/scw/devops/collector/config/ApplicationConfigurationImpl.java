package com.scw.devops.collector.config;

import static java.lang.Boolean.parseBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@Configuration
public class ApplicationConfigurationImpl implements ApplicationConfiguration {

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
	public ObjectMapper yamlObjectMapper() {
		return new ObjectMapper(new YAMLFactory());

	}

}
