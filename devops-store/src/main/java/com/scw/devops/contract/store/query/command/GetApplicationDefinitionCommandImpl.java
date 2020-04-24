package com.scw.devops.contract.store.query.command;

import com.scw.devops.store.service.DataStoreQueryService;

public class GetApplicationDefinitionCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetApplicationDefinitionCommand command ) {
		command.result = new OutputApplicationDefinition( query.getApplicationDefinition( command.name, command.version ) );
	}
}
