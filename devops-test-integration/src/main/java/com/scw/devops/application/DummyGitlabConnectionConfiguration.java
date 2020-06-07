package com.scw.devops.application;

import org.gitlab.api.GitlabAPI;

import com.scw.devops.collector.config.GitlabConnectionConfiguration;


public class DummyGitlabConnectionConfiguration implements GitlabConnectionConfiguration {

	@Override
	public GitlabAPI getGitlabApi() {
		return null;
	}

	@Override
	public String getGitlabUrl() {
		return "dummy";
	}

}
