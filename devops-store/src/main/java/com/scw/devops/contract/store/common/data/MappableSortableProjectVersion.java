package com.scw.devops.contract.store.common.data;

import com.vdurmont.semver4j.Requirement;
import com.vdurmont.semver4j.Semver;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MappableSortableProjectVersion {

	private static final Semver LOWEST_SEMVER = new Semver( "0.0.1" );

	private final ProjectVersion ref;
	private final String singleVersionString;

	public ProjectVersion getProjectVersion() {
		return ref;
	}

	public boolean isPreview() {
		return ref.isPreview;
	}

	public boolean compliesTo( final String semverToCompare ) {
		if ( ref.isPreview ) {
			// Semver does not provide the capability of filtering in or out a preview
			// version, so we will always filter in and expect the caller to discard preview
			// versions if necessary
			return true;
		}
		return getSemver( ref.version ).satisfies( Requirement.buildNPM( semverToCompare ) );
	}

	public int compareTo( final MappableSortableProjectVersion otherVersion ) {
		// WE want highest version first, with preview highest
		if ( ref.isPreview ) {
			if ( otherVersion.ref.isPreview ) {
				return 0;
			}
			return -1;
		}
		if ( otherVersion.ref.isPreview ) {
			return 1;
		}
		return compareSemverStringsHighestFirst( ref.version, otherVersion.ref.version );
	}

	private int compareSemverStringsHighestFirst( final String thisVersion, final String otherVersion ) {
		// Semver library puts lowest first
		return getSemver( otherVersion ).compareTo( getSemver( ref.version ) );
	}

	private static Semver getSemver( final String semverString ) {
		try {
			return new Semver( semverString );
		}
		catch ( Exception se ) {
		}
		return LOWEST_SEMVER;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + singleVersionString.hashCode();
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
		return singleVersionString.equals( ( (MappableSortableProjectVersion) obj ).singleVersionString );
	}

}
