package com.scw.devops.collector.domain;

import com.scw.devops.contract.store.common.data.ApplicationInstanceEntry;

public class RepositoryApplicationInstanceEntry {

	private final String alias;
	private final String repoName;

	public RepositoryApplicationInstanceEntry( final String alias, final String repoName ) {
		this.alias = alias;
		this.repoName = repoName;
	}

	public String getValuesYamlName() {
		// The alias is used as the app name in the values.yaml
		return alias;
	}

	public ApplicationInstanceEntry transformTo( final String versionString ) {
		return new ApplicationInstanceEntry( alias, repoName, RepositoryProjectVersion.fromSingleString( versionString ) );
	}
}
