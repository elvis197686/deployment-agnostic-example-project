package com.scw.devops.contract.store.common.data;

import java.util.Optional;

import com.scw.devops.domain.projectversion.ProjectVersion;
import com.vdurmont.semver4j.Semver;

public class ClientProjectVersionComparator {

	private static final Semver LOWEST_SEMVER = new Semver( "0.0.1" );

	// TODO - This is duplicate of MapableSortableProjectVersion - which includes Semver - find a way to reuse and remove
	public static int compareTo( final ProjectVersion a, final ProjectVersion b ) {
		// We want highest version first, with preview highest
		Optional<String> aVersionName = a.getVersionNameIfNotPreview();
		Optional<String> bVersionName = b.getVersionNameIfNotPreview();
		if ( !aVersionName.isPresent() ) {
			if ( !bVersionName.isPresent() ) {
				return 0;
			}
			return -1;
		}
		if ( !bVersionName.isPresent() ) {
			return 1;
		}
		return compareSemverStringsHighestFirst( aVersionName.get(), bVersionName.get() );
	}

	private static int compareSemverStringsHighestFirst( final String thisVersion, final String otherVersion ) {
		// Semver library puts lowest first
		return getSemver( otherVersion ).compareTo( getSemver( thisVersion ) );
	}

	private static Semver getSemver( final String semverString ) {
		try {
			return new Semver( semverString );
		}
		catch ( Exception se ) {
		}
		return LOWEST_SEMVER;
	}

}
