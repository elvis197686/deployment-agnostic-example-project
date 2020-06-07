package com.scw.devops.contract.serialisation;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.ConfigurationErrorData;

public class EnvironmentDefMixin {

	@JsonCreator
	public EnvironmentDefMixin( @JsonProperty( "name" ) final String name, @JsonProperty( "version" ) final String version,
								@JsonProperty( "autoStart" ) final Optional<Boolean> autoStart,
								@JsonProperty( "sourceRepository" ) final String sourceRepository,
								@JsonProperty( "error" ) final Optional<ConfigurationErrorData> error ) {
		System.out.println( "Wont be called" );
	}

}
