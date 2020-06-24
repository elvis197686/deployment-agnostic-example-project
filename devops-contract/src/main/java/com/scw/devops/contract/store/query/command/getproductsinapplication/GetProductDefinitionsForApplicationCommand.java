package com.scw.devops.contract.store.query.command.getproductsinapplication;

import com.scw.devops.contract.store.query.command.OutputProductDefinitions;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.query.data.VersionQuery;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetProductDefinitionsForApplicationCommand extends StoreQueryCommand {

	final String name;
	final String wantedVersionAsSingleString;
	final VersionQuery productVersionsToReturn;

	OutputProductDefinitions result;

	@Override
	public StoreQueryCommandResult getResultObject() {
		return result;
	}
}
