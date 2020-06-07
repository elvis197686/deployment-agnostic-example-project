package com.scw.devops.contract.collector;

import com.scw.devops.contract.collector.command.CollectorAccessCommand;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;

public interface CollectorAccess {

	// Must return a non-null result object
	public CollectorAccessCommandResult doCommand( final CollectorAccessCommand command );

}
