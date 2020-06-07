package com.scw.devops.collector.domain;

import com.scw.devops.domain.projectversion.ProjectVersion;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class RepositoryProjectVersion {

	private final String originalBranchName;
	private final ProjectVersion projectVersion;

	public String getOriginalName() {
		return originalBranchName;
	}

	public ProjectVersion getProjectVersion() {
		return projectVersion;
	}

}
