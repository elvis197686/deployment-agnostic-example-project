package com.scw.devops.collector.service.collect;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabTag;
import org.slf4j.Logger;

import com.scw.devops.collector.application.autowiring.CollectorAutowiring;
import com.scw.devops.collector.domain.ProjectData;
import com.scw.devops.collector.domain.RepositoryLocation;
import com.scw.devops.collector.domain.RepositoryProjectVersion;
import com.scw.devops.collector.domain.RepositoryType;
import com.scw.devops.collector.vcs.ProjectReader;
import com.scw.devops.collector.vcs.ProjectReaderFactory;
import com.scw.devops.contract.collector.data.GitlabWebhookData;
import com.scw.devops.contract.collector.data.GitlabWebhookDataProcessor;
import com.scw.devops.domain.projectversion.ProjectVersion;

public class CollectionService {

	private final ProjectReaderFactory projectReaderFactory;
	private final String environmentGroupName;
	private final String productGroupName;
	private final Collection<String> productsToIgnore;
	private final Logger logger;

	public CollectionService( final CollectorAutowiring autowiring ) {
		this.projectReaderFactory = autowiring.getProjectReaderFactory();
		this.environmentGroupName = autowiring.getGitlabConfiguration().getEnvironmentGroupName();
		this.productGroupName = autowiring.getGitlabConfiguration().getProductGroupName();
		this.productsToIgnore = autowiring.getGitlabConfiguration().getProductsToIgnore();
		this.logger = autowiring.getLogger();
	}

	public void getEnvironments( final Function<String, List<GitlabProject>> gitlabProjectsProviderByGroup,
								 final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
								 final Consumer<ProjectData> foundEnvironments ) {
		analyseAllProjects( RepositoryType.ENVIRONMENT,
							environmentGroupName,
							gitlabProjectsProviderByGroup,
							gitlabTagProviderByProject,
							foundEnvironments );
	}

	public void getEnvironment( final String environmentName, final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
								final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
								final Consumer<ProjectData> foundEnvironments ) {
		GitlabProject gitlabProject = gitlabProjectProviderByGroupAndName.apply( environmentGroupName, environmentName );//gitlabGateway.getProject(environmentGroupName, environmentName);
		analyseProject( RepositoryType.ENVIRONMENT, gitlabProject, gitlabTagProviderByProject, foundEnvironments );
	}

	public void getSpecificEnvironmentVersion( final RepositoryLocation repositoryLocation,
											   final RepositoryProjectVersion projectVersion,
											   final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
											   final Consumer<ProjectData> environmentConsumer ) {
		analyseSingleProjectVersionWithCatchAll( RepositoryType.ENVIRONMENT,
												 repositoryLocation,
												 projectVersion,
												 gitlabProjectProviderByGroupAndName,
												 environmentConsumer );
	}

	public void getProducts( final Function<String, List<GitlabProject>> gitlabProjectsProviderByGroup,
							 final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject, final Consumer<ProjectData> foundProducts ) {
		analyseAllProjects( RepositoryType.PRODUCT, productGroupName, gitlabProjectsProviderByGroup, gitlabTagProviderByProject, foundProducts );
	}

	public void getProduct( final String productName, final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
							final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
							final Consumer<ProjectData> foundProducts ) {
		GitlabProject gitlabProject = gitlabProjectProviderByGroupAndName.apply( productGroupName, productName );
		analyseProject( RepositoryType.PRODUCT, gitlabProject, gitlabTagProviderByProject, foundProducts );
	}

	public void getSpecificProductVersion(final RepositoryLocation repositoryLocation,
										   final RepositoryProjectVersion repositoryVersion,
										   final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
										   final Consumer<ProjectData> productConsumer ) {
		analyseSingleProjectVersionWithCatchAll( RepositoryType.PRODUCT,
												 repositoryLocation,
												 repositoryVersion,
												 gitlabProjectProviderByGroupAndName,
				productConsumer);
	}

	public void getApplication( final RepositoryLocation repoLocation,
								final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
								final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
								final Consumer<ProjectData> applicationConsumer ) {
		analyseProject( RepositoryType.APPLICATION,
						repoLocation,
						gitlabProjectProviderByGroupAndName,
						gitlabTagProviderByProject,
						applicationConsumer );
	}

	public void getSpecificApplicationVersion(final RepositoryLocation repositoryLocation,
											   final RepositoryProjectVersion repositoryVersion,
											   final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
											   final Consumer<ProjectData> productConsumer ) {
		analyseSingleProjectVersionWithCatchAll( RepositoryType.APPLICATION,
												 repositoryLocation,
												 repositoryVersion,
												 gitlabProjectProviderByGroupAndName,
				productConsumer);
	}

	public Optional<WebhookUpdate> processWebhook( final GitlabWebhookData webhook,
			final BiFunction<RepositoryType, String, Boolean> projectRelevanceEvaluator,
			final BiConsumer<RepositoryLocation, ProjectVersion> analyseProjectConsumer) {
		String groupName = GitlabWebhookDataProcessor.getGroupName( webhook );
		RepositoryType type = getRepositoryType(groupName);
		String projectName = GitlabWebhookDataProcessor.getProjectName( webhook );
		String branchName = GitlabWebhookDataProcessor.getBranch( webhook );
		switch ( GitlabWebhookDataProcessor.getObjectKind( webhook ) ) {
		case "push":
			Collection<String> changedFiles = GitlabWebhookDataProcessor.getChangedFiles( webhook );
			boolean isPreviewBranch = branchName.equals(type.getPreviewBranch());
			if (isPreviewBranch && ProjectReader.anyRelevantFilesInList(type, changedFiles)) {
				return Optional.of(new WebhookUpdate(type, groupName, projectName, branchName, true));
			}
			logger.info("Ignoring push request as no relevant files changed in the preview branch.");
			return Optional.empty();
		case "tag_push":
			return Optional.of(new WebhookUpdate(type, groupName, projectName, branchName, false));
		}
		logger.info( "Ignoring Webhook. Type: " + GitlabWebhookDataProcessor.getObjectKind( webhook ) );
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
								final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
								final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
								final Consumer<ProjectData> projectConsumer ) {
		logger.info("Collecting project data: " + repoLocation.getProjectName());
		try {
			GitlabProject applicationProject = gitlabProjectProviderByGroupAndName.apply( repoLocation.getGroupName(),
																						  repoLocation.getProjectName() );// gitlabGateway.getProject(repoLocation.getGroupName(), repoLocation.getProjectName());
			analyseProject( repositoryType, applicationProject, gitlabTagProviderByProject, projectConsumer );
			logger.info("Collected project data: " + repoLocation.getProjectName());
		} catch (Throwable th) {
			logger.error("Failed to collect project data: " + repoLocation.getProjectName(), th);
		}
	}

	private void analyseAllProjects( final RepositoryType repositoryType, final String group,
									 final Function<String, List<GitlabProject>> gitlabProjectsProviderByGroup,
									 final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
									 final Consumer<ProjectData> foundProjects ) {
		List<GitlabProject> projects = gitlabProjectsProviderByGroup.apply( group );
		projects.stream().forEach( p -> analyseProjectWithCatchAll( repositoryType, p, gitlabTagProviderByProject, foundProjects ) );
	}

	private void analyseProjectWithCatchAll(final RepositoryType repositoryType, final GitlabProject project,
											 final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
			final Consumer<ProjectData> projectConsumer) {
		if (repositoryType.hasProductFiles()) {
			if (ignoreProduct(project.getName())) {
				logger.info("Ignoring project data: " + project.getName());
				return;
			}
		}
		logger.info("Collecting project data: " + project.getName());
		try {
			analyseProject( repositoryType, project, gitlabTagProviderByProject, projectConsumer );
			logger.info("Collected project data: " + project.getName());
		} catch (Throwable th) {
			logger.error("Failed to collect project data: " + project.getName(), th);
		}
	}

	public void analyseSingleProjectVersionWithCatchAll(final RepositoryType repositoryType,
														 final RepositoryLocation repositoryLocation, final RepositoryProjectVersion projectVersion,
														 final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
			final Consumer<ProjectData> projectConsumer) {
		logger.info("Collecting project single version data: " + repositoryLocation.getProjectName());
		try {
			GitlabProject gitlabRepo = gitlabProjectProviderByGroupAndName
				.apply( repositoryLocation.getGroupName(), repositoryLocation.getProjectName() );
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
								 final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
			final Consumer<ProjectData> projectConsumer) {
		ProjectReader projectReader = projectReaderFactory.getProjectReader(repositoryType, project);
		String defaultBranch = repositoryType.getPreviewBranch();
		projectReader.collectFiles( project.getName(), defaultBranch, ProjectVersion.previewVersion(), projectConsumer );
		List<GitlabTag> tags = gitlabTagProviderByProject.apply( project );
		tags.stream().forEach(t -> {
			projectReader.collectFiles( project.getName(), t.getName(), ProjectVersion.namedVersion( t.getName() ), projectConsumer );
		});
	}

	private void analyseBranchOrTag(final RepositoryType repositoryType, final GitlabProject project,
									 final RepositoryProjectVersion repositoryVersion, final Consumer<ProjectData> projectConsumer ) {
		ProjectReader projectReader = projectReaderFactory.getProjectReader(repositoryType, project);
		projectReader.collectFiles(project.getName(), repositoryVersion.getOriginalName(),
									repositoryVersion.getProjectVersion(),
									projectConsumer );
	}
}