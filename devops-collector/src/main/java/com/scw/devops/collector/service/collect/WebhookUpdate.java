package com.scw.devops.collector.service.collect;

import com.scw.devops.collector.domain.RepositoryProjectVersion;
import com.scw.devops.collector.domain.RepositoryType;
import com.scw.devops.collector.vcs.data.RepositoryLocation;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class WebhookUpdate {

	private final RepositoryType type;
	private final String repoGroupName;
	private final String repoProjectName;
	private final String branchName;
	private final boolean isPreviewBranch;

	public RepositoryType getRepositoryType() {
		return type;
	}

	public String getProjectName() {
		return repoProjectName;
	}

	public RepositoryLocation getRepositoryLocation() {
		return new RepositoryLocation(repoGroupName, repoProjectName);
	}

	public RepositoryProjectVersion getProjectVersion() {
		return new RepositoryProjectVersion( branchName, isPreviewBranch );
	}

}
