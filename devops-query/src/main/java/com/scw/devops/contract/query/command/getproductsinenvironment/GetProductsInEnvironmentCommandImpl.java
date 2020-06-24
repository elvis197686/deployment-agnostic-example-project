package com.scw.devops.contract.query.command.getproductsinenvironment;

import com.scw.devops.contract.query.command.ReturnProductDefinitions;
import com.scw.devops.contract.query.command.getproductsinenvironment.GetProductsInEnvironmentCommand;
import com.scw.devops.query.service.DefinitionQuery;

public class GetProductsInEnvironmentCommandImpl {

	public static void execute( final DefinitionQuery queryService, final GetProductsInEnvironmentCommand command ) {
		command.result = new ReturnProductDefinitions( queryService.getProductsInEnvironment( command.name, command.version, command.wantedClass ) );
	}
}
