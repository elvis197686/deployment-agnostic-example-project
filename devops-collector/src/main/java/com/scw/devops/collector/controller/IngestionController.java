package com.scw.devops.collector.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scw.devops.collector.config.ApplicationConfiguration;
import com.scw.devops.collector.domain.RepositoryProjectVersion;
import com.scw.devops.collector.service.collect.CollectionService;
import com.scw.devops.collector.service.collect.WebhookUpdate;
import com.scw.devops.collector.service.ingest.IngestionService;
import com.scw.devops.collector.vcs.data.GitlabWebhook;
import com.scw.devops.collector.vcs.data.ProjectData;
import com.scw.devops.collector.vcs.data.RepositoryLocation;

@RestController
public class IngestionController {
	private final CollectionService collectionService;
	private final IngestionService ingestionService;
	private final Logger logger;
	private final boolean ingestOnStartup;

	@Autowired
	public IngestionController( final ApplicationConfiguration applicationConfig,
								final Logger logger,
			final CollectionService collectionService, final IngestionService ingestionService) {
		this.collectionService = collectionService;
		this.ingestionService = ingestionService;
		this.logger = logger;
		this.ingestOnStartup = applicationConfig.runIngestionOnStartup();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ingestEnvironmentsOnStartup() {
		if (ingestOnStartup) {
			ingestAllData();
		}
	}

	@GetMapping(value = "/ingest/all")
	public void ingestAll() {
		ingestAllData();
	}

	@GetMapping(value = "/ingest/environment/{name}")
	public void ingestSingleEnvironment(@PathVariable(name = "name") final String environmentName) {
		ingestEnvironment(environmentName);
	}

	@GetMapping(value = "/ingest/product/{name}")
	public void ingestSingleProduct(@PathVariable(name = "name") final String productName) {
		ingestApplications(ingestProduct(productName));
	}

	@PostMapping(value = "/webhooks")
	public void gitlabUpdate(@RequestBody final GitlabWebhook webhook) {
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
			logger.error("ERROR ON WEBHOOK PROCESSING!", th);
		}
		return;
	}

	private void ingestAllData() {
		try {
			ingestAllEnvironments();
			ingestApplications(ingestAllProducts());
		} catch (Throwable th) {
			logger.error("ERROR INGESTING ALL DATA!", th);
		}
		return;
	}

	private void ingestAllEnvironments() {
		Consumer<ProjectData> foundEnvironmentsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addEnvironment(t);
			}
		};
		collectionService.getEnvironments(foundEnvironmentsConsumer);
		return;
	}

	private void ingestEnvironment(final String environmentName) {
		Consumer<ProjectData> foundEnvironmentsConsumer = new Consumer<>() {
			@Override
			public void accept(final ProjectData t) {
				ingestionService.addEnvironment(t);
			}
		};
		collectionService.getEnvironment(environmentName, foundEnvironmentsConsumer);
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