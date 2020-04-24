package com.scw.devops.contract.store.query.command;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetApplicationDefinitionCommand extends StoreQueryCommand {

	final String name;
	final String version;

	OutputApplicationDefinition result;

	@Override
	StoreQueryCommandResult getResultObject() {
		return result;
	}
}
