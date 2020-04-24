package com.scw.devops.test.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.scw.devops.collector.config.GitlabConnectionConfiguration;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.test.integration.gitlab.gateway.ResourceFolderGateway;

public class GitlabGatewayConfiguration {

	@Autowired
	private GitlabConnectionConfiguration gitlabConfiguration;

	@Bean
	@Primary
	public GitlabGateway resourceFolderGitlabGateway() {
		return new ResourceFolderGateway(gitlabConfiguration);
	}
}
