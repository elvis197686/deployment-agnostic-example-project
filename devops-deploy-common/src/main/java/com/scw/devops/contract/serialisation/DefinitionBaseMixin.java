package com.scw.devops.contract.serialisation;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ConfigurationError;
import com.scw.devops.domain.projectversion.ProjectVersion;

public class DefinitionBaseMixin {

	@JsonCreator
	public DefinitionBaseMixin( @JsonProperty( "name" ) final String name,
	                            @JsonProperty( "version" ) final ProjectVersion version,
								@JsonProperty( "props" ) final Map<String, Object> arbitraryProperties,
	@JsonProperty( "sourceRepo" ) final String sourceRepository,
	@JsonProperty( "errors" ) final Collection<ConfigurationError> errors ) {
		System.out.println( "Wont be called" );
	}

}
