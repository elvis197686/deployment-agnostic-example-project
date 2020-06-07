package com.scw.devops.domain.projectversion;

import java.util.Optional;

// Immutable object that brings together all information about a project's version
public final class ProjectVersion {

	private static final String PREVIEW_VERSION = "preview";

	private final String version;
	private final boolean isPreview;

	private ProjectVersion( final String version, final boolean isPreview ) {
		this.version = version;
		this.isPreview = isPreview;
	}

	public static ProjectVersion previewVersion() {
		return new ProjectVersion( "preview", true );
	}

	public static ProjectVersion namedVersion( final String version ) {
		return new ProjectVersion( version, false );
	}

	public static ProjectVersion fromYamlVersionString( final String version ) {
		if ( PREVIEW_VERSION.equals( version ) ) {
			return previewVersion();
		}
		return namedVersion( version );
	}

	public static ProjectVersion combineParentAndOwnVersion( final ProjectVersion parentProjectVersion, final String yamlVersionString ) {
		return parentProjectVersion.isPreview ? previewVersion() : fromYamlVersionString( yamlVersionString );
	}

	public boolean isPreview() {
		return isPreview;
	}

	public Optional<String> getVersionNameIfNotPreview() {
		return isPreview ? Optional.empty() : Optional.of( version );
	}

	private String getSingleVersionString() {
		return ( isPreview ) ? PREVIEW_VERSION : version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( getSingleVersionString().hashCode() );
		return result;
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		return getSingleVersionString().equals( ( (ProjectVersion) obj ).getSingleVersionString() );
	}
}
