package com.scw.devops.contract.serialisation;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.StandardQueryInputFilter;

public class GetProductsCommandMixin {

	@JsonCreator
	public GetProductsCommandMixin( @JsonProperty( "versionLimit" ) final Optional<Integer> versionLimit,
									@JsonProperty( "queryFilter" ) final StandardQueryInputFilter queryFilter ) {
		System.out.println( "Wont be called" );
	}

}
