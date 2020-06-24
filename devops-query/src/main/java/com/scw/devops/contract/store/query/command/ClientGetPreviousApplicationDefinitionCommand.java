package com.scw.devops.contract.store.query.command;

import java.util.Optional;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.command.getpreviousapplication.GetPreviousApplicationDefinitionCommand;

public class ClientGetPreviousApplicationDefinitionCommand {

	public static Optional<ApplicationDefinition> process( final GetPreviousApplicationDefinitionCommand command, final DataStoreReader reader ) {
		OutputApplicationDefinition result = (OutputApplicationDefinition) reader.doCommand( command );
		return result.getResult();
	}

}
