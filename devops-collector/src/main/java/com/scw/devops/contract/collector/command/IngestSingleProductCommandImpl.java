package com.scw.devops.contract.collector.command;

import com.scw.devops.collector.application.CollectorAutowiring;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.collector.data.OutputVoid;
import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.contract.store.update.command.AddApplicationDefinitionCommand;
import com.scw.devops.contract.store.update.command.AddProductDefinitionCommand;

public class IngestSingleProductCommandImpl {

	public static void execute( final CollectorAutowiring collectorAutowiring, final IngestSingleProductCommand command ) {
		GitlabGateway gitlabGateway = collectorAutowiring.getGitlabGateway();
		DataStoreUpdater dataStoreUpdater = collectorAutowiring.getDataStoreUpdater();
		collectorAutowiring.getAccessCoordinator()
			.ingestSingleProduct( command.name,
											   ( g, e ) -> gitlabGateway.getProject( g, e ),
											   ( p ) -> gitlabGateway.getAllTags( p ),
											   ( pr ) -> dataStoreUpdater.doCommand( new AddProductDefinitionCommand( pr ) ),
											   ( ap ) -> dataStoreUpdater.doCommand( new AddApplicationDefinitionCommand( ap ) ) );
		command.result = new OutputVoid();
	}

}
