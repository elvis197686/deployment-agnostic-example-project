package com.scw.devops.store.service;

import java.util.Optional;
import java.util.function.Function;

import com.scw.devops.domain.projectversion.ProjectVersion;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProjectVersionWithWildcard {

	private static final String LATEST_TAG = "LatestTag";
	private static final String WILDCARD_VERSION = "*";

	private final ProjectVersion projectVersion;
	private final boolean isWildcard;

	public static ProjectVersionWithWildcard fromVersion( final ProjectVersion version ) {
		Optional<String> versionString = version.getVersionNameIfNotPreview();
		if ( versionString.isPresent() && versionString.get().equals( WILDCARD_VERSION ) ) {
			return new ProjectVersionWithWildcard( ProjectVersion.namedVersion( LATEST_TAG ), true );
		}
		return new ProjectVersionWithWildcard( version, false );
	}

	public ProjectVersion getOrResolveWildcard(final String name, final Function<String, ProjectVersion> versionResolver) {
		if (isWildcard) {
			return versionResolver.apply(name);
		}
		return projectVersion;
	}

}
