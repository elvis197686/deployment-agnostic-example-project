package com.scw.devops.contract.collector.command.ingestall;

import com.scw.devops.contract.collector.command.CollectorAccessCommand;
import com.scw.devops.contract.collector.data.OutputIngestionRequest;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class IngestAllDataCommand extends CollectorAccessCommand {

	OutputIngestionRequest result;

	@Override
	public OutputIngestionRequest getResultObject() {
		return result;
	}
}
