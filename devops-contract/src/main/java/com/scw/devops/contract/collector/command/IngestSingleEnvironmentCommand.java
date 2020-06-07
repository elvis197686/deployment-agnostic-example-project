package com.scw.devops.contract.collector.command;

import com.scw.devops.contract.collector.data.OutputVoid;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class IngestSingleEnvironmentCommand extends CollectorAccessCommand {

	final String name;

	OutputVoid result;

	@Override
	CollectorAccessCommandResult getResultObject() {
		return result;
	}
}
