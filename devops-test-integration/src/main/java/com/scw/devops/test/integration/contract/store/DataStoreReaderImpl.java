package com.scw.devops.test.integration.contract.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResultAccessor;
import com.scw.devops.store.service.DataStoreQueryService;

@Component
public class DataStoreReaderImpl implements DataStoreReader {

	private DataStoreQueryService dataStoreQueryService;

	@Autowired
	public DataStoreReaderImpl( final DataStoreQueryService dataStoreQueryService ) {
		this.dataStoreQueryService = dataStoreQueryService;
	}

	@Override
	public StoreQueryCommandResult doCommand( final StoreQueryCommand command ) {
		StoreContractInvoker.doCommand( command, dataStoreQueryService );
		return StoreQueryCommandResultAccessor.getResultObject( command );
	}


}
