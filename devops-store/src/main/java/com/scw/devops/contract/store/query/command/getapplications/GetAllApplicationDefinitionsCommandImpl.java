package com.scw.devops.contract.store.query.command.getapplications;

import com.scw.devops.contract.store.query.command.OutputApplicationDefinitions;
import com.scw.devops.store.service.DataStoreQueryService;

public class GetAllApplicationDefinitionsCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetAllApplicationDefinitionsCommand command ) {
		command.result = new OutputApplicationDefinitions( query.getAllApplicationDefinitions( command.versionQuery ) );
	}
}
