package com.scw.devops.contract.store.query.command.getpreviousapplication;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.query.command.OutputApplicationDefinition;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetPreviousApplicationDefinitionCommand extends StoreQueryCommand {

	final ApplicationDefinition applicationDefinition;

	OutputApplicationDefinition result;

	@Override
	public StoreQueryCommandResult getResultObject() {
		return result;
	}
}
