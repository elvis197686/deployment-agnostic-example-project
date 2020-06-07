package com.scw.devops.contract.query.command;

import com.scw.devops.query.service.DefinitionQuery;

public class GetApplicationsCommandImpl {

	public static void execute( final DefinitionQuery queryService, final GetApplicationsCommand command ) {
		command.result = new ReturnApplicationDefinitions( queryService.getApplications( command.versionLimit, command.queryFilter ) );
	}
}
