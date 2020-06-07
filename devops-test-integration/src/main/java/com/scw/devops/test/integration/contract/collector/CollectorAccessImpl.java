package com.scw.devops.test.integration.contract.collector;

import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.contract.collector.CollectorAccess;
import com.scw.devops.contract.collector.command.CollectorAccessCommand;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResultAccessor;
import com.scw.devops.test.integration.contract.store.StoreContractInvoker;

public class CollectorAccessImpl implements CollectorAccess {

	private AccessCoordinator accessCoordinator;

	public CollectorAccessImpl( final AccessCoordinator accessCoordinator ) {
		this.accessCoordinator = accessCoordinator;
	}

	@Override
	public CollectorAccessCommandResult doCommand( final CollectorAccessCommand command ) {
		StoreContractInvoker.doCommand( command, accessCoordinator );
		return CollectorAccessCommandResultAccessor.getResultObject( command );
	}

}
