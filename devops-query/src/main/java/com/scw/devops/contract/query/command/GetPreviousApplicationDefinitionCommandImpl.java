package com.scw.devops.contract.query.command;

import com.scw.devops.query.service.DefinitionQuery;

public class GetPreviousApplicationDefinitionCommandImpl {

	public static void execute( final DefinitionQuery queryService, final GetPreviousApplicationsDefinitionCommand command ) {
		command.result = new ReturnApplicationDefinition( queryService.getPreviousApplicationDefinition( command.name, command.version ) );
	}
}
