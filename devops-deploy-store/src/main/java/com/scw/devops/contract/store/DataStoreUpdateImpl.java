package com.scw.devops.contract.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.contract.store.update.command.StoreUpdateCommand;
import com.scw.devops.store.service.DataStoreUpdateService;

@Component
public class DataStoreUpdateImpl implements DataStoreUpdater {

	private DataStoreUpdateService dataStoreUpdateService;

	@Autowired
	public DataStoreUpdateImpl( final DataStoreUpdateService dataStoreUpdateService ) {
		this.dataStoreUpdateService = dataStoreUpdateService;
	}

	@Override
	public void doCommand( final StoreUpdateCommand command ) {
		StoreContractInvoker.doCommand( command, dataStoreUpdateService );
	}


}
