package com.scw.devops.query.controller;

import java.util.Optional;

import org.springframework.lang.Nullable;

import com.scw.devops.contract.store.query.data.VersionQuery;

public class DataStoreQueryBuilder {

	private static final Integer DEFAULT_VERSION_LIMIT = 1;
	private static final String PREVIEW_VERSION = "preview";
	private static final Object WILDCARD_VERSION = "*";

	public static VersionQuery generateQuery( final Optional<Integer> versionLimit,
			@Nullable final String version, @Nullable final Boolean includePreview,
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
				query.wantedVersionAsSemVer = Optional.ofNullable(version);
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
		query.includePreview = Optional.ofNullable(includePreview).orElse(defaultIncludePreview);
		return query;
	}

}
