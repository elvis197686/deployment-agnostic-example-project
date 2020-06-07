package com.scw.devops.contract.query.command;

import com.scw.devops.query.service.DefinitionQuery;

public class GetOrphanedApplicationsSinceRestartCommandImpl {

	public static void execute( final DefinitionQuery queryService, final GetOrphanedApplicationsSinceRestartCommand command ) {
		command.result = new ReturnApplicationDefinitions( queryService.getOrphanedApplicationDefinitionsSinceRestart() );
	}
}
