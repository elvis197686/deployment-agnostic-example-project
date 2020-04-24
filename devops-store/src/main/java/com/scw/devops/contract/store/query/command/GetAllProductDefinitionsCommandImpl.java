package com.scw.devops.contract.store.query.command;

import com.scw.devops.store.service.DataStoreQueryService;

public class GetAllProductDefinitionsCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetAllProductDefinitionsCommand command ) {
		command.result = new OutputProductDefinitions( query.getAllProductDefinitions( command.versionQuery ) );
	}
}
