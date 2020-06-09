package com.scw.devops.test.integration.contract.collector;

import com.scw.devops.collector.application.CollectorAutowiring;
import com.scw.devops.contract.collector.CollectorAccess;
import com.scw.devops.contract.collector.command.CollectorAccessCommand;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResultAccessor;
import com.scw.devops.test.integration.contract.store.StoreContractInvoker;

public class CollectorAccessImpl implements CollectorAccess {

	private CollectorAutowiring autowiring;

	public CollectorAccessImpl( final CollectorAutowiring autowiring ) {
		this.autowiring = autowiring;
	}

	@Override
	public CollectorAccessCommandResult doCommand( final CollectorAccessCommand command ) {
		StoreContractInvoker.doCommand( command, autowiring, CollectorAutowiring.class );
		return CollectorAccessCommandResultAccessor.getResultObject( command );
	}

}
