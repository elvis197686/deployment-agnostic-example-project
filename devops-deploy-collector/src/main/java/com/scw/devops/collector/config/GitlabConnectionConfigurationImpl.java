package com.scw.devops.collector.config;

import static org.apache.commons.lang3.Validate.notBlank;

import org.gitlab.api.GitlabAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.scw.devops.collector.config.GitlabConnectionConfiguration;

@Configuration
public class GitlabConnectionConfigurationImpl implements GitlabConnectionConfiguration {

	private static final String URL_IS_MISSING = "Gitlab URL is missing";
	private static final String ACCESS_TOKEN_IS_MISSING = "Gitlab access token is missing";

	private final String gitlabUrl;
	private final String gitlabAccessToken;

	@Autowired
	public GitlabConnectionConfigurationImpl(@Value("${gitlab.url}") final String gitlabUrl,
			@Value("${gitlab.access-token}") final String gitlabAccessToken) {
		notBlank(gitlabUrl, URL_IS_MISSING);
		notBlank(gitlabAccessToken, ACCESS_TOKEN_IS_MISSING);
		this.gitlabUrl = gitlabUrl;
		this.gitlabAccessToken = gitlabAccessToken;
	}

	public GitlabAPI getGitlabApi() {
		GitlabAPI gitlabAPI = GitlabAPI.connect(gitlabUrl, gitlabAccessToken);
		gitlabAPI.ignoreCertificateErrors(true);

		return gitlabAPI;
	}

	public String getGitlabUrl() {
		return gitlabUrl;
	}

}
