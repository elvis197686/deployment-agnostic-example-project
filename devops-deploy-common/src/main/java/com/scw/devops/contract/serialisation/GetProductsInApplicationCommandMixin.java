package com.scw.devops.contract.serialisation;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.StandardQueryInputFilter;

public class GetProductsInApplicationCommandMixin {

	@JsonCreator
	public GetProductsInApplicationCommandMixin( @JsonProperty( "applicationName" ) final String applicationName,
												 @JsonProperty( "version" ) final String version,
												 @JsonProperty( "versionLimit" ) final Optional<Integer> versionLimit,
												 @JsonProperty( "queryFilter" ) final StandardQueryInputFilter queryFilter ) {
		System.out.println( "Wont be called" );
	}

}
