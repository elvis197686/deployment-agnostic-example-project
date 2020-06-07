package com.scw.devops.contract.serialisation;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetProductsInEnvironmentCommandMixin {

	@JsonCreator
	public GetProductsInEnvironmentCommandMixin( @JsonProperty( "name" ) final String name, @JsonProperty( "version" ) final String version,
												 @JsonProperty( "wantedClass" ) final Optional<String> wantedClass ) {
		System.out.println( "Wont be called" );
	}

}
