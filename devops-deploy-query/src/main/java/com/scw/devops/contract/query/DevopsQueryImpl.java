package com.scw.devops.contract.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.contract.query.command.DevopsQueryCommandResultAccessor;
import com.scw.devops.query.service.DefinitionQuery;

@Component
public class DevopsQueryImpl implements DevopsQuery {

	private DefinitionQuery definitionQuery;

	@Autowired
	public DevopsQueryImpl( final DefinitionQuery definitionQuery ) {
		this.definitionQuery = definitionQuery;
	}

	@Override
	public DevopsQueryCommandResult doCommand( final DevopsQueryCommand command ) {
		StoreContractInvoker.doCommand( command, definitionQuery );
		return DevopsQueryCommandResultAccessor.getResultObject( command );
	}

}
