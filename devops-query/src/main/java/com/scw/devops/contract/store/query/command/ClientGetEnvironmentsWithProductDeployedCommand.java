package com.scw.devops.contract.store.query.command;

import java.util.List;

import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;

public class ClientGetEnvironmentsWithProductDeployedCommand {

	public static List<EnvironmentDefinition> process( final GetEnvironmentsWithProductDeployedCommand command, final DataStoreReader reader ) {
		OutputEnvironmentDefinitions result = (OutputEnvironmentDefinitions) reader.doCommand( command );
		return result.getResult();
	}

}
