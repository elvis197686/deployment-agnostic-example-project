package com.scw.devops.contract.collector.command;

public abstract class CollectorAccessCommand {

	// Required to allow the server implementation to return results generically
	abstract CollectorAccessCommandResult getResultObject();

}
