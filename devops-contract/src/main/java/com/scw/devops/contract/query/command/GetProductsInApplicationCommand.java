package com.scw.devops.contract.query.command;

import java.util.Optional;

import com.scw.devops.contract.query.data.StandardQueryInputFilter;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetProductsInApplicationCommand extends DevopsQueryCommand {

	final String applicationName;
	final String version;
	final Optional<Integer> versionLimit;
	final StandardQueryInputFilter queryFilter;

	ReturnProductDefinitions result;

	@Override
	DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
