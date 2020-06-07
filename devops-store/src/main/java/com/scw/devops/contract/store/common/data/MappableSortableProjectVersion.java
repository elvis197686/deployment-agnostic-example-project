package com.scw.devops.contract.store.common.data;

import java.util.Optional;

import com.scw.devops.domain.projectversion.ProjectVersion;
import com.vdurmont.semver4j.Requirement;
import com.vdurmont.semver4j.Semver;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MappableSortableProjectVersion {

	private static final Semver LOWEST_SEMVER = new Semver( "0.0.1" );

	private final ProjectVersion ref;

	public ProjectVersion getProjectVersion() {
		return ref;
	}

	public boolean compliesTo( final String semverToCompare ) {
		Optional<String> versionString = ref.getVersionNameIfNotPreview();
		if ( !versionString.isPresent() ) {
			// Semver does not provide the capability of filtering in or out a preview
			// version, so we will always filter in and expect the caller to discard preview
			// versions if necessary
			return true;
		}
		return getSemver( versionString.get() ).satisfies( Requirement.buildNPM( semverToCompare ) );
	}

	public int compareTo( final MappableSortableProjectVersion otherVersion ) {
		return compareVersions( ref, otherVersion.ref );
	}

	public static int compareVersions( final ProjectVersion thisVersion, final ProjectVersion otherVersion ) {
		// WE want highest version first, with preview highest
		Optional<String> versionString = thisVersion.getVersionNameIfNotPreview();
		Optional<String> otherVersionString = otherVersion.getVersionNameIfNotPreview();
		if ( !versionString.isPresent() ) {
			if ( !otherVersionString.isPresent() ) {
				return 0;
			}
			return -1;
		}
		if ( !otherVersionString.isPresent() ) {
			return 1;
		}
		return compareSemverStringsHighestFirst( versionString.get(), otherVersionString.get() );
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

	@Override
	public int hashCode() {
		return ref.hashCode();
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		return ref.equals( ( (MappableSortableProjectVersion) obj ).ref );
	}

}
