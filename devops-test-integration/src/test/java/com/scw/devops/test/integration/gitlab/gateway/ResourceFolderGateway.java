package com.scw.devops.test.integration.gitlab.gateway;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabTag;
import org.springframework.beans.factory.annotation.Autowired;

import com.scw.devops.collector.config.GitlabConnectionConfiguration;
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.test.integration.resources.ResourceAccess;

public class ResourceFolderGateway extends GitlabGateway {

	private String projectDirectory;

	@Autowired
	public ResourceFolderGateway(final GitlabConnectionConfiguration gitlabConfiguration) {
		super(gitlabConfiguration);
	}

	public void setProjectDirectory(final String projectDirectory) {
		this.projectDirectory = projectDirectory;
	}

	public synchronized List<GitlabProject> getProjects(final String groupName) {
		// Get folders in directory
		Collection<String> subDirectories = ResourceAccess.getSubDirectories(projectDirectory, groupName, null);
		return subDirectories.stream().map(d -> makeProject(groupName, d)).collect(Collectors.toList());
	}

	public synchronized GitlabProject getProject(final String groupName, final String projectName) {
		// Get folder in directory
		return makeProject(groupName, projectName);
	}

	public synchronized List<GitlabTag> getAllTags(final GitlabProject projectObject) {
		// Get all sub-directories of given folder
		StringTriple rootGroupAndProjectFolder = getGroupAndProjectFolder(projectObject);
		Collection<String> subDirectories = ResourceAccess.getSubDirectories(rootGroupAndProjectFolder.rootName,
				rootGroupAndProjectFolder.groupName, rootGroupAndProjectFolder.projectName);
		return subDirectories.stream().filter(t -> notMasterOrDevelop(t)).map(d -> makeTag(projectObject, d))
				.collect(Collectors.toList());
	}

	public synchronized String getFileContents(final GitlabProject projectObject, final String branchOrTagname,
			final String fileName) {
		// Get file from given sub-directory of given folder
		StringTriple rootGroupAndProjectFolder = getGroupAndProjectFolder(projectObject);
		return ResourceAccess.getGitFileContents(rootGroupAndProjectFolder.rootName,
				rootGroupAndProjectFolder.groupName, rootGroupAndProjectFolder.projectName, branchOrTagname, fileName);
	}

	private GitlabProject makeProject(final String groupName, final String projectDirectoryName) {
		GitlabProject project = new GitlabProject();
		project.setName(projectDirectoryName);
		project.setHttpUrl(
				projectDirectory + File.pathSeparator + groupName + File.pathSeparator + projectDirectoryName);
		return project;
	}

	private StringTriple getGroupAndProjectFolder(final GitlabProject projectObject) {
		String[] rootGroupAndProjectNames = projectObject.getHttpUrl().split(File.pathSeparator);
		return new StringTriple(rootGroupAndProjectNames[0], rootGroupAndProjectNames[1], rootGroupAndProjectNames[2]);
	}

	private GitlabTag makeTag(final GitlabProject projectObject, final String subDirectory) {
		GitlabTag tag = new GitlabTag();
		tag.setName(subDirectory);
		tag.setMessage(projectObject.getHttpUrl() + File.pathSeparator + subDirectory);
		return tag;
	}

	// we must filter out master and develop, because Gitlab API does not count
	// these as tags
	private boolean notMasterOrDevelop(final String t) {
		return !(t.equalsIgnoreCase("develop") || t.equalsIgnoreCase("master"));
	}

	private static final class StringTriple {
		private final String rootName;
		private final String groupName;
		private final String projectName;

		public StringTriple(final String rootName, final String groupName, final String projectName) {
			this.rootName = rootName;
			this.groupName = groupName;
			this.projectName = projectName;
		}
	}
}
