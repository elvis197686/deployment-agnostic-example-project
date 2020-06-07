package com.scw.devops.contract.query.command;

import java.util.List;

import com.scw.devops.contract.query.data.ApplicationDef;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ReturnApplicationDefinitions extends DevopsQueryCommandResult {

	private final List<ApplicationDef> apps;

	List<ApplicationDef> getResult() {
		return apps;
	}

}
