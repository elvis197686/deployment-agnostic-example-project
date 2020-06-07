package com.scw.devops.contract.collector.command;

import com.scw.devops.contract.collector.data.OutputIngestionRequest;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class IngestAllDataCommand extends CollectorAccessCommand {

	OutputIngestionRequest result;

	@Override
	OutputIngestionRequest getResultObject() {
		return result;
	}
}
