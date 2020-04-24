package com.scw.devops.contract.store.update;

import com.scw.devops.contract.store.update.command.StoreUpdateCommand;

public interface DataStoreUpdater {

	public void doCommand( final StoreUpdateCommand command );

}
