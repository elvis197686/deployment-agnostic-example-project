package com.scw.devops.contract.query.command;

import com.scw.devops.query.service.DefinitionQuery;

public class GetEnvironmentsCommandImpl {

	public static void execute( final DefinitionQuery queryService, final GetEnvironmentsCommand command ) {
		command.result = new ReturnEnvironmentDefinitions( queryService.getEnvironments( command.versionLimit, command.queryFilter ) );
	}
}
