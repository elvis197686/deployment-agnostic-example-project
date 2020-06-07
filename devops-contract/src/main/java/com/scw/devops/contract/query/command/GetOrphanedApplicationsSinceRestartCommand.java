package com.scw.devops.contract.query.command;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetOrphanedApplicationsSinceRestartCommand extends DevopsQueryCommand {

	ReturnApplicationDefinitions result;

	@Override
	DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
