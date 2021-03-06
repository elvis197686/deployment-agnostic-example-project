package com.scw.devops.collector.vcs.gateway;

import static com.scw.devops.collector.vcs.gateway.GitlabApiWorkrounds.getRawFileContentWithCorrectFileEncoding;

import java.io.IOException;
import java.util.List;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scw.devops.collector.config.GitlabConnectionConfiguration;

@Component
public class GitlabGateway {

	private final GitlabConnectionConfiguration gitlabConfiguration;

	@Autowired
	public GitlabGateway(final GitlabConnectionConfiguration gitlabConfiguration) {
		this.gitlabConfiguration = gitlabConfiguration;
	}

	public synchronized List<GitlabProject> getProjects(final String groupName) {
		GitlabAPI gitlabApi = gitlabConfiguration.getGitlabApi();

		try {
			return gitlabApi.getGroupProjects(gitlabApi.getGroup(groupName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized GitlabProject getProject(final String groupName, final String projectName) {
		GitlabAPI gitlabApi = gitlabConfiguration.getGitlabApi();

		try {
			return gitlabApi.getProject(groupName, projectName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized List<GitlabTag> getAllTags(final GitlabProject projectObject) {
		GitlabAPI gitlabApi = gitlabConfiguration.getGitlabApi();

		return gitlabApi.getTags(projectObject);
	}

	public synchronized String getFileContents(final GitlabProject projectObject, final String branchOrTagname,
			final String fileName) {
		GitlabAPI gitlabApi = gitlabConfiguration.getGitlabApi();

		try {
			return new String(getRawFileContentWithCorrectFileEncoding(gitlabApi, projectObject.getId(),
					branchOrTagname, fileName), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
