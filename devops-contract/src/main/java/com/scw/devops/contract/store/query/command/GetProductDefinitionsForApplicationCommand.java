package com.scw.devops.contract.store.query.command;

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
	StoreQueryCommandResult getResultObject() {
		return result;
	}
}
