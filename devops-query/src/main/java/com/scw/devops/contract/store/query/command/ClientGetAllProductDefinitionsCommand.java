package com.scw.devops.contract.store.query.command;

import java.util.Collection;

import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;

public class ClientGetAllProductDefinitionsCommand {

	public static Collection<ProductDefinition> process( final GetAllProductDefinitionsCommand command, final DataStoreReader reader ) {
		OutputProductDefinitions result = (OutputProductDefinitions) reader.doCommand( command );
		return result.getResult();
	}
}
