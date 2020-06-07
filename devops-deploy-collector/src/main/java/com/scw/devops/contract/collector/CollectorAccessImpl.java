package com.scw.devops.contract.collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scw.devops.collector.application.AccessCoordinator;
import com.scw.devops.contract.collector.command.CollectorAccessCommand;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResultAccessor;

@Component
public class CollectorAccessImpl implements CollectorAccess {

	private AccessCoordinator accessCoordinator;

	@Autowired
	public CollectorAccessImpl( final AccessCoordinator accessCoordinator ) {
		this.accessCoordinator = accessCoordinator;
	}

	@Override
	public CollectorAccessCommandResult doCommand( final CollectorAccessCommand command ) {
		StoreContractInvoker.doCommand( command, accessCoordinator );
		return CollectorAccessCommandResultAccessor.getResultObject( command );
	}

}
