package com.scw.devops.contract.store.common.data;

import com.scw.devops.domain.projectversion.ProjectVersion;

public class ProjectVersionProcessor {

	// This method allows us to use the name and version string as an Id
	public static ProjectVersion fromQueryVersionString( final String versionAsSingleString ) {
		// Note: The version name given here should not be used except for comparisons
		return ProjectVersion.fromYamlVersionString( versionAsSingleString );
	}

	public static MappableSortableProjectVersion mappableFromSingleString( final String versionAsSingleString ) {
		return new MappableSortableProjectVersion( fromQueryVersionString( versionAsSingleString ) );
	}

}
