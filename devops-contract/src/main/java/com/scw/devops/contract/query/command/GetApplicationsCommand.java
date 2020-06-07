package com.scw.devops.contract.query.command;

import java.util.Optional;

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
	DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
