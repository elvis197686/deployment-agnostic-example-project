package com.scw.devops.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.collector.application.autowiring.CollectorAutowiring;
import com.scw.devops.collector.config.GitlabConfiguration;
import com.scw.devops.collector.config.GitlabConnectionConfiguration;
import com.scw.devops.collector.service.collect.CollectionService;
import com.scw.devops.collector.service.ingest.IngestionService;
import com.scw.devops.collector.vcs.ProjectReaderFactory;
import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.query.application.autowiring.QueryAutowiring;
import com.scw.devops.query.service.ProductResolver;
import com.scw.devops.store.application.StoreAutowiring;
import com.scw.devops.store.service.DataStoreQueryService;
import com.scw.devops.store.service.DataStoreUpdateService;
import com.scw.devops.store.state.DataStore;
import com.scw.devops.test.integration.config.GitlabConfigurationImpl;
import com.scw.devops.test.integration.config.ResourceFolderGateway;
import com.scw.devops.test.integration.contract.store.DataStoreReaderImpl;
import com.scw.devops.test.integration.contract.store.DataStoreUpdateImpl;

public class AutowiringProviderImpl implements StoreAutowiring, QueryAutowiring, CollectorAutowiring {

	private static AutowiringProviderImpl providerInstance = new AutowiringProviderImpl();

	// Static method used by all autowiring
	public static synchronized AutowiringProviderImpl getProvider( final Class<AutowiringProviderImpl> clazz ) {
		return providerInstance;
	}

	private DataStore dataStore = new DataStore();
	private DataStoreUpdateService dataStoreUpdateService;
	private DataStoreQueryService dataStoreQueryService;
	private DataStoreUpdateImpl dataStoreUpdater;
	private DataStoreReaderImpl dataStoreReader;
	private ProductResolver productResolver;
	private ProjectReaderFactory projectReaderFactory;
	private ObjectMapper yamlObjectMapper;
	private GitlabConfigurationImpl gitlabConfiguration;
	private GitlabConnectionConfiguration gitlabConnectionConfiguration;
	private ResourceFolderGateway resourceFolderGateway;
	private CollectionService collectionService;
	private IngestionService ingestionService;
	private AccessCoordinator accessCoordinator;

	public AutowiringProviderImpl() {
		// Must all be done in order
		this.dataStoreUpdateService = new DataStoreUpdateService( this );
		this.dataStoreUpdater = new DataStoreUpdateImpl( dataStoreUpdateService );
		this.dataStoreQueryService = new DataStoreQueryService( this );
		this.dataStoreReader = new DataStoreReaderImpl( dataStoreQueryService );
		this.productResolver = new ProductResolver( this );
		this.gitlabConfiguration = new GitlabConfigurationImpl();
		this.gitlabConnectionConfiguration = new DummyGitlabConnectionConfiguration();
		this.yamlObjectMapper = new ObjectMapper( new YAMLFactory() );
		this.resourceFolderGateway = new ResourceFolderGateway( this );
		this.projectReaderFactory = new ProjectReaderFactory( this );
		this.collectionService = new CollectionService( this );
		this.ingestionService = new IngestionService( this );
		this.accessCoordinator = new AccessCoordinator( this );
	}

	@Override
	public DataStore getDataStore() {
		return dataStore;
	}

	@Override
	public ResourceFolderGateway getGitlabGateway() {
		return resourceFolderGateway;
	}

	@Override
	public ProjectReaderFactory getProjectReaderFactory() {
		return projectReaderFactory;
	}

	@Override
	public GitlabConfiguration getGitlabConfiguration() {
		return gitlabConfiguration;
	}

	@Override
	public Logger getLogger() {
		// TODO Need prototype for this?!
		return LoggerFactory.getLogger( this.getClass() );
	}

	@Override
	public DataStoreUpdater getDataStoreUpdater() {
		return dataStoreUpdater;
	}

	@Override
	public GitlabConnectionConfiguration getGitlabConnectionConfiguration() {
		// Only used to include URL in definitions
		return gitlabConnectionConfiguration;
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

	@Override
	public DataStoreReader getReader() {
		return dataStoreReader;
	}

	@Override
	public ProductResolver getProductResolver() {
		return productResolver;
	}

	@Override
	public AccessCoordinator getAccessCoordinator() {
		return accessCoordinator;
	}

}
