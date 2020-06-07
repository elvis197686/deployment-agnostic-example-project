package com.scw.devops.contract.query.command;

import com.scw.devops.contract.query.data.ApplicationDef;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ReturnApplicationDefinition extends DevopsQueryCommandResult {

	private final ApplicationDef app;

	ApplicationDef getResult() {
		return app;
	}

}
