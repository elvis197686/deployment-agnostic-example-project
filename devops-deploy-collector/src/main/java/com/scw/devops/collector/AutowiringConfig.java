package com.scw.devops.collector;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.collector.application.CollectorAutowiring;
import com.scw.devops.collector.config.GitlabConfiguration;
import com.scw.devops.collector.config.GitlabConnectionConfiguration;
import com.scw.devops.collector.service.collect.CollectionService;
import com.scw.devops.collector.service.ingest.IngestionService;
import com.scw.devops.collector.vcs.ProjectReaderFactory;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.store.DataStoreUpdaterImpl;
import com.scw.devops.contract.store.RestGateway;
import com.scw.devops.contract.store.update.DataStoreUpdater;

@Configuration
public class AutowiringConfig {

	@Autowired
	RestGateway restGateway;
	@Autowired
	Logger logger;
	@Autowired
	GitlabConfiguration gitlabConfig;
	@Autowired
	GitlabConnectionConfiguration gitlabConnectionConfig;
	@Autowired
	ObjectMapper yamlObjectMapper;

	@Bean
	public AccessCoordinator accessCoordinator() {
		return new AccessCoordinator( collectorAutowiring() );
	}

	private CollectorAutowiring collectorAutowiring() {
		AutowiringProviderImpl autowiring = new AutowiringProviderImpl( dataStoreUpdater(),
																		logger,
																		gitlabConfig,
																		gitlabConnectionConfig,
																		yamlObjectMapper );
		autowiring.fixupDependencies( new ProjectReaderFactory( autowiring ), new GitlabGateway( autowiring ) );
		autowiring.fixup( new CollectionService( autowiring ),
						  new IngestionService( autowiring ) );
		return autowiring;
	}

	@Bean
	public DataStoreUpdater dataStoreUpdater() {
		return new DataStoreUpdaterImpl( restGateway );
	}
}
