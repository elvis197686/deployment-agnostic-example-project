package com.scw.devops.contract.collector.command.gitlabwebhook;

import com.scw.devops.contract.collector.command.CollectorAccessCommand;
import com.scw.devops.contract.collector.command.CollectorAccessCommandResult;
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
	public CollectorAccessCommandResult getResultObject() {
		return result;
	}
}
