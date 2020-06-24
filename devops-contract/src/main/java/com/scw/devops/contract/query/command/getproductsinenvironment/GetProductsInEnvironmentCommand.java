package com.scw.devops.contract.query.command.getproductsinenvironment;

import java.util.Optional;

import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.contract.query.command.ReturnProductDefinitions;

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
	public DevopsQueryCommandResult getResultObject() {
		return result;
	}
}
