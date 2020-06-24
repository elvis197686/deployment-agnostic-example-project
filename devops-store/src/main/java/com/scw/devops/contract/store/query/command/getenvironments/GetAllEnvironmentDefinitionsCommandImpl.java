package com.scw.devops.contract.store.query.command.getenvironments;

import com.scw.devops.contract.store.query.command.OutputEnvironmentDefinitions;
import com.scw.devops.store.service.DataStoreQueryService;

public class GetAllEnvironmentDefinitionsCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetAllEnvironmentDefinitionsCommand command ) {
		command.result = new OutputEnvironmentDefinitions( query.getAllEnvironmentDefinitions( command.versionQuery ) );
	}
}
