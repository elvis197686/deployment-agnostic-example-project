package com.scw.devops.store.service;

import java.util.function.Function;

import com.scw.devops.contract.store.common.data.ProjectVersion;
import com.scw.devops.contract.store.common.data.ProjectVersionProcessor;
import com.scw.devops.contract.store.common.data.SharedProjectVersionProcessor;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProjectVersionWithWildcard {

	private static final String LATEST_TAG = "LatestTag";
	private static final String WILDCARD_VERSION = "*";

	private final ProjectVersion projectVersion;
	private final boolean isWildcard;

	public static ProjectVersionWithWildcard fromSingleString(final String versionAsSingleString) {
		if (versionAsSingleString.equals(WILDCARD_VERSION)) {
			return new ProjectVersionWithWildcard( new ProjectVersion( LATEST_TAG, false ), true );
		}
		return new ProjectVersionWithWildcard( ProjectVersionProcessor.fromSingleString( versionAsSingleString ), false );
	}

	public ProjectVersion getOrResolveWildcard(final String name, final Function<String, ProjectVersion> versionResolver) {
		if (isWildcard) {
			return versionResolver.apply(name);
		}
		return projectVersion;
	}

	public String getSingleVersionString() {
		if (isWildcard) {
			return "*";
		}
		return SharedProjectVersionProcessor.getSingleVersionString( projectVersion );
	}

}
