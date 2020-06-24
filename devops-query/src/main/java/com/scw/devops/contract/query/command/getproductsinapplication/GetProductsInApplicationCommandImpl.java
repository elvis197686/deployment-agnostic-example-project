package com.scw.devops.contract.query.command.getproductsinapplication;

import com.scw.devops.contract.query.command.ReturnProductDefinitions;
import com.scw.devops.contract.query.command.getproductsinapplication.GetProductsInApplicationCommand;
import com.scw.devops.query.service.DefinitionQuery;

public class GetProductsInApplicationCommandImpl {

	public static void execute( final DefinitionQuery queryService, final GetProductsInApplicationCommand command ) {
		command.result = new ReturnProductDefinitions( queryService
			.getProductsInApplication( command.applicationName, command.version, command.versionLimit, command.queryFilter ) );
	}
}
