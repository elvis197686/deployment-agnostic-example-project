package com.scw.devops.contract.store.query.command.getproducts;

import com.scw.devops.contract.store.query.command.OutputProductDefinitions;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.query.data.VersionQuery;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetAllProductDefinitionsCommand extends StoreQueryCommand {

	final VersionQuery versionQuery;

	OutputProductDefinitions result;

	@Override
	public StoreQueryCommandResult getResultObject() {
		return result;
	}
}
