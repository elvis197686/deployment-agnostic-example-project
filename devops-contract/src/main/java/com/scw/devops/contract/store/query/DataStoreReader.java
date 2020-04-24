package com.scw.devops.contract.store.query;

import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;

public interface DataStoreReader {

	// Must return a non-null result object
	public StoreQueryCommandResult doCommand( final StoreQueryCommand command );

}
