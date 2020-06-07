package com.scw.devops.query.service;

import java.util.Optional;

import com.scw.devops.contract.store.query.data.VersionQuery;

public class DataStoreQueryBuilder {

	private static final Integer DEFAULT_VERSION_LIMIT = 1;
	private static final String PREVIEW_VERSION = "preview";
	private static final Object WILDCARD_VERSION = "*";

	public static VersionQuery generateQuery( final Optional<Integer> versionLimit,
											  final Optional<String> version,
											  final Optional<Boolean> includePreview,
			final boolean defaultVersionIsPreview) {
		VersionQuery query = new VersionQuery();
		query.versionLimit = versionLimit.orElse(DEFAULT_VERSION_LIMIT);
		if (version != null) {
			query.wantedVersionIsWildcard = false;
			query.wantedVersionIsPreview = false;
			query.wantedVersionAsSemVer = Optional.empty();
			if (version.equals(WILDCARD_VERSION)) {
				query.wantedVersionIsWildcard = true;
			} else if (version.equals(PREVIEW_VERSION)) {
				query.wantedVersionIsPreview = true;
			} else {
				query.wantedVersionAsSemVer = version;
			}
		} else {
			query.wantedVersionIsWildcard = false;
			query.wantedVersionAsSemVer = Optional.empty();
			query.wantedVersionIsPreview = false;
			if (defaultVersionIsPreview) {
				query.wantedVersionIsPreview = true;
			}
		}
		// Default to include preview is based on the version - if it is preview then
		// obv include it
		boolean defaultIncludePreview = false;
		if (query.wantedVersionIsPreview) {
			defaultIncludePreview = true;
		}
		query.includePreview = includePreview.orElse( defaultIncludePreview );
		return query;
	}

}
