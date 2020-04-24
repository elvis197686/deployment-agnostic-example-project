package com.scw.devops.collector.config;

import org.gitlab.api.GitlabAPI;

public interface GitlabConnectionConfiguration {

	public GitlabAPI getGitlabApi();

	public String getGitlabUrl();

}
