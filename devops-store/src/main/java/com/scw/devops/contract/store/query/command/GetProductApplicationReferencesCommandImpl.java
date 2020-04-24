package com.scw.devops.contract.store.query.command;

import com.scw.devops.store.service.DataStoreQueryService;

public class GetProductApplicationReferencesCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetProductApplicationReferencesCommand command ) {
		command.result = new OutputApplicationInstances( query.getProductApplicationReferences( command.productDefinition ) );
	}
}
