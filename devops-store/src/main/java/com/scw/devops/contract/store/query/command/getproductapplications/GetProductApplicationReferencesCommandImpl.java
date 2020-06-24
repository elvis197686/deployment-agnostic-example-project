package com.scw.devops.contract.store.query.command.getproductapplications;

import com.scw.devops.contract.store.query.command.OutputApplicationInstances;
import com.scw.devops.store.service.DataStoreQueryService;

public class GetProductApplicationReferencesCommandImpl {

	public static void execute( final DataStoreQueryService query, final GetProductApplicationReferencesCommand command ) {
		command.result = new OutputApplicationInstances( query.getProductApplicationReferences( command.productDefinition ) );
	}
}
