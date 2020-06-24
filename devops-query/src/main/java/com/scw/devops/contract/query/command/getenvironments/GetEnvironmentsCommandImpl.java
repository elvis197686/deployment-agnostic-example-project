package com.scw.devops.contract.query.command.getenvironments;

import com.scw.devops.contract.query.command.ReturnEnvironmentDefinitions;
import com.scw.devops.contract.query.command.getenvironments.GetEnvironmentsCommand;
import com.scw.devops.query.service.DefinitionQuery;

public class GetEnvironmentsCommandImpl {

	public static void execute( final DefinitionQuery queryService, final GetEnvironmentsCommand command ) {
		command.result = new ReturnEnvironmentDefinitions( queryService.getEnvironments( command.versionLimit, command.queryFilter ) );
	}
}
