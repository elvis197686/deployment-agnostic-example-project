package com.scw.devops.contract.store.query.command.getapplication;

import com.scw.devops.contract.store.query.command.OutputApplicationDefinition;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetApplicationDefinitionCommand extends StoreQueryCommand {

	final String name;
	final String version;

	OutputApplicationDefinition result;

	@Override
	public StoreQueryCommandResult getResultObject() {
		return result;
	}
}
