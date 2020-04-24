package com.scw.devops.contract.store.query.command;

import java.util.List;

import com.scw.devops.contract.store.common.data.EnvironmentDefinition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class OutputEnvironmentDefinitions extends StoreQueryCommandResult {

	private final List<EnvironmentDefinition> environments;

	List<EnvironmentDefinition> getResult() {
		return environments;
	}

}
