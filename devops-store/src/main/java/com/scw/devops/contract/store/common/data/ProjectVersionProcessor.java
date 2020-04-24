package com.scw.devops.contract.store.common.data;

public class ProjectVersionProcessor {

	private static final String PREVIEW_VERSION = "preview";

	// This method allows us to use the name and version string as an Id
	public static ProjectVersion fromSingleString( final String versionAsSingleString ) {
		// Note: The version name given here should not be used except for comparisons
		return ( versionAsSingleString.equals( PREVIEW_VERSION ) ) ? new ProjectVersion( "preview", true )
																   : new ProjectVersion( versionAsSingleString, false );
	}

	public static MappableSortableProjectVersion mappableFromSingleString( final String versionAsSingleString ) {
		return new MappableSortableProjectVersion( fromSingleString( versionAsSingleString ), versionAsSingleString );
	}

}
