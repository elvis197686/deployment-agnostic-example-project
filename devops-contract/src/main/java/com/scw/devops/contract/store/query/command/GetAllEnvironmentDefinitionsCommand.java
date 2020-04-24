package com.scw.devops.contract.store.query.command;

import com.scw.devops.contract.store.query.data.VersionQuery;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetAllEnvironmentDefinitionsCommand extends StoreQueryCommand {

	final VersionQuery versionQuery;

	OutputEnvironmentDefinitions result;

	@Override
	StoreQueryCommandResult getResultObject() {
		return result;
	}
}
