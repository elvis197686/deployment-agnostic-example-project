package com.scw.devops.contract.query.command;

import java.util.List;

import com.scw.devops.contract.query.data.Environment;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ReturnEnvironmentDefinitions extends DevopsQueryCommandResult {

	private final List<Environment> environments;

	List<Environment> getResult() {
		return environments;
	}

}
