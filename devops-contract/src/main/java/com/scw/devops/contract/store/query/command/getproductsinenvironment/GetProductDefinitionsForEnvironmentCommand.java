package com.scw.devops.contract.store.query.command.getproductsinenvironment;

import com.scw.devops.contract.store.query.command.OutputProductDefinitions;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetProductDefinitionsForEnvironmentCommand extends StoreQueryCommand {

	final String name;
	final String wantedVersionAsSingleString;

	OutputProductDefinitions result;

	@Override
	public StoreQueryCommandResult getResultObject() {
		return result;
	}
}
