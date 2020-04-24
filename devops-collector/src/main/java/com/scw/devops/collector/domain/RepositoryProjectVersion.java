package com.scw.devops.collector.domain;

import com.scw.devops.contract.store.common.data.ProjectVersion;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class RepositoryProjectVersion {

	private static final String PREVIEW_VERSION = "preview";

	private final String version;
	private final boolean isPreview;

	public String getOriginalName() {
		return version;
	}

	public boolean isPreview() {
		return isPreview;
	}

	public static String getPreviewString() {
		return PREVIEW_VERSION;
	}

	// This method allows us to use the name and version string as an Id
	public static ProjectVersion fromSingleString( final String versionAsSingleString ) {
		// Note: The version name given here should not be used except for comparisons
		return ( versionAsSingleString.equals( PREVIEW_VERSION ) ) ? new ProjectVersion( PREVIEW_VERSION, true )
																   : new ProjectVersion( versionAsSingleString, false );
	}

}
