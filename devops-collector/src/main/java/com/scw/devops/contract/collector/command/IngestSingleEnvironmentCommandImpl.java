package com.scw.devops.contract.collector.command;

import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.contract.collector.data.OutputVoid;

public class IngestSingleEnvironmentCommandImpl {

	public static void execute( final AccessCoordinator accessCoordinator, final IngestSingleEnvironmentCommand command ) {
		accessCoordinator.ingestSingleEnvironment( command.name );
		command.result = new OutputVoid();
	}

}
