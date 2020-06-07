package com.scw.devops.collector.vcs;

import org.gitlab.api.models.GitlabProject;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.collector.application.CollectorAutowiring;
import com.scw.devops.collector.domain.RepositoryType;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;

public class ProjectReaderFactory {

	private final ObjectMapper yamlObjectMapper;
	private final GitlabGateway fileReader;
	private final Logger logger;

	public ProjectReaderFactory( final CollectorAutowiring autowiring ) {
		this.yamlObjectMapper = autowiring.getYamlObjectMapper();
		this.fileReader = autowiring.getGitlabGateway();
		this.logger = autowiring.getLogger();
	}

	public ProjectReader getProjectReader(final RepositoryType repositoryType, final GitlabProject projectObject) {
		return new ProjectReader(yamlObjectMapper, fileReader, repositoryType, projectObject, logger);
	}
}
