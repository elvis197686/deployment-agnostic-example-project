package com.scw.devops.contract.query.command.getpreviousapplications;

import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.contract.query.command.ReturnApplicationDefinition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetPreviousApplicationsDefinitionCommand extends DevopsQueryCommand {

	final String name;
	final String version;

	ReturnApplicationDefinition result;

	@Override
	public DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
