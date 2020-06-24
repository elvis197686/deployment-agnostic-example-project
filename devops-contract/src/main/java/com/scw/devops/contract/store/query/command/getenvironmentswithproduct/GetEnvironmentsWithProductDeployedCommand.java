package com.scw.devops.contract.store.query.command.getenvironmentswithproduct;

import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.query.command.OutputEnvironmentDefinitions;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetEnvironmentsWithProductDeployedCommand extends StoreQueryCommand {

	final ProductDefinition productDef;

	OutputEnvironmentDefinitions result;

	@Override
	public StoreQueryCommandResult getResultObject() {
		return result;
	}
}
