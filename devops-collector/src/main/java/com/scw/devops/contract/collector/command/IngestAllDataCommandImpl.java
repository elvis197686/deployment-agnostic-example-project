package com.scw.devops.contract.collector.command;

import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.contract.collector.data.OutputIngestionRequest;

public class IngestAllDataCommandImpl {

	public static void execute( final AccessCoordinator accessCoordinator, final IngestAllDataCommand command ) {
		ExceptionHolder excHolder = new ExceptionHolder();
		accessCoordinator.ingestAllData( ( e ) -> excHolder.th = e );
		command.result = new OutputIngestionRequest( excHolder.th );
	}
}
