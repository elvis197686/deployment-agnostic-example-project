package com.scw.devops.collector.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scw.devops.collector.config.ApplicationConfigurationImpl;
import com.scw.devops.contract.collector.CollectorAccessImpl;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;
import com.scw.devops.contract.collector.command.gitlabwebhook.ProcessGitlabUpdateCommand;
import com.scw.devops.contract.collector.command.ingestall.IngestAllDataCommand;
import com.scw.devops.contract.collector.command.ingestenvironment.IngestSingleEnvironmentCommand;
import com.scw.devops.contract.collector.command.ingestproduct.IngestSingleProductCommand;
import com.scw.devops.contract.collector.data.GitlabWebhookData;

@RestController
public class IngestionController {

	private final CollectorAccessImpl collectorAccess;
	private final Logger logger;
	private final boolean ingestOnStartup;

	@Autowired
	public IngestionController( final CollectorAccessImpl collectorAccess, final Logger logger,
								final ApplicationConfigurationImpl applicationConfig ) {
		this.collectorAccess = collectorAccess;
		this.logger = logger;
		this.ingestOnStartup = applicationConfig.runIngestionOnStartup();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ingestEnvironmentsOnStartup() {
		if (ingestOnStartup) {
			ingestAll();
		}
	}

	@GetMapping(value = "/ingest/all")
	public CollectorAccessCommandResult ingestAll() {
		IngestAllDataCommand command = new IngestAllDataCommand();
		return collectorAccess.doCommand( command );
	}

	@GetMapping(value = "/ingest/environment/{name}")
	public CollectorAccessCommandResult ingestSingleEnvironment( @PathVariable( name = "name" ) final String environmentName ) {
		return collectorAccess.doCommand( new IngestSingleEnvironmentCommand( environmentName ) );
	}

	@GetMapping(value = "/ingest/product/{name}")
	public CollectorAccessCommandResult ingestSingleProduct( @PathVariable( name = "name" ) final String productName ) {
		return collectorAccess.doCommand( new IngestSingleProductCommand( productName ) );
	}

	@PostMapping(value = "/webhooks")
	public CollectorAccessCommandResult gitlabUpdate( @RequestBody final GitlabWebhookData webhook ) {
		return collectorAccess.doCommand( new ProcessGitlabUpdateCommand( webhook ) );
	}

}