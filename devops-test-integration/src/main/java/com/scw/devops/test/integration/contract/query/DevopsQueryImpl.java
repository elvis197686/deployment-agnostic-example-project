package com.scw.devops.test.integration.contract.query;

import com.scw.devops.contract.query.DevopsQuery;
import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.contract.query.command.DevopsQueryCommandResultAccessor;
import com.scw.devops.query.service.DefinitionQuery;
import com.scw.devops.test.integration.contract.store.StoreContractInvoker;

public class DevopsQueryImpl implements DevopsQuery {

	private DefinitionQuery definitionQuery;

	public DevopsQueryImpl( final DefinitionQuery definitionQuery ) {
		this.definitionQuery = definitionQuery;
	}

	@Override
	public DevopsQueryCommandResult doCommand( final DevopsQueryCommand command ) {
		StoreContractInvoker.doCommand( command, definitionQuery );
		return DevopsQueryCommandResultAccessor.getResultObject( command );
	}

}
