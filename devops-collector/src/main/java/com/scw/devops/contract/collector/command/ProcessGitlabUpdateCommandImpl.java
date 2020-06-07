package com.scw.devops.contract.collector.command;

import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.contract.collector.data.OutputIngestionRequest;

public class ProcessGitlabUpdateCommandImpl {

	public static void execute( final AccessCoordinator accessCoordinator, final ProcessGitlabUpdateCommand command ) {
		ExceptionHolder excHolder = new ExceptionHolder();
		accessCoordinator.processGitlabUpdate( command.webhookData, ( e ) -> excHolder.th = e );
		command.result = new OutputIngestionRequest( excHolder.th );
	}

}
