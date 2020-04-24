package com.scw.devops.contract.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;

@Component
public class DataStoreReaderImpl implements DataStoreReader {

	private RestGateway restGateway;

	@Autowired
	public DataStoreReaderImpl( final RestGateway restGateway ) {
		this.restGateway = restGateway;
	}

	@Override
	public StoreQueryCommandResult doCommand( final StoreQueryCommand command ) {
		StoreQueryCommandResult result = restGateway.doPost( "/query", command, StoreQueryCommandResult.class );
		return result;
	}


}
