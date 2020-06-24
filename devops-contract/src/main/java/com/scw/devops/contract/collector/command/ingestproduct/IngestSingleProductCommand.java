package com.scw.devops.contract.collector.command.ingestproduct;

import com.scw.devops.contract.collector.command.CollectorAccessCommand;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;
import com.scw.devops.contract.collector.data.OutputVoid;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class IngestSingleProductCommand extends CollectorAccessCommand {

	final String name;

	OutputVoid result;

	@Override
	public CollectorAccessCommandResult getResultObject() {
		return result;
	}
}
