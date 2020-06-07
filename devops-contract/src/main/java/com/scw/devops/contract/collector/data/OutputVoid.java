package com.scw.devops.contract.collector.data;

import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class OutputVoid extends CollectorAccessCommandResult {

	// TODO - remove - must also disallow FAIL_ON_EMPTY_BEANS?
	boolean dummyBoolean;
}
