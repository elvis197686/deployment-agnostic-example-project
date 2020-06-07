package com.scw.devops.contract.query;

import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;

public interface DevopsQuery {

	// Must return a non-null result object
	public DevopsQueryCommandResult doCommand( final DevopsQueryCommand command );

}
