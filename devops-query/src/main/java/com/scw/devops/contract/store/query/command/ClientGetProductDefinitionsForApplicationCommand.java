package com.scw.devops.contract.store.query.command;

import java.util.List;

import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.command.getproductsinapplication.GetProductDefinitionsForApplicationCommand;

public class ClientGetProductDefinitionsForApplicationCommand {

	public static List<ProductDefinition> process( final GetProductDefinitionsForApplicationCommand command, final DataStoreReader reader ) {
		OutputProductDefinitions result = (OutputProductDefinitions) reader.doCommand( command );
		return result.getResult();
	}

}
