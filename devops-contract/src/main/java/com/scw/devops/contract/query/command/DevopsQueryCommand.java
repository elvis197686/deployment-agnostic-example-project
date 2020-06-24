package com.scw.devops.contract.query.command;

public abstract class DevopsQueryCommand {

	// Required to allow the server implementation to return results generically
	public abstract DevopsQueryCommandResult getResultObject();

}
