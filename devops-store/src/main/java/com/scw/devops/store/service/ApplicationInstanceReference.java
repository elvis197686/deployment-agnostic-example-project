package com.scw.devops.store.service;

import java.util.Map;
import java.util.Optional;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.MappableSortableProjectVersion;
import com.scw.devops.contract.store.common.data.ProjectVersionProcessor;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ApplicationInstanceReference {

	private final String repoName;
	private final String versionAsSingleString;

	public Optional<ApplicationDefinition> getApplication( final Map<String, Map<MappableSortableProjectVersion, ApplicationDefinition>> appMap ) {
		Map<MappableSortableProjectVersion, ApplicationDefinition> applicationDefinitionsByVersion = appMap.get( repoName );
		if ( applicationDefinitionsByVersion != null ) {
			return Optional.ofNullable( applicationDefinitionsByVersion
				.getOrDefault( ProjectVersionProcessor.fromSingleString( versionAsSingleString ), null ) );
		}
		return Optional.empty();
	}
}
