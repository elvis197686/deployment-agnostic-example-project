package com.scw.devops.contract.store.query.command;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetPreviousApplicationDefinitionCommand extends StoreQueryCommand {

	final ApplicationDefinition applicationDefinition;

	OutputApplicationDefinition result;

	@Override
	StoreQueryCommandResult getResultObject() {
		return result;
	}
}
