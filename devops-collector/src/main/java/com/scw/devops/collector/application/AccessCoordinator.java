package com.scw.devops.collector.application;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import com.scw.devops.collector.domain.ProjectData;
import com.scw.devops.collector.domain.RepositoryLocation;
import com.scw.devops.collector.domain.RepositoryProjectVersion;
import com.scw.devops.collector.service.collect.CollectionService;
import com.scw.devops.collector.service.collect.WebhookUpdate;
import com.scw.devops.collector.service.ingest.IngestionService;
import com.scw.devops.contract.collector.data.GitlabWebhookData;

public class AccessCoordinator {
	private final CollectionService collectionService;
	private final IngestionService ingestionService;

	public AccessCoordinator( final CollectorAutowiring autowiring ) {
		this.collectionService = autowiring.getCollectionService();
		this.ingestionService = autowiring.getIngestionService();
	}

	public void processGitlabUpdate( final GitlabWebhookData webhook, final Consumer<Throwable> exceptionHandler ) {
		try {
			Optional<WebhookUpdate> processedWebhook = collectionService.processWebhook(webhook, null, null);
			if (processedWebhook.isPresent()) {
				WebhookUpdate webhookInfo = processedWebhook.get();
				RepositoryLocation repoLocation = webhookInfo.getRepositoryLocation();
				RepositoryProjectVersion projectVersion = webhookInfo.getProjectVersion();
				webhookInfo.getRepositoryType().processType((e) -> ingestEnvironment(repoLocation, projectVersion),
						(p) -> ingestApplications(ingestProduct(repoLocation, projectVersion)),
						(a) -> ingestApplication(repoLocation, projectVersion));
			}
		} catch (Throwable th) {
			exceptionHandler.accept( th );
		}
		return;
	}

	public void ingestAllData( final Consumer<Throwable> exceptionHandler ) {
		try {
			ingestAllEnvironments();
			ingestApplications( ingestAllProducts() );
		}
		catch ( Throwable th ) {
			exceptionHandler.accept( th );
		}
		return;
	}

	public void ingestSingleEnvironment( final String environmentName ) {
		Consumer<ProjectData> foundEnvironmentsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addEnvironment(t);
			}
		};
		collectionService.getEnvironment( environmentName, foundEnvironmentsConsumer );
		return;
	}

	public void ingestSingleProduct( final String productName ) {
		ingestApplications( ingestProduct( productName ) );
	}

	private void ingestAllEnvironments() {
		Consumer<ProjectData> foundEnvironmentsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addEnvironment(t);
			}
		};
		collectionService.getEnvironments( foundEnvironmentsConsumer );
		return;
	}

	private void ingestEnvironment( final RepositoryLocation repositoryLocation, final RepositoryProjectVersion p_projectVersion ) {
		Consumer<ProjectData> foundEnvironmentsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addEnvironment(t);
			}
		};
		collectionService.getSpecificEnvironmentVersion( repositoryLocation, p_projectVersion, foundEnvironmentsConsumer );
		return;
	}

	private Set<RepositoryLocation> ingestAllProducts() {
		Set<RepositoryLocation> foundApplicationLocations = new HashSet<>();
		Consumer<ProjectData> foundProductsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addProduct(t);
				t.getApplicationRepositories().stream().forEach(r -> foundApplicationLocations.add(r));
			}
		};
		collectionService.getProducts(foundProductsConsumer);
		return foundApplicationLocations;
	}

	private Set<RepositoryLocation> ingestProduct(final String productName) {
		Set<RepositoryLocation> foundApplicationLocations = new HashSet<>();
		Consumer<ProjectData> foundProductsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addProduct(t);
				t.getApplicationRepositories().stream().forEach(r -> foundApplicationLocations.add(r));
			}
		};
		collectionService.getProduct(productName, foundProductsConsumer);
		return foundApplicationLocations;
	}

	private Set<RepositoryLocation> ingestProduct(final RepositoryLocation repositoryLocation,
												   final RepositoryProjectVersion p_projectVersion ) {
		Set<RepositoryLocation> foundApplicationLocations = new HashSet<>();
		Consumer<ProjectData> foundProductsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addProduct(t);
				t.getApplicationRepositories().stream().forEach(r -> foundApplicationLocations.add(r));
			}
		};
		collectionService.getSpecificProductVersion( repositoryLocation, p_projectVersion, foundProductsConsumer );
		return foundApplicationLocations;
	}

	private void ingestApplications(final Set<RepositoryLocation> applicationLocations) {
		applicationLocations.stream().forEach(
				r -> collectionService.getApplication(r, (t) -> ingestionService.addApplication(t, r.getGroupName())));
		return;
	}

	private void ingestApplication( final RepositoryLocation repositoryLocation, final RepositoryProjectVersion p_projectVersion ) {
		Consumer<ProjectData> foundApplicationsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addApplication(t, repositoryLocation.getGroupName());
			}
		};
		collectionService.getSpecificApplicationVersion( repositoryLocation, p_projectVersion, foundApplicationsConsumer );
		return;
	}

}