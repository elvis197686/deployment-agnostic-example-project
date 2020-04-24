package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetApplicationDefinitionCommandMixin {

	@JsonCreator
	public GetApplicationDefinitionCommandMixin( @JsonProperty( "name" ) final String name, @JsonProperty( "version" ) final String version ) {
		System.out.println( "Wont be called" );
	}

}
