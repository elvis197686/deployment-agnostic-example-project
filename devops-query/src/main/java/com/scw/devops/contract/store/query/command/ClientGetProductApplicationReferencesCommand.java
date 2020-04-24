package com.scw.devops.contract.store.query.command;

import java.util.List;

import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.data.ApplicationInstance;

public class ClientGetProductApplicationReferencesCommand {

	public static List<ApplicationInstance> process( final GetProductApplicationReferencesCommand command, final DataStoreReader reader ) {
		OutputApplicationInstances result = (OutputApplicationInstances) reader.doCommand( command );
		return result.getResult();
	}

}
