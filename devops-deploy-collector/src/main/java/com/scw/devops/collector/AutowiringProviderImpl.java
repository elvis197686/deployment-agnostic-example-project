package com.scw.devops.collector;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.collector.application.autowiring.CollectorAutowiring;
import com.scw.devops.collector.config.GitlabConfiguration;
import com.scw.devops.collector.config.GitlabConnectionConfiguration;
import com.scw.devops.collector.service.collect.CollectionService;
import com.scw.devops.collector.service.ingest.IngestionService;
import com.scw.devops.collector.vcs.ProjectReaderFactory;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.store.update.DataStoreUpdater;

public class AutowiringProviderImpl implements CollectorAutowiring {

	private final DataStoreUpdater dataStoreUpdater;
	private final Logger logger;
	private final GitlabConfiguration gitlabConfig;
	private final GitlabConnectionConfiguration gitlabConnectionConfig;
	private final ObjectMapper yamlObjectMapper;
	// Cannot make final as it refers to this implementation TODO - Inject reader directly instead of this indirection?
	private AccessCoordinator accessCoordinator;
	private ProjectReaderFactory projectReaderFactory;
	private GitlabGateway gitlabGateway;
	private CollectionService collectionService;
	private IngestionService ingestionService;

	public AutowiringProviderImpl( final DataStoreUpdater dataStoreUpdater, final Logger logger, final GitlabConfiguration gitlabConfig,
								   final GitlabConnectionConfiguration gitlabConnectionConfig, final ObjectMapper yamlObjectMapper ) {
		this.dataStoreUpdater = dataStoreUpdater;
		this.logger = logger;
		this.gitlabConfig = gitlabConfig;
		this.gitlabConnectionConfig = gitlabConnectionConfig;
		this.yamlObjectMapper = yamlObjectMapper;
	}

	public void fixupDependencies( final ProjectReaderFactory projectReaderFactory,
								   final GitlabGateway gitlabGateway ) {
		this.projectReaderFactory = projectReaderFactory;
		this.gitlabGateway = gitlabGateway;
	}

	public void fixup( final CollectionService collectionService, final IngestionService ingestionService ) {
		this.collectionService = collectionService;
		this.ingestionService = ingestionService;
	}

	public void fixupStage2( final AccessCoordinator accessCoordinator ) {
		this.accessCoordinator = accessCoordinator;
	}

	@Override
	public AccessCoordinator getAccessCoordinator() {
		return accessCoordinator;
	}

	@Override
	public GitlabGateway getGitlabGateway() {
		return gitlabGateway;
	}

	@Override
	public ProjectReaderFactory getProjectReaderFactory() {
		return projectReaderFactory;
	}

	@Override
	public GitlabConfiguration getGitlabConfiguration() {
		return gitlabConfig;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public DataStoreUpdater getDataStoreUpdater() {
		return dataStoreUpdater;
	}

	@Override
	public GitlabConnectionConfiguration getGitlabConnectionConfiguration() {
		return gitlabConnectionConfig;
	}

	@Override
	public ObjectMapper getYamlObjectMapper() {
		return yamlObjectMapper;
	}

	@Override
	public CollectionService getCollectionService() {
		return collectionService;
	}

	@Override
	public IngestionService getIngestionService() {
		return ingestionService;
	}

}
