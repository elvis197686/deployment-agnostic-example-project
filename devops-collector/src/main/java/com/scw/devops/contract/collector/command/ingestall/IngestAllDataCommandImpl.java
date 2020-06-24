package com.scw.devops.contract.collector.command.ingestall;

import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.collector.application.autowiring.CollectorAutowiring;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.collector.command.ExceptionHolder;
import com.scw.devops.contract.collector.command.ingestall.IngestAllDataCommand;
import com.scw.devops.contract.collector.data.OutputIngestionRequest;
import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.contract.store.update.command.addapplication.AddApplicationDefinitionCommand;
import com.scw.devops.contract.store.update.command.addenvironment.AddEnvironmentDefinitionCommand;
import com.scw.devops.contract.store.update.command.addproduct.AddProductDefinitionCommand;

public class IngestAllDataCommandImpl {

	// TODO - 3 issues regarding separation of IO from algorithms:
	// 1 These params need to be derived from the context somehow
	// 2 We can't pass all these functions/providers/suppliers as parameters
	// 3 re-use (is it even that important?):
	// (a) This is very similar to webhook processing - how to consolidate?
	// (b) Some methods (e.g getProject/getAllTags) kind of belong together - how can we abstract?
	public static void execute( final CollectorAutowiring collectorAutowiring, final IngestAllDataCommand command ) {
		ExceptionHolder excHolder = new ExceptionHolder();
		// TODO - Autowiring should be done up front somehow
		GitlabGateway gitlabGateway = collectorAutowiring.getGitlabGateway();
		DataStoreUpdater dataStoreUpdater = collectorAutowiring.getDataStoreUpdater();
		AccessCoordinator accessCoordinator = collectorAutowiring.getAccessCoordinator();
		accessCoordinator
			.ingestAllData( ( g ) -> gitlabGateway.getProjects( g ),
							( g, e ) -> gitlabGateway.getProject( g, e ),
							( p ) -> gitlabGateway
								.getAllTags( p ),
							( e ) -> excHolder.th = e,
							( en ) -> dataStoreUpdater.doCommand( new AddEnvironmentDefinitionCommand( en ) ),
							( pr ) -> dataStoreUpdater.doCommand( new AddProductDefinitionCommand( pr ) ),
							( ap ) -> dataStoreUpdater.doCommand( new AddApplicationDefinitionCommand( ap ) ) );
		command.result = new OutputIngestionRequest( excHolder.th );
	}
}
