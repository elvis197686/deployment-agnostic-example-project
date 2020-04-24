package com.scw.devops.collector.vcs;

import java.util.Collection;
import java.util.function.Consumer;

import org.gitlab.api.models.GitlabProject;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.collector.domain.RepositoryType;
import com.scw.devops.collector.vcs.data.DeployYaml;
import com.scw.devops.collector.vcs.data.ProjectData;
import com.scw.devops.collector.vcs.data.RequirementsYaml;
import com.scw.devops.collector.vcs.data.ValuesYaml;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;

public class ProjectReader {

	private static final String DEPLOYMENT_METADATA_FILE = "deploy.yaml";
	private static final String PRODUCT_REQUIREMENTS_FILE = "requirements.yaml";
	private static final String PRODUCT_VALUES_FILE = "values.yaml";

	private final ObjectMapper yamlObjectMapper;
	private final GitlabGateway fileReader;
	private final RepositoryType repositoryType;
	private final GitlabProject project;
	private final Logger logger;

	public ProjectReader(final ObjectMapper yamlObjectMapper, final GitlabGateway fileReader,
			final RepositoryType repositoryType, final GitlabProject project, final Logger logger) {
		this.yamlObjectMapper = yamlObjectMapper;
		this.fileReader = fileReader;
		this.repositoryType = repositoryType;
		this.project = project;
		this.logger = logger;
	}

	public static boolean anyRelevantFilesInList(final RepositoryType repositoryType, final Collection<String> changedFiles) {
		return changedFiles.contains(DEPLOYMENT_METADATA_FILE) || changedFiles.contains(PRODUCT_REQUIREMENTS_FILE)
				|| changedFiles.contains(PRODUCT_VALUES_FILE);
	}

	public void collectFiles(final String projectName, final String branchOrTagName, final boolean isPreview,
			final Consumer<ProjectData> projectConsumer) {
		String errorContext = "rokit.yaml";
		ProjectData foundProjectData = null;
		try {
			DeployYaml rokitYamlContents = yamlObjectMapper
				.readValue( fileReader.getFileContents( project, branchOrTagName, DEPLOYMENT_METADATA_FILE ), DeployYaml.class );
			ValuesYaml valuesYamlContents = null;
			RequirementsYaml requirementsYamlContents = null;
			if (repositoryType.hasProductFiles()) {
				errorContext = "values.yaml";
				valuesYamlContents = yamlObjectMapper.readValue( fileReader.getFileContents( project, branchOrTagName, PRODUCT_VALUES_FILE ),
																 ValuesYaml.class );
				errorContext = "requirements.yaml";
				requirementsYamlContents = yamlObjectMapper.readValue(
						fileReader.getFileContents(project, branchOrTagName, PRODUCT_REQUIREMENTS_FILE),
																	   RequirementsYaml.class );
			}
			errorContext = "ingestion";
			foundProjectData = new ProjectData(repositoryType, projectName, branchOrTagName, isPreview,
					rokitYamlContents, valuesYamlContents, requirementsYamlContents);
		} catch (Exception e) {
			String errorMessage = "Error whilst reading Gitlab project. Context: " + errorContext;
			logger.warn(errorMessage, e);
			Exception wrappedException = new Exception(errorMessage, e);
			foundProjectData = new ProjectData(repositoryType, projectName, branchOrTagName, isPreview,
					wrappedException);
		}
		projectConsumer.accept(foundProjectData);
		return;
	}

}
