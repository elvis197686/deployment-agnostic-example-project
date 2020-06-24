package com.scw.devops.contract.collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scw.devops.collector.application.autowiring.CollectorAutowiring;
import com.scw.devops.contract.collector.command.CollectorAccessCommand;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResultAccessor;

@Component
public class CollectorAccessImpl implements CollectorAccess {

	private final CollectorAutowiring collectorAutowiring;

	@Autowired
	public CollectorAccessImpl( final CollectorAutowiring collectorAutowiring ) {
		this.collectorAutowiring = collectorAutowiring;
	}

	@Override
	public CollectorAccessCommandResult doCommand( final CollectorAccessCommand command ) {
		StoreContractInvoker.doCommand( command, collectorAutowiring, CollectorAutowiring.class );
		return CollectorAccessCommandResultAccessor.getResultObject( command );
	}

}
