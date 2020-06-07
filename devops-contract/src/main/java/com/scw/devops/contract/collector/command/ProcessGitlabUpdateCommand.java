package com.scw.devops.contract.collector.command;

import com.scw.devops.contract.collector.data.GitlabWebhookData;
import com.scw.devops.contract.collector.data.OutputIngestionRequest;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ProcessGitlabUpdateCommand extends CollectorAccessCommand {

	final GitlabWebhookData webhookData;

	OutputIngestionRequest result;

	@Override
	CollectorAccessCommandResult getResultObject() {
		return result;
	}
}
