package com.scw.devops.contract.store.query.command;

import com.scw.devops.store.service.DataStoreQueryService;

public class GetPreviousApplicationDefinitionCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetPreviousApplicationDefinitionCommand command ) {
		command.result = new OutputApplicationDefinition( query.getPreviousApplicationDefinition( command.applicationDefinition ) );
	}
}
