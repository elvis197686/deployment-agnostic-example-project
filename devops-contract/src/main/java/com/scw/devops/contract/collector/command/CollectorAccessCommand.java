package com.scw.devops.contract.collector.command;

public abstract class CollectorAccessCommand {

	// Required to allow the server-side implementation of the command pattern to return results generically
	public abstract CollectorAccessCommandResult getResultObject();

}
