package com.scw.devops.test.integration.contract.store;

import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResultAccessor;
import com.scw.devops.store.service.DataStoreQueryService;

public class DataStoreReaderImpl implements DataStoreReader {

	private DataStoreQueryService dataStoreQueryService;

	public DataStoreReaderImpl( final DataStoreQueryService dataStoreQueryService ) {
		this.dataStoreQueryService = dataStoreQueryService;
	}

	@Override
	public StoreQueryCommandResult doCommand( final StoreQueryCommand command ) {
		StoreContractInvoker.doCommand( command, dataStoreQueryService );
		return StoreQueryCommandResultAccessor.getResultObject( command );
	}


}
