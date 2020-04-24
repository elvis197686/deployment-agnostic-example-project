package com.scw.devops.contract.store.query.command;

import java.util.Optional;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;

public class ClientGetApplicationDefinitionCommand {

	public static Optional<ApplicationDefinition> process( final GetApplicationDefinitionCommand command, final DataStoreReader reader ) {
		OutputApplicationDefinition result = (OutputApplicationDefinition) reader.doCommand( command );
		return result.getResult();
	}

}
