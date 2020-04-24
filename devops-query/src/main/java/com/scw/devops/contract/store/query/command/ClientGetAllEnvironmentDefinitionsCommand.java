package com.scw.devops.contract.store.query.command;

import java.util.List;

import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;

public class ClientGetAllEnvironmentDefinitionsCommand {

	public static List<EnvironmentDefinition> process( final GetAllEnvironmentDefinitionsCommand command, final DataStoreReader reader ) {
		OutputEnvironmentDefinitions result = (OutputEnvironmentDefinitions) reader.doCommand( command );
		return result.getResult();
	}
}
