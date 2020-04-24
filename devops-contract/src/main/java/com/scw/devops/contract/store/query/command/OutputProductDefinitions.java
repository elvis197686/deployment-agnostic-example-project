package com.scw.devops.contract.store.query.command;

import java.util.List;

import com.scw.devops.contract.store.common.data.ProductDefinition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class OutputProductDefinitions extends StoreQueryCommandResult {

	private final List<ProductDefinition> products;

	List<ProductDefinition> getResult() {
		return products;
	}
}
