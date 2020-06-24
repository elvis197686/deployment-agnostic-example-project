package com.scw.devops.contract.store.query.command.getproductsinenvironment;

import com.scw.devops.contract.store.query.command.OutputProductDefinitions;
import com.scw.devops.store.service.DataStoreQueryService;

public class GetProductDefinitionsForEnvironmentCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetProductDefinitionsForEnvironmentCommand command ) {
		command.result = new OutputProductDefinitions( query.getProductDefinitionsForEnvironment( command.name,
																								  command.wantedVersionAsSingleString ) );
	}
}
