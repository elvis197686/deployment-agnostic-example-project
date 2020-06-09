package com.scw.devops.collector.application;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabTag;

import com.scw.devops.collector.domain.ProjectData;
import com.scw.devops.collector.domain.RepositoryLocation;
import com.scw.devops.collector.domain.RepositoryProjectVersion;
import com.scw.devops.collector.service.collect.CollectionService;
import com.scw.devops.collector.service.collect.WebhookUpdate;
import com.scw.devops.collector.service.ingest.IngestionService;
import com.scw.devops.contract.collector.data.GitlabWebhookData;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.ProductDefinition;

public class AccessCoordinator {
	private final CollectionService collectionService;
	private final IngestionService ingestionService;

	public AccessCoordinator( final CollectorAutowiring autowiring ) {
		this.collectionService = autowiring.getCollectionService();
		this.ingestionService = autowiring.getIngestionService();
	}

	public void processGitlabUpdate( final GitlabWebhookData webhook, final Consumer<Throwable> exceptionHandler,
									 final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
									 final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
									 final Consumer<EnvironmentDefinition> environmentConsumer, final Consumer<ProductDefinition> productConsumer,
									 final Consumer<ApplicationDefinition> applicationConsumer ) {
		try {
			Optional<WebhookUpdate> processedWebhook = collectionService.processWebhook(webhook, null, null);
			if (processedWebhook.isPresent()) {
				WebhookUpdate webhookInfo = processedWebhook.get();
				RepositoryLocation repoLocation = webhookInfo.getRepositoryLocation();
				RepositoryProjectVersion projectVersion = webhookInfo.getProjectVersion();
				webhookInfo.getRepositoryType()
					.processType( ( e ) -> ingestEnvironment( repoLocation,
															  projectVersion,
															  gitlabProjectProviderByGroupAndName,
															  environmentConsumer ),
								  ( p ) -> ingestApplications( ingestProduct( repoLocation,
																			  projectVersion,
																			  gitlabProjectProviderByGroupAndName,
																			  productConsumer ),
															   gitlabProjectProviderByGroupAndName,
															   gitlabTagProviderByProject,
															   applicationConsumer ),
								  ( a ) -> ingestApplication( repoLocation,
															  projectVersion,
															  gitlabProjectProviderByGroupAndName,
															  applicationConsumer ) );
			}
		} catch (Throwable th) {
			exceptionHandler.accept( th );
		}
		return;
	}

	public void ingestAllData( final Function<String, List<GitlabProject>> gitlabProjectsProviderByGroup,
							   final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
							   final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
							   final Consumer<Throwable> exceptionHandler,
							   final Consumer<EnvironmentDefinition> environmentConsumer, final Consumer<ProductDefinition> productConsumer,
							   final Consumer<ApplicationDefinition> applicationConsumer ) {
		try {
			ingestAllEnvironments( gitlabProjectsProviderByGroup, gitlabTagProviderByProject, environmentConsumer );
			ingestApplications( ingestAllProducts( gitlabProjectsProviderByGroup, gitlabTagProviderByProject, productConsumer ),
								gitlabProjectProviderByGroupAndName,
								gitlabTagProviderByProject,
								applicationConsumer );
		}
		catch ( Throwable th ) {
			exceptionHandler.accept( th );
		}
		return;
	}

	public void ingestSingleEnvironment( final String environmentName,
										 final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
										 final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
										 final Consumer<EnvironmentDefinition> environmentConsumer ) {
		Consumer<ProjectData> foundEnvironmentsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addEnvironment( t, environmentConsumer );
			}
		};
		collectionService
			.getEnvironment( environmentName, gitlabProjectProviderByGroupAndName, gitlabTagProviderByProject, foundEnvironmentsConsumer );
		return;
	}

	public void ingestSingleProduct( final String productName, final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
									 final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
									 final Consumer<ProductDefinition> productConsumer,
									 final Consumer<ApplicationDefinition> applicationConsumer ) {
		ingestApplications( ingestProduct( productName, gitlabProjectProviderByGroupAndName, gitlabTagProviderByProject, productConsumer ),
							gitlabProjectProviderByGroupAndName,
							gitlabTagProviderByProject,
							applicationConsumer );
	}

	private void ingestAllEnvironments( final Function<String, List<GitlabProject>> gitlabProjectsProviderByGroup,
										final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
										final Consumer<EnvironmentDefinition> environmentConsumer ) {
		Consumer<ProjectData> foundEnvironmentsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addEnvironment( t, environmentConsumer );
			}
		};
		collectionService.getEnvironments( gitlabProjectsProviderByGroup, gitlabTagProviderByProject, foundEnvironmentsConsumer );
		return;
	}

	private void ingestEnvironment( final RepositoryLocation repositoryLocation,
									final RepositoryProjectVersion projectVersion,
									final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
									final Consumer<EnvironmentDefinition> environmentConsumer ) {
		Consumer<ProjectData> foundEnvironmentsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addEnvironment( t, environmentConsumer );
			}
		};
		collectionService
			.getSpecificEnvironmentVersion( repositoryLocation, projectVersion, gitlabProjectProviderByGroupAndName, foundEnvironmentsConsumer );
		return;
	}

	private Set<RepositoryLocation> ingestAllProducts( final Function<String, List<GitlabProject>> gitlabProjectsProviderByGroup,
													   final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
													   final Consumer<ProductDefinition> productConsumer ) {
		Set<RepositoryLocation> foundApplicationLocations = new HashSet<>();
		Consumer<ProjectData> foundProductsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addProduct( t, productConsumer );
				t.getApplicationRepositories().stream().forEach(r -> foundApplicationLocations.add(r));
			}
		};
		collectionService.getProducts( gitlabProjectsProviderByGroup, gitlabTagProviderByProject, foundProductsConsumer );
		return foundApplicationLocations;
	}

	private Set<RepositoryLocation> ingestProduct( final String productName,
												   final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
												   final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
												   final Consumer<ProductDefinition> productConsumer ) {
		Set<RepositoryLocation> foundApplicationLocations = new HashSet<>();
		Consumer<ProjectData> foundProductsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addProduct( t, productConsumer );
				t.getApplicationRepositories().stream().forEach(r -> foundApplicationLocations.add(r));
			}
		};
		collectionService.getProduct( productName, gitlabProjectProviderByGroupAndName, gitlabTagProviderByProject, foundProductsConsumer );
		return foundApplicationLocations;
	}

	private Set<RepositoryLocation> ingestProduct(final RepositoryLocation repositoryLocation,
												   final RepositoryProjectVersion projectVersion,
												   final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
												   final Consumer<ProductDefinition> productConsumer ) {
		Set<RepositoryLocation> foundApplicationLocations = new HashSet<>();
		Consumer<ProjectData> foundProductsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addProduct( t, productConsumer );
				t.getApplicationRepositories().stream().forEach(r -> foundApplicationLocations.add(r));
			}
		};
		collectionService.getSpecificProductVersion( repositoryLocation, projectVersion, gitlabProjectProviderByGroupAndName, foundProductsConsumer );
		return foundApplicationLocations;
	}

	private void ingestApplications( final Set<RepositoryLocation> applicationLocations,
									 final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
									 final Function<GitlabProject, List<GitlabTag>> gitlabTagProviderByProject,
									 final Consumer<ApplicationDefinition> applicationConsumer ) {
		applicationLocations.stream().forEach(
											   r -> collectionService
												   .getApplication( r,
																	gitlabProjectProviderByGroupAndName,
																	gitlabTagProviderByProject,
																	( t ) -> ingestionService
																		.addApplication( t, r.getGroupName(), applicationConsumer ) ) );
		return;
	}

	private void ingestApplication( final RepositoryLocation repositoryLocation, final RepositoryProjectVersion projectVersion,
									final BiFunction<String, String, GitlabProject> gitlabProjectProviderByGroupAndName,
									final Consumer<ApplicationDefinition> applicationConsumer ) {
		Consumer<ProjectData> foundApplicationsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addApplication( t, repositoryLocation.getGroupName(), applicationConsumer );
			}
		};
		collectionService
			.getSpecificApplicationVersion( repositoryLocation, projectVersion, gitlabProjectProviderByGroupAndName, foundApplicationsConsumer );
		return;
	}

}