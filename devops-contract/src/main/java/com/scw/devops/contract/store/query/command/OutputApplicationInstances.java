package com.scw.devops.contract.store.query.command;

import java.util.List;

import com.scw.devops.contract.store.query.data.ApplicationInstance;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class OutputApplicationInstances extends StoreQueryCommandResult {

	private final List<ApplicationInstance> application;

	List<ApplicationInstance> getResult() {
		return application;
	}

}
