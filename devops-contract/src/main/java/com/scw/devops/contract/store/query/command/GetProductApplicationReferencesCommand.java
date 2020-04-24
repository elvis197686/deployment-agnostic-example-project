package com.scw.devops.contract.store.query.command;

import com.scw.devops.contract.store.common.data.ProductDefinition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GetProductApplicationReferencesCommand extends StoreQueryCommand {

	final ProductDefinition productDefinition;

	OutputApplicationInstances result;

	@Override
	StoreQueryCommandResult getResultObject() {
		return result;
	}
}
