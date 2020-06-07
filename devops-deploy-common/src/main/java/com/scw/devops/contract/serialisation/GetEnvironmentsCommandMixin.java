package com.scw.devops.contract.serialisation;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.EnvironmentQueryInputFilter;

public class GetEnvironmentsCommandMixin {

	@JsonCreator
	public GetEnvironmentsCommandMixin( @JsonProperty( "versionLimit" ) final Optional<Integer> versionLimit,
										@JsonProperty( "queryFilter" ) final EnvironmentQueryInputFilter queryFilter ) {
		System.out.println( "Wont be called" );
	}

}
