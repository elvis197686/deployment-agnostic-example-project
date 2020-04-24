package com.scw.devops.collector.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.scw.devops.collector.vcs.gateway.GitlabGateway;

@Profile( "test" ) // Needed so that the original definitions of the beans are ignored
@Configuration
public class GitlabMockConfiguration {

	@Bean
	@Primary
	// Note: Must have different name to original bean!
	public GitlabGateway mockGitlabGateway() {
		return Mockito.mock( GitlabGateway.class );
	}

}
