package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.query.data.VersionQuery;

public class GetProductDefinitionsForApplicationCommandMixin {

	@JsonCreator
	public GetProductDefinitionsForApplicationCommandMixin( @JsonProperty( "name" ) final String name,
															@JsonProperty( "versionString" ) final String wantedVersionAsSingleString,
															@JsonProperty( "productVersions" ) final VersionQuery productVersionsToReturn ) {
		System.out.println( "Wont be called" );
	}

}
