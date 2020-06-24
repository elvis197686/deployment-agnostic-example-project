package com.scw.devops.contract.store.query.command;

public abstract class StoreQueryCommand {

	// Required to allow the server implementation to return results generically
	public abstract StoreQueryCommandResult getResultObject();

}
