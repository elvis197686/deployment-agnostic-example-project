package com.scw.devops.contract.store.query.command;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetProductDefinitionsForEnvironmentCommand extends StoreQueryCommand {

	final String name;
	final String wantedVersionAsSingleString;

	OutputProductDefinitions result;

	@Override
	StoreQueryCommandResult getResultObject() {
		return result;
	}
}
