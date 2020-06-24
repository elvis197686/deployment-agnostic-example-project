package com.scw.devops.contract.store.query.command.getproductapplications;

import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.query.command.OutputApplicationInstances;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetProductApplicationReferencesCommand extends StoreQueryCommand {

	final ProductDefinition productDefinition;

	OutputApplicationInstances result;

	@Override
	public StoreQueryCommandResult getResultObject() {
		return result;
	}
}
