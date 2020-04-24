package com.scw.devops.contract.store.query.data;

import java.util.Optional;

import lombok.ToString;

@ToString
public class VersionQuery {

	// We must define a constructor (or use Lombok) in order to make items final,
	// so, to save typing, we will make the properties modifiable.
	// Also, we will make them public so we do not need to define getters
	public int versionLimit;
	public boolean wantedVersionIsWildcard;
	public boolean wantedVersionIsPreview;
	public Optional<String> wantedVersionAsSemVer;
	public boolean includePreview;
}
