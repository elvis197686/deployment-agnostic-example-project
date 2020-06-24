package com.scw.devops.contract.store.update.command.addproduct;

import com.scw.devops.contract.store.update.command.StoreUpdateCommand;
import com.scw.devops.contract.store.update.command.addproduct.AddProductDefinitionCommand;
import com.scw.devops.store.service.DataStoreUpdateService;

public class AddProductDefinitionCommandImpl extends StoreUpdateCommand {

	public static void execute( final DataStoreUpdateService updater, final AddProductDefinitionCommand command ) {
		try {
			updater.addProductDefinition( command.data );
		}
		catch ( Exception l_ex ) {
			command.ex = l_ex;
		}
	}

}
