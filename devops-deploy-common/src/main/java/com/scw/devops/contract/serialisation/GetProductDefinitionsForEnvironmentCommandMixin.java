package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetProductDefinitionsForEnvironmentCommandMixin {

	@JsonCreator
	public GetProductDefinitionsForEnvironmentCommandMixin( @JsonProperty( "name" ) final String name,
															@JsonProperty( "versionString" ) final String wantedVersionAsSingleString ) {
		System.out.println( "Wont be called" );
	}

}
