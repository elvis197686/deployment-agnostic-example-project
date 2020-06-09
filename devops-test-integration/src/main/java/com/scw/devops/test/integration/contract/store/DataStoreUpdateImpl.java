package com.scw.devops.test.integration.contract.store;

import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.contract.store.update.command.StoreUpdateCommand;
import com.scw.devops.store.service.DataStoreUpdateService;

public class DataStoreUpdateImpl implements DataStoreUpdater {

	private DataStoreUpdateService dataStoreUpdateService;

	public DataStoreUpdateImpl( final DataStoreUpdateService dataStoreUpdateService ) {
		this.dataStoreUpdateService = dataStoreUpdateService;
	}

	@Override
	public void doCommand( final StoreUpdateCommand command ) {
		StoreContractInvoker.doCommand( command, dataStoreUpdateService, DataStoreUpdateService.class );
	}


}
