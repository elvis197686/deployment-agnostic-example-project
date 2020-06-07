package com.scw.devops.store.service;

import java.util.Map;
import java.util.Optional;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.MappableSortableProjectVersion;
import com.scw.devops.contract.store.common.data.ProjectVersionProcessor;
import com.scw.devops.domain.projectversion.ProjectVersion;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ApplicationInstanceReferenceQuery {

	private final String repoName;
	// TODO - Only one of these is required
	private final String wantedVersionAsSingleString;
	private final ProjectVersion wantedVersion;

	public Optional<ApplicationDefinition> getApplication( final Map<String, Map<MappableSortableProjectVersion, ApplicationDefinition>> appMap ) {
		Map<MappableSortableProjectVersion, ApplicationDefinition> applicationDefinitionsByVersion = appMap.get( repoName );
		if ( applicationDefinitionsByVersion != null ) {
			return Optional.ofNullable( applicationDefinitionsByVersion
				.getOrDefault( new MappableSortableProjectVersion( wantedVersion != null ? wantedVersion
																						 : ProjectVersionProcessor
																							 .fromQueryVersionString( wantedVersionAsSingleString ) ),
							   null ) );
		}
		return Optional.empty();
	}
}
