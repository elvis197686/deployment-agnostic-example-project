package com.scw.devops.contract.collector.command.ingestproduct;

import com.scw.devops.collector.application.autowiring.CollectorAutowiring;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.collector.command.ingestproduct.IngestSingleProductCommand;
import com.scw.devops.contract.collector.data.OutputVoid;
import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.contract.store.update.command.addapplication.AddApplicationDefinitionCommand;
import com.scw.devops.contract.store.update.command.addproduct.AddProductDefinitionCommand;

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
