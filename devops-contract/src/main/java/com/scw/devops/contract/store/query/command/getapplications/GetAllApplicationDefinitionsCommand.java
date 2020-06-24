package com.scw.devops.contract.store.query.command.getapplications;

import com.scw.devops.contract.store.query.command.OutputApplicationDefinitions;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.query.data.VersionQuery;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetAllApplicationDefinitionsCommand extends StoreQueryCommand {

	final VersionQuery versionQuery;

	OutputApplicationDefinitions result;

	@Override
	public StoreQueryCommandResult getResultObject() {
		return result;
	}
}
