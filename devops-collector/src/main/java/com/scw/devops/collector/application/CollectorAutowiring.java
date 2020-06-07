package com.scw.devops.collector.application;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.collector.config.GitlabConfiguration;
import com.scw.devops.collector.config.GitlabConnectionConfiguration;
import com.scw.devops.collector.service.collect.CollectionService;
import com.scw.devops.collector.service.ingest.IngestionService;
import com.scw.devops.collector.vcs.ProjectReaderFactory;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.store.update.DataStoreUpdater;

public interface CollectorAutowiring {

	public GitlabGateway getGitlabGateway();

	public ProjectReaderFactory getProjectReaderFactory();

	public GitlabConfiguration getGitlabConfiguration();

	public Logger getLogger();

	public DataStoreUpdater getDataStoreUpdater();

	public GitlabConnectionConfiguration getGitlabConnectionConfiguration();

	public ObjectMapper getYamlObjectMapper();

	public CollectionService getCollectionService();

	public IngestionService getIngestionService();

}
