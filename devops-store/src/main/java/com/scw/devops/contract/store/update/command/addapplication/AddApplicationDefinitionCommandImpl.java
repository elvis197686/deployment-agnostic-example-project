package com.scw.devops.contract.store.update.command.addapplication;

import com.scw.devops.contract.store.update.command.addapplication.AddApplicationDefinitionCommand;
import com.scw.devops.store.service.DataStoreUpdateService;

public class AddApplicationDefinitionCommandImpl {

	public static void execute( final DataStoreUpdateService updater, final AddApplicationDefinitionCommand command ) {
		updater.addApplicationDefinition( command.data );
	}
}
