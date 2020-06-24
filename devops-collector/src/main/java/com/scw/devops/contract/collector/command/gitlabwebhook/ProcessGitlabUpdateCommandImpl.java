package com.scw.devops.contract.collector.command.gitlabwebhook;

import com.scw.devops.collector.application.autowiring.CollectorAutowiring;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.collector.command.ExceptionHolder;
import com.scw.devops.contract.collector.command.gitlabwebhook.ProcessGitlabUpdateCommand;
import com.scw.devops.contract.collector.data.OutputIngestionRequest;
import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.contract.store.update.command.addapplication.AddApplicationDefinitionCommand;
import com.scw.devops.contract.store.update.command.addenvironment.AddEnvironmentDefinitionCommand;
import com.scw.devops.contract.store.update.command.addproduct.AddProductDefinitionCommand;

public class ProcessGitlabUpdateCommandImpl {

	public static void execute( final CollectorAutowiring collectorAutowiring, final ProcessGitlabUpdateCommand command ) {
		ExceptionHolder excHolder = new ExceptionHolder();
		GitlabGateway gitlabGateway = collectorAutowiring.getGitlabGateway();
		DataStoreUpdater dataStoreUpdater = collectorAutowiring.getDataStoreUpdater();
		collectorAutowiring.getAccessCoordinator()
			.processGitlabUpdate( command.webhookData,
											   ( e ) -> excHolder.th = e,
											   ( g, e ) -> gitlabGateway.getProject( g, e ),
											   ( p ) -> gitlabGateway.getAllTags( p ),
											   ( en ) -> dataStoreUpdater.doCommand( new AddEnvironmentDefinitionCommand( en ) ),
											   ( pr ) -> dataStoreUpdater.doCommand( new AddProductDefinitionCommand( pr ) ),
											   ( ap ) -> dataStoreUpdater.doCommand( new AddApplicationDefinitionCommand( ap ) ) );
		command.result = new OutputIngestionRequest( excHolder.th );
	}

}
