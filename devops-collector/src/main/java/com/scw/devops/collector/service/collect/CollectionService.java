package com.scw.devops.collector.service.collect;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabTag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scw.devops.collector.config.GitlabConfiguration;
import com.scw.devops.collector.domain.RepositoryProjectVersion;
import com.scw.devops.collector.domain.RepositoryType;
import com.scw.devops.collector.vcs.ProjectReader;
import com.scw.devops.collector.vcs.ProjectReaderFactory;
import com.scw.devops.collector.vcs.data.GitlabWebhook;
import com.scw.devops.collector.vcs.data.ProjectData;
import com.scw.devops.collector.vcs.data.RepositoryLocation;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.store.common.data.ProjectVersion;

@Service
public class CollectionService {

	private final GitlabGateway gitlabGateway;
	private final ProjectReaderFactory projectReaderFactory;
	private final String environmentGroupName;
	private final String productGroupName;
	private final Collection<String> productsToIgnore;
	private final Logger logger;

	@Autowired
	public CollectionService(final GitlabGateway gitlabGateway, final ProjectReaderFactory projectReaderFactory,
			final GitlabConfiguration gitlabConfiguration, final Logger logger) {
		this.gitlabGateway = gitlabGateway;
		this.projectReaderFactory = projectReaderFactory;
		this.environmentGroupName = gitlabConfiguration.getEnvironmentGroupName();
		this.productGroupName = gitlabConfiguration.getProductGroupName();
		this.productsToIgnore = gitlabConfiguration.getProductsToIgnore();
		this.logger = logger;
	}

	public void getEnvironments(final Consumer<ProjectData> foundEnvironments) {
		analyseAllProjects(RepositoryType.ENVIRONMENT, environmentGroupName, foundEnvironments);
	}

	public void getEnvironment(final String environmentName, final Consumer<ProjectData> foundEnvironments) {
		GitlabProject gitlabProject = gitlabGateway.getProject(environmentGroupName, environmentName);
		analyseProject(RepositoryType.ENVIRONMENT, gitlabProject, foundEnvironments);
	}

	public void getProducts(final Consumer<ProjectData> foundProducts) {
		analyseAllProjects(RepositoryType.PRODUCT, productGroupName, foundProducts);
	}

	public void getProduct(final String productName, final Consumer<ProjectData> foundProducts) {
		GitlabProject gitlabProject = gitlabGateway.getProject(productGroupName, productName);
		analyseProject(RepositoryType.PRODUCT, gitlabProject, foundProducts);
	}

	public void getSpecificEnvironmentVersion(final RepositoryLocation repositoryLocation,
											   final RepositoryProjectVersion p_projectVersion, final Consumer<ProjectData> environmentConsumer ) {
		analyseSingleProjectVersionWithCatchAll( RepositoryType.ENVIRONMENT,
												 repositoryLocation,
												 p_projectVersion,
				environmentConsumer);
	}

	public void getSpecificProductVersion(final RepositoryLocation repositoryLocation,
										   final RepositoryProjectVersion repositoryVersion, final Consumer<ProjectData> productConsumer ) {
		analyseSingleProjectVersionWithCatchAll(RepositoryType.PRODUCT, repositoryLocation, repositoryVersion,
				productConsumer);
	}

	public void getApplication(final RepositoryLocation repoLocation, final Consumer<ProjectData> applicationConsumer) {
		analyseProject(RepositoryType.APPLICATION, repoLocation, applicationConsumer);
	}

	public void getSpecificApplicationVersion(final RepositoryLocation repositoryLocation,
											   final RepositoryProjectVersion repositoryVersion, final Consumer<ProjectData> productConsumer ) {
		analyseSingleProjectVersionWithCatchAll(RepositoryType.APPLICATION, repositoryLocation, repositoryVersion,
				productConsumer);
	}

	public Optional<WebhookUpdate> processWebhook(final GitlabWebhook webhook,
			final BiFunction<RepositoryType, String, Boolean> projectRelevanceEvaluator,
			final BiConsumer<RepositoryLocation, ProjectVersion> analyseProjectConsumer) {
		String groupName = webhook.getGroupName();
		RepositoryType type = getRepositoryType(groupName);
		String projectName = webhook.getProjectName();
		String branchName = webhook.getBranch();
		switch (webhook.getObjectKind()) {
		case "push":
			Collection<String> changedFiles = webhook.getChangedFiles();
			boolean isPreviewBranch = branchName.equals(type.getPreviewBranch());
			if (isPreviewBranch && ProjectReader.anyRelevantFilesInList(type, changedFiles)) {
				return Optional.of(new WebhookUpdate(type, groupName, projectName, branchName, true));
			}
			logger.info("Ignoring push request as no relevant files changed in the preview branch.");
			return Optional.empty();
		case "tag_push":
			return Optional.of(new WebhookUpdate(type, groupName, projectName, branchName, false));
		}
		logger.info("Ignoring Webhook. Type: " + webhook.getObjectKind());
		return Optional.empty();
	}

	private RepositoryType getRepositoryType(final String groupName) {
		if (groupName.equals(environmentGroupName)) {
			return RepositoryType.ENVIRONMENT;
		}
		if (groupName.equals(productGroupName)) {
			return RepositoryType.PRODUCT;
		}
		// Safe to assum an application - they will be ignored if they do not already
		// exist in the repo
		return RepositoryType.APPLICATION;
	}

	public void analyseProject(final RepositoryType repositoryType, final RepositoryLocation repoLocation,
			final Consumer<ProjectData> projectConsumer) {
		logger.info("Collecting project data: " + repoLocation.getProjectName());
		try {
			GitlabProject applicationProject = gitlabGateway.getProject(repoLocation.getGroupName(),
					repoLocation.getProjectName());
			analyseProject(repositoryType, applicationProject, projectConsumer);
			logger.info("Collected project data: " + repoLocation.getProjectName());
		} catch (Throwable th) {
			logger.error("Failed to collect project data: " + repoLocation.getProjectName(), th);
		}
	}

	private void analyseAllProjects(final RepositoryType repositoryType, final String group, final Consumer<ProjectData> foundProjects) {
		List<GitlabProject> projects = gitlabGateway.getProjects(group);
		projects.stream().forEach(p -> analyseProjectWithCatchAll(repositoryType, p, foundProjects));
	}

	private void analyseProjectWithCatchAll(final RepositoryType repositoryType, final GitlabProject project,
			final Consumer<ProjectData> projectConsumer) {
		if (repositoryType.hasProductFiles()) {
			if (ignoreProduct(project.getName())) {
				logger.info("Ignoring project data: " + project.getName());
				return;
			}
		}
		logger.info("Collecting project data: " + project.getName());
		try {
			analyseProject(repositoryType, project, projectConsumer);
			logger.info("Collected project data: " + project.getName());
		} catch (Throwable th) {
			logger.error("Failed to collect project data: " + project.getName(), th);
		}
	}

	public void analyseSingleProjectVersionWithCatchAll(final RepositoryType repositoryType,
														 final RepositoryLocation repositoryLocation, final RepositoryProjectVersion projectVersion,
			final Consumer<ProjectData> projectConsumer) {
		logger.info("Collecting project single version data: " + repositoryLocation.getProjectName());
		try {
			GitlabProject gitlabRepo = gitlabGateway.getProject(repositoryLocation.getGroupName(),
					repositoryLocation.getProjectName());
			analyseBranchOrTag(repositoryType, gitlabRepo, projectVersion, projectConsumer);
			logger.info("Collected project single version data: " + repositoryLocation.getProjectName());
		} catch (Throwable th) {
			logger.error("Failed to collect project single version data: " + repositoryLocation.getProjectName(), th);
		}
	}

	private boolean ignoreProduct(final String projectName) {
		return productsToIgnore.stream().anyMatch(i -> projectName.matches(i));
	}

	private void analyseProject(final RepositoryType repositoryType, final GitlabProject project,
			final Consumer<ProjectData> projectConsumer) {
		ProjectReader projectReader = projectReaderFactory.getProjectReader(repositoryType, project);
		String defaultBranch = repositoryType.getPreviewBranch();
		projectReader.collectFiles(project.getName(), defaultBranch, true, projectConsumer);
		List<GitlabTag> tags = gitlabGateway.getAllTags(project);
		tags.stream().forEach(t -> {
			projectReader.collectFiles(project.getName(), t.getName(), false, projectConsumer);
		});
	}

	private void analyseBranchOrTag(final RepositoryType repositoryType, final GitlabProject project,
									 final RepositoryProjectVersion repositoryVersion, final Consumer<ProjectData> projectConsumer ) {
		ProjectReader projectReader = projectReaderFactory.getProjectReader(repositoryType, project);
		projectReader.collectFiles(project.getName(), repositoryVersion.getOriginalName(),
				repositoryVersion.isPreview(), projectConsumer);
	}
}