package com.scw.devops.test.integration;

import com.scw.devops.application.AutowiringProviderImpl;
import com.scw.devops.contract.collector.command.IngestAllDataCommand;
import com.scw.devops.contract.collector.data.OutputIngestionRequest;
import com.scw.devops.contract.collector.data.OutputIngestionRequestProcessor;
import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.query.service.DefinitionQuery;
import com.scw.devops.test.integration.config.ResourceFolderGateway;
import com.scw.devops.test.integration.contract.collector.CollectorAccessImpl;
import com.scw.devops.test.integration.contract.query.DevopsQueryImpl;

public class Application {

	private final CollectorAccessImpl collectorApi;
	private final DevopsQueryImpl queryApi;
	private final ResourceFolderGateway ingestionConfig;

	public Application() {
		AutowiringProviderImpl autowiring = new AutowiringProviderImpl();
		collectorApi = new CollectorAccessImpl( autowiring );
		queryApi = new DevopsQueryImpl( new DefinitionQuery( autowiring ) );
		ingestionConfig = autowiring.getGitlabGateway();
	}

	public void ingestAll( final String projectDirectory ) throws Throwable {
		ingestionConfig.setProjectDirectory( projectDirectory );
		IngestAllDataCommand ingestAll = new IngestAllDataCommand();
		OutputIngestionRequest result = (OutputIngestionRequest) collectorApi.doCommand( ingestAll );
		OutputIngestionRequestProcessor.processResult( result );
	}

	public DevopsQueryCommandResult doQuery( final DevopsQueryCommand request ) {
		return queryApi.doCommand( request );
	}

}
