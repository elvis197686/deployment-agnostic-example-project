package com.scw.devops.contract.serialisation;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StandardQueryInputFilterMixin {

	@JsonCreator
	public StandardQueryInputFilterMixin( @JsonProperty( "name" ) final Optional<String> name,
										  @JsonProperty( "version" ) final Optional<String> version,
										  @JsonProperty( "includePreview" ) final Optional<Boolean> includePreview,
										  @JsonProperty( "validity" ) final Optional<String> validity,
										  @JsonProperty( "productClass" ) final Optional<String> productClass ) {
		System.out.println( "Wont be called" );
	}

}
