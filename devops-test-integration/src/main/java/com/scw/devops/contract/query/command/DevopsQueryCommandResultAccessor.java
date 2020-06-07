package com.scw.devops.contract.query.command;

public class DevopsQueryCommandResultAccessor {

	// having a class to do this may seem useless,
	// but actually it does ensure only this project can access the results object.
	public static DevopsQueryCommandResult getResultObject( final DevopsQueryCommand command ) {
		return command.getResultObject();
	}
}
