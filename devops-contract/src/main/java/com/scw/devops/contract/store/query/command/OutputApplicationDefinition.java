package com.scw.devops.contract.store.query.command;

import java.util.Optional;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class OutputApplicationDefinition extends StoreQueryCommandResult {

	private final Optional<ApplicationDefinition> application;

	Optional<ApplicationDefinition> getResult() {
		return application;
	}

}
