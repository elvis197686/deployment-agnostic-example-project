package com.scw.devops.contract.query.command;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetProductsInEnvironmentCommand extends DevopsQueryCommand {

	final String name;
	final String version;
	final Optional<String> wantedClass;

	ReturnProductDefinitions result;

	@Override
	DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
