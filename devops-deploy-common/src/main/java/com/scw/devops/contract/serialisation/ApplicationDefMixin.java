package com.scw.devops.contract.serialisation;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.ConfigurationErrorData;

public class ApplicationDefMixin {

	@JsonCreator
	public ApplicationDefMixin( @JsonProperty( "name" ) final String name, @JsonProperty( "version" ) final String version,
								@JsonProperty( "sourceRepository" ) final String sourceRepository,
								@JsonProperty( "imageRepository" ) final String imageRepository,
								@JsonProperty( "applicationRuntime" ) final String applicationRuntime,
								@JsonProperty( "error" ) final Optional<ConfigurationErrorData> error ) {
		System.out.println( "Wont be called" );
	}

}
