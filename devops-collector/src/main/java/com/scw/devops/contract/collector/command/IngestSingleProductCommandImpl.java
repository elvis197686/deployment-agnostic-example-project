package com.scw.devops.contract.collector.command;

import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.contract.collector.data.OutputVoid;

public class IngestSingleProductCommandImpl {

	public static void execute( final AccessCoordinator accessCoordinator, final IngestSingleProductCommand command ) {
		accessCoordinator.ingestSingleProduct( command.name );
		command.result = new OutputVoid();
	}

}
