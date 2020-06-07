package com.scw.devops.contract.collector.data;

import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class OutputIngestionRequest extends CollectorAccessCommandResult {

	final Throwable th;

}
