package com.scw.devops.contract.collector.command;

public class CollectorAccessCommandResultAccessor {

	// having a class to do this may seem useless,
	// but actually it does ensure only this project can access the results object.
	public static CollectorAccessCommandResult getResultObject( final CollectorAccessCommand command ) {
		return command.getResultObject();
	}
}
