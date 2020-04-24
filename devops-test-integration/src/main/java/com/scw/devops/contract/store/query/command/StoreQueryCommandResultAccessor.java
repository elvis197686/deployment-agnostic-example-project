package com.scw.devops.contract.store.query.command;

public class StoreQueryCommandResultAccessor {

	// having a class to do this may seem useless,
	// but actually it does ensure only this project can access the results object.
	public static StoreQueryCommandResult getResultObject(final StoreQueryCommand command) {
		return command.getResultObject();
	}
}
