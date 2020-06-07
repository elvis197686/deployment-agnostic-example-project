package com.scw.devops.collector.config;

import org.gitlab.api.GitlabAPI;


public class DummyGitlabConnectionConfiguration implements GitlabConnectionConfiguration {

	private GitlabAPI mockGitlabAPI;

	public DummyGitlabConnectionConfiguration( final GitlabAPI mockGitlabAPI ) {
		this.mockGitlabAPI = mockGitlabAPI;
	}

	@Override
	public GitlabAPI getGitlabApi() {
		return mockGitlabAPI;
	}

	@Override
	public String getGitlabUrl() {
		return "dummy";
	}

}
