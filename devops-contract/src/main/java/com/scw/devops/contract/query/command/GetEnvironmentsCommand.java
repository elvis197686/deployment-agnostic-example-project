package com.scw.devops.contract.query.command;

import java.util.Optional;

import com.scw.devops.contract.query.data.EnvironmentQueryInputFilter;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetEnvironmentsCommand extends DevopsQueryCommand {

	final Optional<Integer> versionLimit;
	final EnvironmentQueryInputFilter queryFilter;

	ReturnEnvironmentDefinitions result;

	@Override
	DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
