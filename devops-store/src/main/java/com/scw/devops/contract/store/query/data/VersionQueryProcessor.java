package com.scw.devops.contract.store.query.data;

import com.scw.devops.contract.store.common.data.MappableSortableProjectVersion;

public class VersionQueryProcessor {

	public static boolean includeThisVersion( final VersionQuery versionQuery, final MappableSortableProjectVersion candidateVersion ) {
		if ( !versionQuery.includePreview && candidateVersion.getProjectVersion().isPreview() ) {
			return false;
		}
		if ( versionQuery.wantedVersionIsWildcard ) {
			return true;
		}
		if ( versionQuery.wantedVersionIsPreview ) {
			return candidateVersion.getProjectVersion().isPreview();
		}
		if ( versionQuery.wantedVersionAsSemVer.isPresent() ) {
			return candidateVersion.compliesTo( versionQuery.wantedVersionAsSemVer.get() );
		}
		return true;
	}

	public static boolean hasMaximumVersions( final VersionQuery versionQuery, final int currentNumberOfVersions ) {
		return currentNumberOfVersions >= versionQuery.versionLimit;
	}

}
