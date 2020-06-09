package com.scw.devops.contract.collector.command;

import com.scw.devops.collector.application.CollectorAutowiring;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.collector.data.OutputVoid;
import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.contract.store.update.command.AddEnvironmentDefinitionCommand;

public class IngestSingleEnvironmentCommandImpl {

	public static void execute( final CollectorAutowiring collectorAutowiring, final IngestSingleEnvironmentCommand command ) {
		GitlabGateway gitlabGateway = collectorAutowiring.getGitlabGateway();
		DataStoreUpdater dataStoreUpdater = collectorAutowiring.getDataStoreUpdater();
		collectorAutowiring.getAccessCoordinator()
			.ingestSingleEnvironment( command.name,
		                                           ( g, e ) -> gitlabGateway.getProject( g, e ),
												   ( p ) -> gitlabGateway.getAllTags( p ),
												   ( en ) -> dataStoreUpdater.doCommand( new AddEnvironmentDefinitionCommand( en ) ) );
		command.result = new OutputVoid();
	}

}
