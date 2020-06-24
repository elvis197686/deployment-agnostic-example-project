package com.scw.devops.contract.store.query.command;

import java.util.List;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.command.getapplications.GetAllApplicationDefinitionsCommand;

public class ClientGetAllApplicationDefinitionsCommand {

	// TODO - make these a generic class?
	public static List<ApplicationDefinition> process( final GetAllApplicationDefinitionsCommand command, final DataStoreReader reader ) {
		OutputApplicationDefinitions result = (OutputApplicationDefinitions) reader.doCommand( command );
		return result.getResult();
	}
}
