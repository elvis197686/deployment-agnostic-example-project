package com.scw.devops.contract.store.update.command.addenvironment;

import com.scw.devops.contract.store.common.data.EnhancedEnvironmentDefinition;
import com.scw.devops.contract.store.update.command.StoreUpdateCommand;
import com.scw.devops.contract.store.update.command.addenvironment.AddEnvironmentDefinitionCommand;
import com.scw.devops.store.service.DataStoreUpdateService;

public class AddEnvironmentDefinitionCommandImpl extends StoreUpdateCommand {

	public static void execute( final DataStoreUpdateService updater, final AddEnvironmentDefinitionCommand command ) {
		try {
			updater.addEnvironmentDefinition( EnhancedEnvironmentDefinition.transform( command.data ) );
		}
		catch ( Exception l_ex ) {
			command.ex = l_ex;
		}
	}

}
