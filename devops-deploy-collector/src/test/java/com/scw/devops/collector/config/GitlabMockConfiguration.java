package com.scw.devops.collector.config;

import org.gitlab.api.GitlabAPI;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile( "test" ) // Needed so that the original definitions of the beans are ignored
@Configuration
public class GitlabMockConfiguration {

	@Bean
	@Primary
	// Note: Must have different name to original bean!
	public GitlabConnectionConfiguration mockGitlabConnectionConfiguration() {
		return new DummyGitlabConnectionConfiguration( mockGitlabAPI() );
	}

	@Bean
	public GitlabAPI mockGitlabAPI() {
		return Mockito.mock( GitlabAPI.class );
	}

}
