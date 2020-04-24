package com.scw.devops.contract.store.query.command;

import com.scw.devops.store.service.DataStoreQueryService;

public class GetAllApplicationDefinitionsCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetAllApplicationDefinitionsCommand command ) {
		command.result = new OutputApplicationDefinitions( query.getAllApplicationDefinitions( command.versionQuery ) );
	}
}
