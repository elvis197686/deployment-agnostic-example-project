package com.scw.devops.contract.store.query.command.getenvironments;

import com.scw.devops.contract.store.query.command.OutputEnvironmentDefinitions;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.query.data.VersionQuery;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetAllEnvironmentDefinitionsCommand extends StoreQueryCommand {

	final VersionQuery versionQuery;

	OutputEnvironmentDefinitions result;

	@Override
	public StoreQueryCommandResult getResultObject() {
		return result;
	}
}
