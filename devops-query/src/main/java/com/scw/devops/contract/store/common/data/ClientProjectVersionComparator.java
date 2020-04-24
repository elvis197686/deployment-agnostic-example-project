package com.scw.devops.contract.store.common.data;

import com.vdurmont.semver4j.Semver;

public class ClientProjectVersionComparator {

	private static final Semver LOWEST_SEMVER = new Semver( "0.0.1" );

	public static int compareTo( final ProjectVersion a, final ProjectVersion b ) {
		// We want highest version first, with preview highest
		if ( a.isPreview ) {
			if ( b.isPreview ) {
				return 0;
			}
			return -1;
		}
		if ( b.isPreview ) {
			return 1;
		}
		return compareSemverStringsHighestFirst( a.version, b.version );
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
