package com.scw.devops.contract.query.command.getorphanedapplications;

import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.contract.query.command.ReturnApplicationDefinitions;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetOrphanedApplicationsSinceRestartCommand extends DevopsQueryCommand {

	ReturnApplicationDefinitions result;

	@Override
	public DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
