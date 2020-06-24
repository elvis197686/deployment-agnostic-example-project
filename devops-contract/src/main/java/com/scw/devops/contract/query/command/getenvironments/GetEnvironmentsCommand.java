package com.scw.devops.contract.query.command.getenvironments;

import java.util.Optional;

import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.contract.query.command.ReturnEnvironmentDefinitions;
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
	public DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
