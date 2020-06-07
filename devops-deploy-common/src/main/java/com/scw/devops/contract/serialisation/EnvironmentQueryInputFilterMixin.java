package com.scw.devops.contract.serialisation;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EnvironmentQueryInputFilterMixin {

	@JsonCreator
	public EnvironmentQueryInputFilterMixin( @JsonProperty( "name" ) final Optional<String> name,
											 @JsonProperty( "version" ) final Optional<String> version,
											 @JsonProperty( "includePreview" ) final Optional<Boolean> includePreview,
											 @JsonProperty( "autoStart " ) final Optional<Boolean> autoStart ) {
		System.out.println( "Wont be called" );
	}

}
