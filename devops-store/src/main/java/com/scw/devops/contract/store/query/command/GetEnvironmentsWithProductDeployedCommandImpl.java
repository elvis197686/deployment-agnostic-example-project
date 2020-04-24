package com.scw.devops.contract.store.query.command;

import com.scw.devops.store.service.DataStoreQueryService;

public class GetEnvironmentsWithProductDeployedCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetEnvironmentsWithProductDeployedCommand command ) {
		command.result = new OutputEnvironmentDefinitions( query.getEnvironmentsWithProductDeployed( command.productDef ) );
	}
}
