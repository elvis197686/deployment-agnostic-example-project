package com.scw.devops.collector.vcs;

import org.gitlab.api.models.GitlabProject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.collector.domain.RepositoryType;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;

@Component
public class ProjectReaderFactory {

	private final ObjectMapper yamlObjectMapper;
	private final GitlabGateway fileReader;
	private final Logger logger;

	@Autowired
	public ProjectReaderFactory(@Qualifier("yamlObjectMapper") final ObjectMapper yamlObjectMapper,
			final GitlabGateway fileReader, final Logger logger) {
		this.yamlObjectMapper = yamlObjectMapper;
		this.fileReader = fileReader;
		this.logger = logger;
	}

	public ProjectReader getProjectReader(final RepositoryType repositoryType, final GitlabProject projectObject) {
		return new ProjectReader(yamlObjectMapper, fileReader, repositoryType, projectObject, logger);
	}
}
