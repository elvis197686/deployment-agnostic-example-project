package com.scw.devops.contract.store.query.command;

import com.scw.devops.contract.store.query.data.VersionQuery;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetAllProductDefinitionsCommand extends StoreQueryCommand {

	final VersionQuery versionQuery;

	OutputProductDefinitions result;

	@Override
	StoreQueryCommandResult getResultObject() {
		return result;
	}
}
