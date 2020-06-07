package com.scw.devops.contract.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.contract.store.update.command.StoreUpdateCommand;

@Component
public class DataStoreUpdaterImpl implements DataStoreUpdater {

	private RestGateway restGateway;

	@Autowired
	public DataStoreUpdaterImpl( final RestGateway restGateway ) {
		this.restGateway = restGateway;
	}

	@Override
	public void doCommand( final StoreUpdateCommand command ) {
		restGateway.doPost( "/update", command, Void.class );
	}


}
