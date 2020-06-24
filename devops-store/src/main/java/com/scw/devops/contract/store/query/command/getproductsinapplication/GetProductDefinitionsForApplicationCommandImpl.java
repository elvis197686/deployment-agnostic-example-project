package com.scw.devops.contract.store.query.command.getproductsinapplication;

import com.scw.devops.contract.store.query.command.OutputProductDefinitions;
import com.scw.devops.store.service.DataStoreQueryService;

public class GetProductDefinitionsForApplicationCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetProductDefinitionsForApplicationCommand command ) {
		command.result = new OutputProductDefinitions( query
			.getProductDefinitionsForApplication( command.name, command.wantedVersionAsSingleString, command.productVersionsToReturn ) );
	}
}
