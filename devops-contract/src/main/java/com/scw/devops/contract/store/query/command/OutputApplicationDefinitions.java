package com.scw.devops.contract.store.query.command;

import java.util.List;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class OutputApplicationDefinitions extends StoreQueryCommandResult {

	private final List<ApplicationDefinition> applications;

	List<ApplicationDefinition> getResult() {
		return applications;
	}
}
