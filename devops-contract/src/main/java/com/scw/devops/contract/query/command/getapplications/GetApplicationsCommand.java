package com.scw.devops.contract.query.command.getapplications;

import java.util.Optional;

import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.contract.query.command.ReturnApplicationDefinitions;
import com.scw.devops.contract.query.data.StandardQueryInputFilter;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetApplicationsCommand extends DevopsQueryCommand {

	final Optional<Integer> versionLimit;
	final StandardQueryInputFilter queryFilter;

	ReturnApplicationDefinitions result;

	@Override
	public DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
