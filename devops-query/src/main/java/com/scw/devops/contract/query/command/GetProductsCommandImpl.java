package com.scw.devops.contract.query.command;

import com.scw.devops.query.service.DefinitionQuery;

public class GetProductsCommandImpl {

	public static void execute( final DefinitionQuery queryService, final GetProductsCommand command ) {
		command.result = new ReturnProductDefinitions( queryService.getProducts( command.versionLimit, command.queryFilter ) );
	}
}
