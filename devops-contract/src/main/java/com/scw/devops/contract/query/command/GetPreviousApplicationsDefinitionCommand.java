package com.scw.devops.contract.query.command;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetPreviousApplicationsDefinitionCommand extends DevopsQueryCommand {

	final String name;
	final String version;

	ReturnApplicationDefinition result;

	@Override
	DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
