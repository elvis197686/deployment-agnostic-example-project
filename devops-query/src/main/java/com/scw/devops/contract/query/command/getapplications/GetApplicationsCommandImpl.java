package com.scw.devops.contract.query.command.getapplications;

import com.scw.devops.contract.query.command.ReturnApplicationDefinitions;
import com.scw.devops.query.service.DefinitionQuery;

public class GetApplicationsCommandImpl {

	public static void execute( final DefinitionQuery queryService, final GetApplicationsCommand command ) {
		command.result = new ReturnApplicationDefinitions( queryService.getApplications( command.versionLimit, command.queryFilter ) );
	}
}
